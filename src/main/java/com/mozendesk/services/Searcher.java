package com.mozendesk.services;

import com.mozendesk.objects.*;
import com.mozendesk.objects.field.SearchableFields;
import com.mozendesk.objects.results.OrganizationResult;
import com.mozendesk.objects.results.SearchResult;
import com.mozendesk.objects.results.TicketResult;
import com.mozendesk.objects.results.UserResult;
import com.mozendesk.objects.field.FieldType;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mozendesk.services.PrettyPrinter.*;

/**
 * The main search worker.
 *
 * Collections and indexes stored in memory prioritizing performance and
 * simplicity over memory given the smaller set of data
 */
public class Searcher {

    private Map<Integer, Organization> organizations = null;
    private Map<String, Ticket> tickets = null;
    private Map<Integer, User> users = null;

    //In memory indexes to speed up relationship lookups.
    //Otherwise would have to do many linear passes which yields really bad performance.
    private Map<Integer, List<Ticket>> orgTickets;
    private Map<Integer, List<User>> orgUsers;
    private Map<Integer, List<Ticket>> userSubmittedTickets;
    private Map<Integer, List<Ticket>> userAssignedTickets;
    public static final Set<String> searchableObjectTypes = Set.of("organization", "ticket", "user");

    //init objects and indexes
    public Searcher() {}

    public void init(String jsonFolder) throws IOException {
        JSONLoader loader = new JSONLoader();
        organizations = loader.loadOrgs(jsonFolder + "/organizations.json");
        tickets = loader.loadTickets(jsonFolder + "/tickets.json");
        users = loader.loadUsers(jsonFolder + "/users.json");

        orgUsers = users.values().stream().filter(u -> u.hasField("organization_id")).collect(Collectors.groupingBy(u -> u.getFieldAsInteger("organization_id")));
        orgTickets = tickets.values().stream().filter(t -> t.hasField("organization_id")).collect(Collectors.groupingBy(u -> u.getFieldAsInteger("organization_id")));
        userSubmittedTickets = tickets.values().stream().filter(t -> t.hasField("submitter_id")).collect(Collectors.groupingBy(u -> u.getFieldAsInteger("submitter_id")));
        userAssignedTickets = tickets.values().stream().filter(t -> t.hasField("assignee_id")).collect(Collectors.groupingBy(u -> u.getFieldAsInteger("assignee_id")));
    }

    /**
     * Validates the object only
     */
    public boolean isValidObjectType(String objectType) {
        return searchableObjectTypes.contains(objectType);
    }

    /**
     * Validates the object and field inputs
     * Fetches the type of the field on the given object
     * @return the FieldType of the field on the given object
     */
    public FieldType getType(String object, String field) {
        switch(object) {
            case "organization":
                if (SearchableFields.orgFieldTypes.containsKey(field)) {
                    return SearchableFields.orgFieldTypes.get(field).getType();
                }
                break;
            case "user" :
                if (SearchableFields.userFieldTypes.containsKey(field)) {
                    return SearchableFields.userFieldTypes.get(field).getType();
                }
                break;
            case "ticket" :
                if (SearchableFields.ticketFieldTypes.containsKey(field)) {
                    return SearchableFields.ticketFieldTypes.get(field).getType();
                }
                break;
            default:
                throw new IllegalSearchException(INVALID_OBJECT_TYPE_TEXT);
        }
        throw new IllegalSearchException(INVALID_FIELD_TYPE_TEXT);
    }

