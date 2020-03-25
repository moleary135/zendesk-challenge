package com.mozendesk.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mozendesk.objects.*;
import com.mozendesk.objects.searchable.FieldType;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mozendesk.services.PrettyPrinter.JSON_DIR_NOT_FOUND_TEXT;
import static com.mozendesk.services.PrettyPrinter.SEARCH_RESULTS_TEXT;

/**
 * @TODO need to make indexes and relationships
 */
public class Searcher {

    private Map<Integer, Organization> organizations = null;
    private Map<String, Ticket> tickets = null;
    private Map<Integer, User> users = null;

    //In memory indexes to speed up relationship lookups
    //Prioritizing performance and simplicity over memory given the smaller set of data
    private Map<Integer, List<Ticket>> orgTickets;
    private Map<Integer, List<User>> orgUsers;
    private Map<Integer, List<Ticket>> userSubmittedTickets;
    private Map<Integer, List<Ticket>> userAssignedTickets;

    //init objects and indexes
    public Searcher(String jsonFolder) {
        JSONLoader loader = new JSONLoader();
        try {
            organizations = loader.loadOrgs(jsonFolder);
            tickets = loader.loadTickets(jsonFolder);
            users = loader.loadUsers(jsonFolder);
        } catch (IOException e) {
            System.err.printf(JSON_DIR_NOT_FOUND_TEXT, jsonFolder);
            System.exit(-1);
        }

        orgUsers = users.values().stream().filter(u -> u.hasField("organization_id")).collect(Collectors.groupingBy(u -> u.getFieldAsInteger("organization_id")));
        orgTickets = tickets.values().stream().filter(t -> t.hasField("organization_id")).collect(Collectors.groupingBy(u -> u.getFieldAsInteger("organization_id")));
        userSubmittedTickets = tickets.values().stream().filter(t -> t.hasField("submitter_id")).collect(Collectors.groupingBy(u -> u.getFieldAsInteger("submitter_id")));
        userAssignedTickets = tickets.values().stream().filter(t -> t.hasField("assignee_id")).collect(Collectors.groupingBy(u -> u.getFieldAsInteger("assignee_id")));
    }

    /**
     * @return true if inValue is a possible value given the FieldType, else false
     */
    public boolean validateInValue(FieldType ft, String inValue) {
        switch(ft) {
            case INTEGER:
                try {
                    Integer.parseInt(inValue);
                } catch (NumberFormatException e){
                    return false;
                }
                break;
            case BOOLEAN:
                return (inValue.equalsIgnoreCase("true") || inValue.equalsIgnoreCase("false"));
            case TIMESTAMP:
                DateFormat df = new SimpleDateFormat(JSONLoader.dateFormatString);
                try {
                    df.parse(inValue);
                } catch (ParseException e) {
                    return false;
                }
        }
        return true;
    }

    public List<? extends SearchResult> search(String inObject, FieldType ft, String inField, String inValue) {
        switch (inObject) {
            case "organization":
                return Objects.requireNonNull(searchObjects(organizations.values(), ft, inField, inValue))
                        .map(o -> new OrganizationResult((Organization)o,
                        orgUsers.get(o.getFieldAsInteger("_id")),
                        orgTickets.get(o.getFieldAsInteger("_id")))).collect(Collectors.toList());
            case "user":
                return Objects.requireNonNull(searchObjects(users.values(), ft, inField, inValue))
                        .map(u -> new UserResult((User)u,
                        organizations.get(u.getFieldAsInteger("organization_id")),
                        userAssignedTickets.get(u.getFieldAsInteger("_id")),
                        userSubmittedTickets.get(u.getFieldAsInteger("_id")))).collect(Collectors.toList());
            case "ticket":
                return Objects.requireNonNull(searchObjects(tickets.values(), ft, inField, inValue))
                        .map(t -> new TicketResult((Ticket)t,
                        organizations.get(t.getFieldAsInteger("organization_id")),
                        users.get(t.getFieldAsInteger("assignee_id")),
                        users.get(t.getFieldAsInteger("submitter_id")))).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private Stream<? extends SearchableObject> searchObjects(Collection<? extends SearchableObject> objs, FieldType ft, String inField, String inValue) {
        switch(ft) {
            case STRING:
                return objs.stream().filter(o -> ((String)o.getField(inField)).equalsIgnoreCase(inValue));
            case INTEGER:
                int i = Integer.parseInt(inValue);
                return objs.stream().filter(o -> ((Integer)o.getField(inField)) == i);
            case BOOLEAN:
                boolean b = Boolean.parseBoolean(inValue);
                return objs.stream().filter(o -> ((Boolean)o.getField(inField)) == b);
            case TIMESTAMP:
                DateFormat df = new SimpleDateFormat(JSONLoader.dateFormatString);
                try {
                    Date d = df.parse(inValue);
                    return objs.stream().filter(o -> o.getField(inField).equals(d));
                } catch (ParseException e){
                    e.printStackTrace();
                }
            case SARRAY: //@TODO make custom searchablearraylist type that extend searchableobject?
                return objs.stream().filter(
                        o -> {
                            List<?> list = (List<?>)o.getField(inField);
                            return list.stream().anyMatch(e -> ((String)e).equalsIgnoreCase(inValue));
                        });
        }
        //Will throw only if adding new searchable field types and not correctly adding search support
        throw new IllegalSearchException("Search on a field that is not supported.");
    }
}
