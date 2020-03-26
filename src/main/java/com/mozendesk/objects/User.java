package com.mozendesk.objects;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.mozendesk.objects.field.SearchableFields;

import static com.mozendesk.services.PrettyPrinter.DOUBLE_COLUMNS_TEXT;
import static com.mozendesk.services.PrettyPrinter.INDENTED_DOUBLE_COLUMNS_TEXT;

public class User extends SearchableObject {

    public User() {}

    @JsonSetter("last_login_at")
    public void setLastLoginAt(Object jsonDate) {
        setDateTimeField("last_login_at", jsonDate);
    }

    //Returns the String of the object containing all fields
    public String prettyString() {
        StringBuilder sb = new StringBuilder(200);
        for (String key : SearchableFields.userFieldPrintList) {
            sb.append(String.format(DOUBLE_COLUMNS_TEXT, SearchableFields.userFieldTypes.get(key).getPrettyName(), getField(key)));
        }
        sb.append("\n");
        return sb.toString();
    }

    //Returns the String of the object with only most important fields for use in object relationships
    public String toSummaryString() {
        StringBuilder sb = new StringBuilder(200);
        String[] fieldsToPrint = new String[] {"_id", "name"};
        for (String key : fieldsToPrint) {
            sb.append(String.format(INDENTED_DOUBLE_COLUMNS_TEXT, SearchableFields.userFieldTypes.get(key).getPrettyName(), getField(key)));
        }
        sb.append("\n");
        return sb.toString();
    }
}

