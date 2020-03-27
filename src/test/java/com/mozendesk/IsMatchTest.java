package com.mozendesk;

import com.mozendesk.objects.field.FieldType;
import com.mozendesk.services.JSONLoader;
import com.mozendesk.services.Searcher;
import org.junit.jupiter.api.BeforeAll;
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

public class IsMatchTest {

    public static Searcher searcher;

    @BeforeAll
    static void setUp(){
        searcher = new Searcher();
    }

    @Test
    public void testIsMatchString() {
        assertEquals(TRUE, searcher.isMatch("", FieldType.STRING, ""));
        assertEquals(TRUE, searcher.isMatch("Mary Margaret O'Leary", FieldType.STRING, "mary margaret o'leary"));

        assertEquals(FALSE, searcher.isMatch("", FieldType.STRING, "123"));
        assertEquals(FALSE, searcher.isMatch("Margaret", FieldType.STRING, ""));
    }

    @Test
    public void testIsMatchInteger() {
        assertEquals(TRUE, searcher.isMatch(123, FieldType.INTEGER, "123"));
        assertEquals(TRUE, searcher.isMatch(0, FieldType.INTEGER, "0"));

        assertEquals(FALSE, searcher.isMatch(123, FieldType.INTEGER, "321"));
    }

    @Test
    public void testIsMatchBoolean() {
        assertEquals(TRUE, searcher.isMatch(TRUE, FieldType.BOOLEAN, "true"));
        assertEquals(TRUE, searcher.isMatch(FALSE, FieldType.BOOLEAN, "FaLsE"));

        assertEquals(FALSE, searcher.isMatch(TRUE, FieldType.BOOLEAN, "false"));
    }

    @Test
    public void testIsMatchTimestamp() {
        DateFormat df = new SimpleDateFormat(JSONLoader.dateFormatString);
        try {
            assertEquals(TRUE, searcher.isMatch(df.parse("2013-08-04T01:03:27 -10:00"), FieldType.TIMESTAMP, "2013-08-04T01:03:27 -10:00"));
            assertEquals(FALSE, searcher.isMatch(df.parse("2013-08-04T01:03:27 -10:00"), FieldType.TIMESTAMP, "2014-08-04T01:03:27 -10:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIsMatchArray() {
        List<String> list = new ArrayList<>(Arrays.asList("Madison", "Milwaukee", "New York", "aßC"));

        assertEquals(TRUE, searcher.isMatch(list, FieldType.SARRAY, "madison"));
        assertEquals(TRUE, searcher.isMatch(list, FieldType.SARRAY, "aßc"));

        assertEquals(FALSE, searcher.isMatch(list, FieldType.SARRAY, "abc"));
    }
}
