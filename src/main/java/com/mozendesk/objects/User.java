package com.mozendesk.objects;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.mozendesk.objects.searchable.SearchableFields;

public class User extends SearchableObject {

    public User() {}

    @JsonSetter("last_login_at")
    public void setLastLoginAt(Object jsonDate) {
        setDateTimeField("last_login_at", jsonDate);
    }

    public String prettyString() {
        StringBuilder sb = new StringBuilder(200);
        for (String key : SearchableFields.userFieldTypes.keySet()) {
            sb.append(String.format("%-20s %-20s%n", SearchableFields.userFieldTypes.get(key).getPrettyName(), getField(key)));
        }
        sb.append("\n");
        return sb.toString();
    }

    public String toSummaryString() {
        StringBuilder sb = new StringBuilder(200);
        String[] fieldsToPrint = new String[] {"_id", "name"};
        for (String key : fieldsToPrint) {
            sb.append(String.format("%-20s %-20s%n", SearchableFields.userFieldTypes.get(key).getPrettyName(), getField(key)));
        }
        sb.append("\n");
        return sb.toString();
    }
}

