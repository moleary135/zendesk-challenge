package com.mozendesk.objects;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.mozendesk.services.JSONLoader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Parent class for any object that can be searched.
 */
public abstract class SearchableObject {
    //field name is the key, value is the object created from the json string
    private HashMap<String, Object> fields;

    public SearchableObject() {
        this.fields = new HashMap<>();
    }

    //Tell json mapper to use this setter for any fields that do not have a matching setter on the object
    @JsonAnySetter
    public void setField(String fieldName, Object value) {
        if (value != null) {
            this.fields.put(fieldName, value);
        }
    }

    //Need to specify how to create datetime fields or else the object mapper creates them as Strings.
    @JsonSetter("created_at")
    public void setCreatedAt(Object jsonDate) {
        setDateTimeField("created_at", jsonDate);
    }

    public Object getField(String fieldName) {
        return this.hasField(fieldName) ? this.fields.get(fieldName) : "";
    }

    /**
     * Fails if the value cannot be converted to an Integer
     */
    public Integer getFieldAsInteger(String fieldName) {
        try {
            if (this.hasField(fieldName)) {
                return Integer.parseInt(this.fields.get(fieldName).toString());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean hasField(String field) {
        return this.fields.containsKey(field);
    }

    public void setDateTimeField(String fieldName, Object jsonDate) {
        DateFormat df = new SimpleDateFormat(JSONLoader.dateFormatString);
        try {
            setField(fieldName, df.parse((String) jsonDate));
        } catch (ParseException e) {
            //invalid date format in json file
            e.printStackTrace();
        }
    }
}
