package com.mozendesk.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mozendesk.objects.Organization;
import com.mozendesk.objects.SearchableObject;
import com.mozendesk.objects.Ticket;
import com.mozendesk.objects.User;
import com.mozendesk.objects.searchable.FieldType;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @TODO need to make indexes and relationships
 */
public class Searcher {

    //In memory indexes to speed up relationship lookups
    //Prioritizing performance and simplicity over memory given the smaller set of data
    private Map<Optional<Integer>, List<Ticket>> orgTickets;
    private Map<Optional<Integer>, List<User>> orgUsers;
    private Map<Optional<Integer>, List<Ticket>> userSubmittedTickets;
    private Map<Optional<Integer>, List<Ticket>> userAssignedTickets;

    //init indexes
    public Searcher(Map<String, Ticket> tickets, Map<Integer, User> users) {
        orgUsers = users.values().stream().collect(Collectors.groupingBy(u -> Optional.ofNullable(u.getFieldAsInteger("organization_id"))));
        orgTickets = tickets.values().stream().collect(Collectors.groupingBy(u -> Optional.ofNullable(u.getFieldAsInteger("organization_id"))));
        userSubmittedTickets = tickets.values().stream().collect(Collectors.groupingBy(u -> Optional.ofNullable(u.getFieldAsInteger("submitter_id"))));
        userAssignedTickets = tickets.values().stream().collect(Collectors.groupingBy(u -> Optional.ofNullable(u.getFieldAsInteger("assignee_id"))));
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

    //@TODO make new Result classes and super Result class and change return type to List<? extends superResult>
    // add switch based on object to call this one?, can send predicate maybe?
    public <E> List<? extends SearchableObject> search(Collection<? extends SearchableObject> objs, FieldType ft, String inField, String inValue) {
        switch(ft) {
            case STRING:
                return objs.stream().filter(o -> ((String)o.getField(inField)).equalsIgnoreCase(inValue)).collect(Collectors.toList());
            case INTEGER:
                int i = Integer.parseInt(inValue);
                return objs.stream().filter(o -> ((Integer)o.getField(inField)) == i).collect(Collectors.toList());
            case BOOLEAN:
                boolean b = Boolean.parseBoolean(inValue);
                return objs.stream().filter(o -> ((Boolean)o.getField(inField)) == b).collect(Collectors.toList());
            case TIMESTAMP:
                DateFormat df = new SimpleDateFormat(JSONLoader.dateFormatString);
                try {
                    Date d = df.parse(inValue);
                    return objs.stream().filter(o -> o.getField(inField).equals(d)).collect(Collectors.toList());
                } catch (ParseException e){
                    e.printStackTrace();
                }
            case SARRAY: //@TODO make custom searchablearraylist type that extend searchableobject?
                return objs.stream().filter(
                        o -> {
                            List<?> list = (List<?>)o.getField(inField);
                            return list.stream().anyMatch(e -> ((String)e).equalsIgnoreCase(inValue));
                        }).collect(Collectors.toList());
        }

        return null; //@TODO throw
    }
}
