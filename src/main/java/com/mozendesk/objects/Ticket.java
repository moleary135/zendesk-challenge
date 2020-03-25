package com.mozendesk.objects;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.mozendesk.objects.searchable.SearchableFields;

public class Ticket extends SearchableObject {

    public Ticket() {}

    @JsonSetter("due_at")
    public void setDueAt(Object jsonDate) {
        setDateTimeField("due_at", jsonDate);
    }

    public String prettyString() {
        StringBuilder sb = new StringBuilder(200);
        for (String key : SearchableFields.ticketFieldTypes.keySet()) {
            sb.append(String.format("%-20s %-20s%n", SearchableFields.ticketFieldTypes.get(key).getPrettyName(), getField(key)));
        }
        sb.append("\n");
        return sb.toString();
    }

    public String toSummaryString() {
        StringBuilder sb = new StringBuilder(200);
        String[] fieldsToPrint = new String[] {"_id", "subject", "status"};
        for (String key : fieldsToPrint) {
            sb.append(String.format("%-20s %-20s%n", SearchableFields.ticketFieldTypes.get(key).getPrettyName(), getField(key)));
        }
        sb.append("\n");
        return sb.toString();
    }
}
