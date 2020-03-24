package com.mozendesk.objects;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Ticket extends SearchableObject {

    public Ticket(){}

    @JsonSetter("due_at")
    public void setDueAt(String fieldName, Object jsonDate) {
        setDateTimeField(fieldName, jsonDate);
    }

    @Override
    public String toString() {
        return getField("subject") + " " + getField("created_at");
    }
}
