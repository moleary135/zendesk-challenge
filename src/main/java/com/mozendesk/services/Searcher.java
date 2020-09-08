package com.mozendesk.services;

import com.mozendesk.objects.*;
import com.mozendesk.objects.field.SearchableField;
import com.mozendesk.objects.field.SearchableFields;
import com.mozendesk.objects.results.OrganizationResult;
import com.mozendesk.objects.results.SearchResult;
import com.mozendesk.objects.results.TicketResult;
import com.mozendesk.objects.results.UserResult;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    public SearchableField getType(String object, String field) {
        switch(object) {
            case "organization":
                if (SearchableFields.orgFieldTypes.containsKey(field)) {
                    return SearchableFields.orgFieldTypes.get(field).getFieldType();
                }
                break;
            case "user" :
                if (SearchableFields.userFieldTypes.containsKey(field)) {
                    return SearchableFields.userFieldTypes.get(field).getFieldType();
                }
                break;
            case "ticket" :
                if (SearchableFields.ticketFieldTypes.containsKey(field)) {
                    return SearchableFields.ticketFieldTypes.get(field).getFieldType();
                }
                break;
            default:
                throw new IllegalSearchException(INVALID_OBJECT_TYPE_TEXT);
        }
        throw new IllegalSearchException(INVALID_FIELD_TYPE_TEXT);
    }

    /**
     * The public search call that returns a List of SearchResults given a valid search
     * Filters based on search parameters, and then maps results to the corresponding SearchResult objects, which includes lookups for related objects.
     */
    public List<? extends SearchResult> search(String inObject, SearchableField sf, String inField, String inValue) {
        switch (inObject) {
            case "organization":
                return search(organizations.values(), sf, inField, inValue)
                        .map(o -> new OrganizationResult((Organization)o,
                                orgUsers.get(o.getFieldAsInteger("_id")),
                                orgTickets.get(o.getFieldAsInteger("_id")))).collect(Collectors.toList());
            case "user":
                return search(users.values(), sf, inField, inValue)
                        .map(u -> new UserResult((User)u,
                                organizations.get(u.getFieldAsInteger("organization_id")),
                                userAssignedTickets.get(u.getFieldAsInteger("_id")),
                                userSubmittedTickets.get(u.getFieldAsInteger("_id")))).collect(Collectors.toList());
            case "ticket":
                return search(tickets.values(), sf, inField, inValue)
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
    private Stream<? extends SearchableObject> search(Collection<? extends SearchableObject> objs, SearchableField sf, String inField, String inValue) {
        if (inValue.isEmpty()) { //special case when looking for objects where the field does not exist or is 'empty'
            return objs.stream().filter(o -> o.getField(inField).equals(""));
        } else { //filter out objects where field is absent first
            return objs.stream().filter(o -> o.hasField(inField)).filter(o -> sf.isMatch(o.getField(inField), inValue));
        }
    }
}
