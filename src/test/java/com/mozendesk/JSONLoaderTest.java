package com.mozendesk;

import com.mozendesk.objects.Organization;
import com.mozendesk.objects.Ticket;
import com.mozendesk.objects.User;
import com.mozendesk.services.JSONLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.mozendesk.services.PrettyPrinter.JSON_DIR_NOT_FOUND_TEXT;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class JSONLoaderTest {
    public static JSONLoader loader;

    @BeforeAll
    public static void setUp() {
        loader = new JSONLoader();
    }

    @Test
    public void testLoadObjectsWithNull() {
        Map<Integer, Organization> orgs = null;
        try {
            orgs = loader.loadOrgs("testresources/organizationNullField.json");
        } catch (IOException e) {
            System.err.printf(JSON_DIR_NOT_FOUND_TEXT, "testresources/organizationNullField.json");
        }
        assertNotEquals(null, orgs);
        assertNotEquals(null, orgs.get(126));
        assertEquals(FALSE, orgs.get(126).hasField("details"));
        assertEquals("", orgs.get(126).getField("details"));
    }

    @Test
    public void testLoadObjectsMissingFields() {
        Map<String, Ticket> tickets = null;
        try {
            tickets = loader.loadTickets("testresources/ticketMissingField.json");
        } catch (IOException e) {
            System.err.printf(JSON_DIR_NOT_FOUND_TEXT, "testresources/ticketMissingField.json");
        }
        assertNotEquals(null, tickets);
        assertNotEquals(null, tickets.get("81bdd837-e955-4aa4-a971-ef1e3b373c6d"));
        assertEquals(FALSE, tickets.get("81bdd837-e955-4aa4-a971-ef1e3b373c6d").hasField("due_at"));
        assertEquals("", tickets.get("81bdd837-e955-4aa4-a971-ef1e3b373c6d").getField("due_at"));
    }

    @Test
    public void testLoadFullValidObject() {
        Map<Integer, User> users = null;
        try {
            users = loader.loadUsers("testresources/userFull.json");
        } catch (IOException e) {
            System.err.printf(JSON_DIR_NOT_FOUND_TEXT, "testresources/userFull.json");
        }
        assertNotEquals(null, users);
        assertNotEquals(null, users.get(1));

        //Check all different types of fields
        DateFormat df = new SimpleDateFormat(JSONLoader.dateFormatString);
        try {
            assertEquals(df.parse("2013-08-04T01:03:27 -10:00"), users.get(1).getField("last_login_at"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertEquals("74341f74-9c79-49d5-9611-87ef9b6eb75f", users.get(1).getField("external_id"));
        assertEquals(119, users.get(1).getField("organization_id"));
        assertEquals(TRUE, users.get(1).getField("active"));

        List<?> tags = (List<?>)users.get(1).getField("tags");
        List<String> matchingTags = new ArrayList<>(
                Arrays.asList("Springville", "Sutton", "Hartsville/Hartley", "Diaperville"));
        assertEquals(TRUE, tags.containsAll(matchingTags));
    }

}
