package com.mozendesk.objects.field;

import com.mozendesk.objects.IllegalSearchException;
import static com.mozendesk.services.PrettyPrinter.INVALID_INTEGER_VALUE_TEXT;

public class IntegerFieldType extends SearchableField {

    @Override
    public boolean validateInputValue(String inValue) {
        try {
            Integer.parseInt(inValue);
        } catch (NumberFormatException e) {
            throw new IllegalSearchException(INVALID_INTEGER_VALUE_TEXT);
        }
        return true;
    }

    @Override
    public boolean isMatch(Object val, String inValue) {
        int i = Integer.parseInt(inValue);
        return (Integer)val == i;
    }

    @Override
    public String getPrettyTypeName() {
        return "INTEGER";
    }
}
