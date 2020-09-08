package com.mozendesk.objects.field;

import com.mozendesk.objects.IllegalSearchException;
import com.mozendesk.services.JSONLoader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.mozendesk.services.PrettyPrinter.INVALID_TIMESTAMP_VALUE_TEXT;

public class TimestampFieldType extends SearchableField {

    @Override
    public boolean validateInputValue(String inValue) {
        DateFormat df = new SimpleDateFormat(JSONLoader.dateFormatString);
        try {
            df.parse(inValue);
        } catch (ParseException e) {
            throw new IllegalSearchException(INVALID_TIMESTAMP_VALUE_TEXT);
        }
        return true;
    }

    @Override
    public boolean isMatch(Object val, String inValue) {
        DateFormat df = new SimpleDateFormat(JSONLoader.dateFormatString);
        try {
            return val.equals(df.parse(inValue));
        } catch (ParseException e) {
            throw new IllegalSearchException(INVALID_TIMESTAMP_VALUE_TEXT);
        }
    }

    @Override
    public String getPrettyTypeName() {
        return "TIMESTAMP";
    }
}
