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
    public static void setUp() throws Exception {
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

    @Test
    public void testValidGetType() {
        assertEquals(FieldType.INTEGER, searcher.getType("organization", "_id"));
        assertEquals(FieldType.STRING, searcher.getType("ticket", "_id"));
        assertEquals(FieldType.INTEGER, searcher.getType("user", "organization_id"));
        assertEquals(FieldType.SARRAY, searcher.getType("user", "tags"));
        assertEquals(FieldType.TIMESTAMP, searcher.getType("user", "last_login_at"));
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
