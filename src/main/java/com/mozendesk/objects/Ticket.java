package com.mozendesk.objects;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.mozendesk.objects.searchable.SearchableFields;

public class Ticket extends SearchableObject {

    public Ticket(){}

    @JsonSetter("due_at")
    public void setDueAt(String fieldName, Object jsonDate) {
        setDateTimeField(fieldName, jsonDate);
    }

    public String prettyString() {
        StringBuilder sb = new StringBuilder(200);
        for (String key : SearchableFields.ticketFieldTypes.keySet()) {
            sb.append(SearchableFields.ticketFieldTypes.get(key).getPrettyName())
                    .append("\t\t")
                    .append(getField(key))
                    .append("\n\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getField("subject") + " " + getField("created_at");
    }
}
