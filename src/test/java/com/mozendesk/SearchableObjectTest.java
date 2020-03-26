package com.mozendesk;

import com.mozendesk.objects.SearchableObject;
import com.mozendesk.objects.Ticket;
import com.mozendesk.services.JSONLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SearchableObjectTest {

    public static SearchableObject ticket;

    @BeforeEach
    public void setUp() {
        ticket = new Ticket();
    }

    @Test
    public void testValidSetField() {
        ticket.setField("organization_id", 123);
        assertEquals(123, ticket.getField("organization_id"));

        ticket.setField("subject", "Margaret");
        assertEquals("Margaret", ticket.getField("subject"));

        ticket.setField("has_incidents", FALSE);
        assertEquals(FALSE, ticket.getField("has_incidents"));
    }

    @Test
    public void testDatetimeSetField() {
        DateFormat df = new SimpleDateFormat(JSONLoader.dateFormatString);
        ticket.setCreatedAt("2013-08-04T01:03:27 -10:00");
        ticket.setDateTimeField("due_at", "2014-08-04T01:03:27 -10:00");

        try {
            assertEquals(df.parse("2013-08-04T01:03:27 -10:00"), ticket.getField("created_at"));
            assertEquals(df.parse("2014-08-04T01:03:27 -10:00"), ticket.getField("due_at"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testArraySetField() {
        List<String> matchingTags = new ArrayList<>(
                Arrays.asList("Springville", "Sutton", "Hartsville/Hartley", "Diaperville"));

        ticket.setField("tags", matchingTags);
        assertEquals(matchingTags, ticket.getField("tags"));
    }

    @Test
    public void testHasField() {
        ticket.setField("subject", null);
        assertEquals(FALSE, ticket.hasField("subject"));
        assertEquals(FALSE, ticket.hasField("_id"));

        ticket.setField("subject", "new subject");
        assertEquals(TRUE, ticket.hasField("subject"));
    }

    @Test
    public void testInvalidSetField() {
        ticket.setField("subject", null);
        assertEquals("", ticket.getField("subject"));
    }

    @Test
    public void testGetFieldAsInteger() {
        ticket.setField("subject", "new subject");
        ticket.setField("organization_id", 103);
        ticket.setField("submitter_id", "103");
        assertNull(ticket.getFieldAsInteger("subject"));
        assertEquals(TRUE, 103 == ticket.getFieldAsInteger("organization_id"));
        assertEquals(TRUE, 103 == ticket.getFieldAsInteger("submitter_id"));
    }
}
