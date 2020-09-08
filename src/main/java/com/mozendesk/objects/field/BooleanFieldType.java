package com.mozendesk.objects.field;

import com.mozendesk.objects.IllegalSearchException;

import static com.mozendesk.services.PrettyPrinter.INVALID_BOOLEAN_VALUE_TEXT;

public class BooleanFieldType extends SearchableField {

    @Override
    public boolean validateInputValue(String inValue) {
        if (!(inValue.equalsIgnoreCase("true") || inValue.equalsIgnoreCase("false"))) {
            throw new IllegalSearchException(INVALID_BOOLEAN_VALUE_TEXT);
        }
        return true;
    }

    @Override
    public boolean isMatch(Object val, String inValue) {
        boolean b = Boolean.parseBoolean(inValue);
        return (Boolean)val == b;
    }

    @Override
    public String getPrettyTypeName() {
        return "BOOLEAN";
    }
}
