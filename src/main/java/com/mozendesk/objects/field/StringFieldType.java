package com.mozendesk.objects.field;
//and then use instanceOf to type check
public class StringFieldType extends SearchableField {

    //Since input is a String by default, always valid
    @Override
    public boolean validateInputValue(String inValue) {
        return true;
    }

    @Override
    public boolean isMatch(Object val, String inValue) {
        return ((String)val).equalsIgnoreCase(inValue);
    }

    @Override
    public String getPrettyTypeName() {
        return "STRING";
    }
}
