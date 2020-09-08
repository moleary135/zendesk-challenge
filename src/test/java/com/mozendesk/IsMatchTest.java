package com.mozendesk;

import com.mozendesk.objects.field.*;
import com.mozendesk.services.JSONLoader;
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

    @Test
    public void testIsMatchString() {
        SearchableField sf = new StringFieldType();
        assertEquals(TRUE, sf.isMatch("", ""));
        assertEquals(TRUE, sf.isMatch("Mary Margaret O'Leary", "mary margaret o'leary"));

        assertEquals(FALSE, sf.isMatch("", "123"));
        assertEquals(FALSE, sf.isMatch("Margaret", ""));
    }

    @Test
    public void testIsMatchInteger() {
        SearchableField sf = new IntegerFieldType();
        assertEquals(TRUE, sf.isMatch(123, "123"));
        assertEquals(TRUE, sf.isMatch(0, "0"));

        assertEquals(FALSE, sf.isMatch(123, "321"));
    }

    @Test
    public void testIsMatchBoolean() {
        SearchableField sf = new BooleanFieldType();
        assertEquals(TRUE, sf.isMatch(TRUE, "true"));
        assertEquals(TRUE, sf.isMatch(FALSE, "FaLsE"));

        assertEquals(FALSE, sf.isMatch(TRUE, "false"));
    }

    @Test
    public void testIsMatchTimestamp() {
        SearchableField sf = new TimestampFieldType();
        DateFormat df = new SimpleDateFormat(JSONLoader.dateFormatString);
        try {
            assertEquals(TRUE, sf.isMatch(df.parse("2013-08-04T01:03:27 -10:00"), "2013-08-04T01:03:27 -10:00"));
            assertEquals(FALSE, sf.isMatch(df.parse("2013-08-04T01:03:27 -10:00"), "2014-08-04T01:03:27 -10:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIsMatchArray() {
        SearchableField sf = new ArrayFieldType(new StringFieldType());
        List<String> list = new ArrayList<>(Arrays.asList("Madison", "Milwaukee", "New York", "aßC"));

        assertEquals(TRUE, sf.isMatch(list, "madison"));
        assertEquals(TRUE, sf.isMatch(list, "aßc"));

        assertEquals(FALSE, sf.isMatch(list, "abc"));
    }

    @Test
    public void testIsMatchIntArray() {
        SearchableField sf = new ArrayFieldType(new IntegerFieldType());
        List<Integer> list = new ArrayList<>(Arrays.asList(1234, 8675309, 624443));

        assertEquals(TRUE, sf.isMatch(list, "1234"));
        assertEquals(TRUE, sf.isMatch(list, "624443"));

        assertEquals(FALSE, sf.isMatch(list, "0"));
    }
}
