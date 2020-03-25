package com.mozendesk.objects;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.mozendesk.objects.searchable.SearchableFields;

public class User extends SearchableObject {

    public User(){}

    @JsonSetter("last_login_at")
    public void setLastLoginAt(Object jsonDate) {
        setDateTimeField("last_login_at", jsonDate);
    }

    public String prettyString() {
        StringBuilder sb = new StringBuilder(200);
        for (String key : SearchableFields.userFieldTypes.keySet()) {
            sb.append(SearchableFields.userFieldTypes.get(key).getPrettyName())
                    .append("\t\t")
                    .append(getField(key))
                    .append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return getField("subject") + " " + getField("created_at") + " " + getField("last_login_at");
    }

    public String toSummaryString() {
        return getField("subject") + " " + getField("created_at") + " " + getField("last_login_at") + "\n";
    }
}

