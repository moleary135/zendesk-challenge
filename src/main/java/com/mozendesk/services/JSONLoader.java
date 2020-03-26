package com.mozendesk.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mozendesk.objects.Organization;
import com.mozendesk.objects.Ticket;
import com.mozendesk.objects.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility class to map json data to objects.
 * Create object maps keyed on _id field
 */
public class JSONLoader {
    //format of dates from json files and expected matching format for search input
    public final static String dateFormatString = "yyyy-MM-dd'T'HH:mm:ss X";

    public Map<Integer, Organization> loadOrgs(String jsonFolder) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Organization> orgs =  mapper.readValue(
                new File(jsonFolder + "/organizations.json"),
                new TypeReference<List<Organization>>(){});
        return orgs.stream().collect(
                Collectors.toMap(o -> (Integer)o.getField("_id"), Function.identity()));
    }

    public Map<String, Ticket> loadTickets(String jsonFolder) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Ticket> tickets = mapper.readValue(
                new File(jsonFolder + "/tickets.json"),
                new TypeReference<List<Ticket>>(){});
        return tickets.stream().collect(
                Collectors.toMap(o -> (String)o.getField("_id"), Function.identity()));
    }

    public Map<Integer, User> loadUsers(String jsonFolder) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = mapper.readValue(
                new File(jsonFolder + "/users.json"),
                new TypeReference<List<User>>(){});
        return users.stream().collect(
                Collectors.toMap(o -> (Integer)o.getField("_id"), Function.identity()));
    }
}
