package com.mozendesk;

import com.mozendesk.objects.IllegalSearchException;
import com.mozendesk.objects.Organization;
import com.mozendesk.objects.Ticket;
import com.mozendesk.objects.User;
import com.mozendesk.objects.field.FieldType;
import com.mozendesk.objects.results.OrganizationResult;
import com.mozendesk.objects.results.SearchResult;
import com.mozendesk.objects.results.TicketResult;
import com.mozendesk.objects.results.UserResult;
import com.mozendesk.services.Searcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

public class SearcherTest {
    public static Searcher searcher;

    @BeforeAll
    static void setUp(){
        searcher = new Searcher();
        try {
            searcher.init("testresources");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchInvalidObjectType() {
        assertThrows(IllegalSearchException.class, () -> searcher.search("badObjectType", FieldType.STRING, "email", "rosannasimpson@flotonic.com"));
    }

    @Test
    public void testSearchEmptyValue() {
        List<? extends SearchResult> result = searcher.search("ticket", FieldType.TIMESTAMP, "due_at", "");
        assertEquals(5, result.size());
    }

    @Test
    public void testSearchStringValue() {
        List<? extends SearchResult> result = searcher.search("user", FieldType.STRING, "email", "rosannasimpson@flotonic.com");
        assertEquals(1, result.size());

        result = searcher.search("organization", FieldType.STRING, "details", "MegaCorp");
        assertEquals(9, result.size());
    }

    @Test
    public void testSearchArrayValue() {
        List<? extends SearchResult> result = searcher.search("organization", FieldType.SARRAY, "tags", "fulton");
        assertEquals(1, result.size());
    }

    @Test
    public void testSearchBooleanValue() {
        List<? extends SearchResult> result = searcher.search("ticket", FieldType.BOOLEAN, "has_incidents", "true");
        assertEquals(99, result.size());
    }

    @Test
    public void testSearchIntegerValue() {
        List<? extends SearchResult> result = searcher.search("ticket", FieldType.INTEGER, "organization_id", "103");
        assertEquals(6, result.size());
    }

    @Test
    public void testSearchTimestampValue() {
        List<? extends SearchResult> result = searcher.search("user", FieldType.TIMESTAMP, "created_at", "2016-07-28T05:29:25 -10:00");
        assertEquals(1, result.size());
    }

    @Test
    public void testSearchInvalidTimestampValue() {
        assertThrows(IllegalSearchException.class, () -> searcher.search("user", FieldType.TIMESTAMP, "created_at", "2016/07/28T05:29:25 -10:00"));
    }

    @Test
    public void testSearchOrganizationResult() {
        List<? extends SearchResult> results = searcher.search("organization", FieldType.INTEGER, "_id", "102");
        assertEquals(1, results.size());

        OrganizationResult result = (OrganizationResult)results.get(0);

        Integer[] matchUserIds = {25, 33, 69};
        String[] matchTicketIds = {"25cb699f-a5dd-45d8-9bc1-9c4b7d096946", "20615fe1-765b-4ff5-b4f6-ea42dcc8cac3",
                "3ff0599a-fe0f-4f8f-ac31-e2636843bcea", "6fed7d01-15dd-4b59-94f9-1093b4bc0995", "df1a642a-e704-4556-af79-98a63b59401d",
                "bb8b1829-25d9-4534-83a2-c4e6086d76d4", "ea69e0c0-d1b8-462e-a654-b571666e6253", "a12a5f33-d4a0-4e43-8773-4b22e16fc0c8"};

        Set<Integer> userIdsSet = new HashSet<>(Arrays.asList(matchUserIds));
        Set<String> ticketIdsSet = new HashSet<>(Arrays.asList(matchTicketIds));

        Set<Integer> userIds = new HashSet<>();
        result.users.forEach(u -> userIds.add(u.getFieldAsInteger("_id")));

        Set<String> ticketIds = new HashSet<>();
        result.tickets.forEach(u -> ticketIds.add((String)u.getField("_id")));


        assertEquals(102, result.organization.getField("_id"));
        assertEquals(TRUE, userIds.equals(userIdsSet));
        assertEquals(TRUE, ticketIds.equals(ticketIdsSet));
    }

    @Test
    public void testSearchTicketResult() {
        List<? extends SearchResult> results = searcher.search("ticket", FieldType.STRING, "_id", "e68d8bfd-9826-42fd-9692-add445aa7430");
        assertEquals(1, results.size());

        TicketResult result = (TicketResult)results.get(0);

        assertNull(result.organization);
        assertNull(result.assignedTo);
        assertEquals("Tyler Bates", result.submittedBy.getField("name"));
    }

    @Test
    public void testSearchUserResult() {
        List<? extends SearchResult> results = searcher.search("user", FieldType.INTEGER, "_id", "2");
        assertEquals(1, results.size());

        UserResult result = (UserResult)results.get(0);

        String[] matchAssignedIds = {"6fed7d01-15dd-4b59-94f9-1093b4bc0995", "dcb9143e-cb17-49ea-a9be-abf6989bd2d4"};
        Set<String> assignedIdsSet = new HashSet<>(Arrays.asList(matchAssignedIds));

        Set<String> assignedIds = new HashSet<>();
        result.assignedTickets.forEach(u -> assignedIds.add((String)u.getField("_id")));

        assertEquals("Qualitern", result.organization.getField("name"));
        assertEquals(TRUE, assignedIds.equals(assignedIdsSet));
        assertNull(result.submittedTickets);

    }



}
