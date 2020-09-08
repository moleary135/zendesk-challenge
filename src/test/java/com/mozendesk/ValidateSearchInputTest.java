package com.mozendesk;

import com.mozendesk.objects.IllegalSearchException;
import com.mozendesk.objects.field.*;
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
        assertEquals(TRUE, searcher.getType("organization", "_id") instanceof IntegerFieldType);
        assertEquals(TRUE, searcher.getType("organization", "url") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("organization", "external_id") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("organization", "name") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("organization", "domain_names") instanceof ArrayFieldType);
        assertEquals(TRUE, searcher.getType("organization", "created_at") instanceof TimestampFieldType);
        assertEquals(TRUE, searcher.getType("organization", "details") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("organization", "shared_tickets") instanceof BooleanFieldType);
        assertEquals(TRUE, searcher.getType("organization", "tags") instanceof ArrayFieldType);

        assertEquals(TRUE, searcher.getType("ticket", "_id") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("ticket", "url") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("ticket", "external_id") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("ticket", "created_at")instanceof TimestampFieldType);
        assertEquals(TRUE, searcher.getType("ticket", "subject") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("ticket", "description") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("ticket", "priority") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("ticket", "status") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("ticket", "type") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("ticket", "submitter_id") instanceof IntegerFieldType);
        assertEquals(TRUE, searcher.getType("ticket", "assignee_id") instanceof IntegerFieldType);
        assertEquals(TRUE, searcher.getType("ticket", "organization_id") instanceof IntegerFieldType);
        assertEquals(TRUE, searcher.getType("ticket", "tags")  instanceof ArrayFieldType);
        assertEquals(TRUE, searcher.getType("ticket", "has_incidents") instanceof BooleanFieldType);
        assertEquals(TRUE, searcher.getType("ticket", "due_at") instanceof TimestampFieldType);
        assertEquals(TRUE, searcher.getType("ticket", "via") instanceof StringFieldType);

        assertEquals(TRUE, searcher.getType("user", "_id") instanceof IntegerFieldType);
        assertEquals(TRUE, searcher.getType("user", "url") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("user", "external_id") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("user", "name") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("user", "alias") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("user", "created_at") instanceof TimestampFieldType);
        assertEquals(TRUE, searcher.getType("user", "active") instanceof BooleanFieldType);
        assertEquals(TRUE, searcher.getType("user", "verified") instanceof BooleanFieldType);
        assertEquals(TRUE, searcher.getType("user", "shared") instanceof BooleanFieldType);
        assertEquals(TRUE, searcher.getType("user", "locale") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("user", "timezone") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("user", "last_login_at") instanceof TimestampFieldType);
        assertEquals(TRUE, searcher.getType("user", "email") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("user", "phone") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("user", "signature") instanceof StringFieldType);
        assertEquals(TRUE, searcher.getType("user", "organization_id") instanceof IntegerFieldType);
        assertEquals(TRUE, searcher.getType("user", "tags") instanceof ArrayFieldType);
        assertEquals(TRUE, searcher.getType("user", "suspended") instanceof BooleanFieldType);
        assertEquals(TRUE, searcher.getType("user", "role") instanceof StringFieldType);
    }

    @Test
    public void testInvalidGetType() {
        assertThrows(IllegalSearchException.class, () -> searcher.getType("badobjectname", "_id"));
        assertThrows(IllegalSearchException.class, () -> searcher.getType("organization", "badtypename"));
    }

    @Test
    public void testValidateInValueString() {
        assertEquals(TRUE, new StringFieldType().validateInputValue(""));
        assertEquals(TRUE, new StringFieldType().validateInputValue("testing123"));
        assertEquals(TRUE, new StringFieldType().validateInputValue("null"));
    }

    @Test
    public void testValidateInValueInteger() {
        assertThrows(IllegalSearchException.class, () -> new IntegerFieldType().validateInputValue("testing123"));
        assertEquals(TRUE, new IntegerFieldType().validateInputValue("123"));
    }

    @Test
    public void testValidateInValueBoolean() {
        assertThrows(IllegalSearchException.class, () -> new BooleanFieldType().validateInputValue("testing123"));
        assertEquals(TRUE, new BooleanFieldType().validateInputValue("True"));
        assertEquals(TRUE, new BooleanFieldType().validateInputValue("FaLsE"));
    }

    @Test
    public void testValidateInValueTimestamp() {
        assertEquals(TRUE, new TimestampFieldType().validateInputValue("2016-04-14T08:32:31 -10:00"));
        assertThrows(IllegalSearchException.class, () -> new TimestampFieldType().validateInputValue("2016/04/14 08:32:31 -10:00"));
    }

    @Test
    public void testValidateInValueSArray() {
        assertEquals(TRUE, new ArrayFieldType(new StringFieldType()).validateInputValue(""));
        assertEquals(TRUE, new ArrayFieldType(new StringFieldType()).validateInputValue("123"));
    }
}
