package com.mozendesk;

import com.mozendesk.objects.IllegalSearchException;
import com.mozendesk.objects.field.FieldType;
import com.mozendesk.services.Searcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test user input validation
 */
public class ValidateSearchInputTest {

    public static Searcher searcher;

    @BeforeAll
    public static void setUp() {
        searcher = new Searcher();
    }

    @Test
    public void testIsValidObjectType() {
        assertEquals(TRUE, searcher.isValidObjectType("organization"));
        assertEquals(TRUE, searcher.isValidObjectType("ticket"));
        assertEquals(TRUE, searcher.isValidObjectType("user"));

        assertEquals(FALSE, searcher.isValidObjectType("organisation"));
        assertEquals(FALSE, searcher.isValidObjectType("users"));
    }

    // May seem obnoxious, but catches pesky copy paste errors.
    @Test
    public void testValidGetType() {
        assertEquals(FieldType.INTEGER, searcher.getType("organization", "_id"));
        assertEquals(FieldType.STRING, searcher.getType("organization", "url"));
        assertEquals(FieldType.STRING, searcher.getType("organization", "external_id"));
        assertEquals(FieldType.STRING, searcher.getType("organization", "name"));
        assertEquals(FieldType.SARRAY, searcher.getType("organization", "domain_names"));
        assertEquals(FieldType.TIMESTAMP, searcher.getType("organization", "created_at"));
        assertEquals(FieldType.STRING, searcher.getType("organization", "details"));
        assertEquals(FieldType.BOOLEAN, searcher.getType("organization", "shared_tickets"));
        assertEquals(FieldType.SARRAY, searcher.getType("organization", "tags"));

        assertEquals(FieldType.STRING, searcher.getType("ticket", "_id"));
        assertEquals(FieldType.STRING, searcher.getType("ticket", "url"));
        assertEquals(FieldType.STRING, searcher.getType("ticket", "external_id"));
        assertEquals(FieldType.TIMESTAMP, searcher.getType("ticket", "created_at"));
        assertEquals(FieldType.STRING, searcher.getType("ticket", "subject"));
        assertEquals(FieldType.STRING, searcher.getType("ticket", "description"));
        assertEquals(FieldType.STRING, searcher.getType("ticket", "priority"));
        assertEquals(FieldType.STRING, searcher.getType("ticket", "status"));
        assertEquals(FieldType.STRING, searcher.getType("ticket", "type"));
        assertEquals(FieldType.INTEGER, searcher.getType("ticket", "submitter_id"));
        assertEquals(FieldType.INTEGER, searcher.getType("ticket", "assignee_id"));
        assertEquals(FieldType.INTEGER, searcher.getType("ticket", "organization_id"));
        assertEquals(FieldType.SARRAY, searcher.getType("ticket", "tags"));
        assertEquals(FieldType.BOOLEAN, searcher.getType("ticket", "has_incidents"));
        assertEquals(FieldType.TIMESTAMP, searcher.getType("ticket", "due_at"));
        assertEquals(FieldType.STRING, searcher.getType("ticket", "via"));

        assertEquals(FieldType.INTEGER, searcher.getType("user", "_id"));
        assertEquals(FieldType.STRING, searcher.getType("user", "url"));
        assertEquals(FieldType.STRING, searcher.getType("user", "external_id"));
        assertEquals(FieldType.STRING, searcher.getType("user", "name"));
        assertEquals(FieldType.STRING, searcher.getType("user", "alias"));
        assertEquals(FieldType.TIMESTAMP, searcher.getType("user", "created_at"));
        assertEquals(FieldType.BOOLEAN, searcher.getType("user", "active"));
        assertEquals(FieldType.BOOLEAN, searcher.getType("user", "verified"));
        assertEquals(FieldType.BOOLEAN, searcher.getType("user", "shared"));
        assertEquals(FieldType.STRING, searcher.getType("user", "locale"));
        assertEquals(FieldType.STRING, searcher.getType("user", "timezone"));
        assertEquals(FieldType.TIMESTAMP, searcher.getType("user", "last_login_at"));
        assertEquals(FieldType.STRING, searcher.getType("user", "email"));
        assertEquals(FieldType.STRING, searcher.getType("user", "phone"));
        assertEquals(FieldType.STRING, searcher.getType("user", "signature"));
        assertEquals(FieldType.INTEGER, searcher.getType("user", "organization_id"));
        assertEquals(FieldType.SARRAY, searcher.getType("user", "tags"));
        assertEquals(FieldType.BOOLEAN, searcher.getType("user", "suspended"));
        assertEquals(FieldType.STRING, searcher.getType("user", "role"));
    }

    @Test
    public void testInvalidGetType() {
        assertThrows(IllegalSearchException.class, () -> searcher.getType("badobjectname", "_id"));
        assertThrows(IllegalSearchException.class, () -> searcher.getType("organization", "badtypename"));
    }

    @Test
    public void testValidateInValueString() {
        assertEquals(TRUE, searcher.validateInValue(FieldType.STRING, ""));
        assertEquals(TRUE, searcher.validateInValue(FieldType.STRING, "testing123"));
        assertEquals(TRUE, searcher.validateInValue(FieldType.STRING, "null"));
    }

    @Test
    public void testValidateInValueInteger() {
        assertEquals(TRUE, searcher.validateInValue(FieldType.INTEGER, ""));
        assertThrows(IllegalSearchException.class, () -> searcher.validateInValue(FieldType.INTEGER, "testing123"));
        assertEquals(TRUE, searcher.validateInValue(FieldType.INTEGER, "123"));
    }

    @Test
    public void testValidateInValueBoolean() {
        assertEquals(TRUE, searcher.validateInValue(FieldType.BOOLEAN, ""));
        assertThrows(IllegalSearchException.class, () -> searcher.validateInValue(FieldType.BOOLEAN, "testing123"));
        assertEquals(TRUE, searcher.validateInValue(FieldType.BOOLEAN, "True"));
        assertEquals(TRUE, searcher.validateInValue(FieldType.BOOLEAN, "FaLsE"));
    }

    @Test
    public void testValidateInValueTimestamp() {
        assertEquals(TRUE, searcher.validateInValue(FieldType.TIMESTAMP, ""));
        assertEquals(TRUE, searcher.validateInValue(FieldType.TIMESTAMP, "2016-04-14T08:32:31 -10:00"));
        assertThrows(IllegalSearchException.class, () -> searcher.validateInValue(FieldType.TIMESTAMP, "2016/04/14 08:32:31 -10:00"));
    }

    @Test
    public void testValidateInValueSArray() {
        assertEquals(TRUE, searcher.validateInValue(FieldType.SARRAY, ""));
        assertEquals(TRUE, searcher.validateInValue(FieldType.SARRAY, "123"));
    }
}
