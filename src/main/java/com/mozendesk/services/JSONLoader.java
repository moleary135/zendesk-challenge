package com.mozendesk.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mozendesk.objects.Organization;
import com.mozendesk.objects.Ticket;
import com.mozendesk.objects.User;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Utility class to map json data to objects.
 */
public class JSONLoader {

    public final static String dateFormatString = "yyyy-MM-dd'T'HH:mm:ss X";

    public List<Organization> loadOrgs(String jsonFolder) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(jsonFolder + "/organizations.json"),
                new TypeReference<List<Organization>>(){});
    }

    public List<Ticket> loadTickets(String jsonFolder) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(jsonFolder + "/tickets.json"),
                new TypeReference<List<Ticket>>(){});
    }

    public List<User> loadUsers(String jsonFolder) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(jsonFolder + "/users.json"),
                new TypeReference<List<User>>(){});
    }
}
