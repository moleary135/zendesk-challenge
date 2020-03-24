package com.mozendesk.objects;

import com.fasterxml.jackson.annotation.JsonSetter;

public class User extends SearchableObject {

    public User(){}

    @JsonSetter("last_login_at")
    public void setLastLoginAt(String fieldName, Object jsonDate) {
        setDateTimeField(fieldName, jsonDate);
    }

    @Override
    public String toString() {
        return getField("subject") + " " + getField("created_at") + " " + getField("last_login_at");
    }
}

