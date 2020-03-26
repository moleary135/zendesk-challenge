package com.mozendesk.objects;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.mozendesk.objects.field.SearchableFields;

public class Ticket extends SearchableObject {

    public Ticket() {}

    @JsonSetter("due_at")
    public void setDueAt(Object jsonDate) {
        setDateTimeField("due_at", jsonDate);
    }

    //Returns the String of the object containing all fields
    public String prettyString() {
        StringBuilder sb = new StringBuilder(200);
        for (String key : SearchableFields.ticketFieldTypes.keySet()) {
            sb.append(String.format("%-20s %-20s%n", SearchableFields.ticketFieldTypes.get(key).getPrettyName(), getField(key)));
        }
        sb.append("\n");
        return sb.toString();
    }

    //Returns the String of the object with only most important fields for use in object relationships
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