    /**
     * Validates the search value against the field type
     * @return true if inValue is a possible value given the FieldType, else false
     * Since input is a String by default, Strings (including empty "") are always valid
     */
    public boolean validateInValue(FieldType fieldType, String inValue) {
        if (inValue.isEmpty()) {
            return true;
        }

        switch(fieldType) {
            case INTEGER:
                try {
                    Integer.parseInt(inValue);
                } catch (NumberFormatException e) {
                    throw new IllegalSearchException(INVALID_INTEGER_VALUE_TEXT);
                }
                break;
            case BOOLEAN:
                if (!(inValue.equalsIgnoreCase("true") || inValue.equalsIgnoreCase("false"))) {
                    throw new IllegalSearchException(INVALID_BOOLEAN_VALUE_TEXT);
                }
                break;
            case TIMESTAMP:
                DateFormat df = new SimpleDateFormat(JSONLoader.dateFormatString);
                try {
                    df.parse(inValue);
                } catch (ParseException e) {
                    throw new IllegalSearchException(INVALID_TIMESTAMP_VALUE_TEXT);
                }
        }
        return true;
    }

    /**
     * The public search call that returns a List of SearchResults given a valid search
     * First filters based on search parameters.
     * Then maps to the corresponding SearchResult objects, which includes lookups for related objects.
     */
    public List<? extends SearchResult> search(String inObject, FieldType fieldType, String inField, String inValue) {
        switch (inObject) {
            case "organization":
                return searchObjects(organizations.values(), fieldType, inField, inValue)
                        .map(o -> new OrganizationResult((Organization)o,
                        orgUsers.get(o.getFieldAsInteger("_id")),
                        orgTickets.get(o.getFieldAsInteger("_id")))).collect(Collectors.toList());
            case "user":
                return searchObjects(users.values(), fieldType, inField, inValue)
                        .map(u -> new UserResult((User)u,
                        organizations.get(u.getFieldAsInteger("organization_id")),
                        userAssignedTickets.get(u.getFieldAsInteger("_id")),
                        userSubmittedTickets.get(u.getFieldAsInteger("_id")))).collect(Collectors.toList());
            case "ticket":
                return searchObjects(tickets.values(), fieldType, inField, inValue)
                        .map(t -> new TicketResult((Ticket)t,
                        organizations.get(t.getFieldAsInteger("organization_id")),
                        users.get(t.getFieldAsInteger("assignee_id")),
                        users.get(t.getFieldAsInteger("submitter_id")))).collect(Collectors.toList());
        }
        throw new IllegalSearchException(INVALID_OBJECT_TYPE_TEXT); //inObject already validated so will hit return in switch
    }

    /**
     * Filters a collection based on the fieldType and search inValue
     * @return The filtered stream of SearchableObjects
     */
    private Stream<? extends SearchableObject> searchObjects(Collection<? extends SearchableObject> objs, FieldType fieldType, String inField, String inValue) {
        if (inValue.isEmpty()) {
            return objs.stream().filter(o -> o.getField(inField).equals(""));
        }
        //else filter out nonexistent values
        Stream<? extends SearchableObject> stream = objs.stream().filter(o -> !o.getField(inField).equals(""));
        switch(fieldType) {
            case STRING:
                return stream.filter(o -> ((String)o.getField(inField)).equalsIgnoreCase(inValue));
            case INTEGER:
                int i = Integer.parseInt(inValue);
                return stream.filter(o -> ((Integer) o.getField(inField)) == i);
            case BOOLEAN:
                boolean b = Boolean.parseBoolean(inValue);
                return stream.filter(o -> ((Boolean) o.getField(inField)) == b);
            case TIMESTAMP:
                DateFormat df = new SimpleDateFormat(JSONLoader.dateFormatString);
                try {
                    Date d = df.parse(inValue);
                    return stream.filter(o -> o.getField(inField).equals(d));
                } catch (ParseException e) { //input and timestamp fields should've already been validated
                    throw new IllegalSearchException(INVALID_TIMESTAMP_VALUE_TEXT);
                }
            case SARRAY:
                return stream.filter(o -> {
                            List<?> list = (List<?>)o.getField(inField);
                            return list.stream().anyMatch(e -> ((String)e).equalsIgnoreCase(inValue));});
        }
        //After validating input using methods above, should only throw when adding new searchable field types
        // and not correctly adding search support
        throw new IllegalSearchException(INVALID_FIELD_TYPE_TEXT);
    }
}
