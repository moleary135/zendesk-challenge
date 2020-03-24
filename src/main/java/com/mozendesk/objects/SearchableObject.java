package com.mozendesk.objects;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.mozendesk.services.JSONLoader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public abstract class SearchableObject {

    //field name is the key, value is the object created by json mapper
    private HashMap<String, Object> fields;

    public SearchableObject() {
        this.fields = new HashMap<>();
    }

    //Tell json mapper to use this setter for any fields the do not have a matching field on the object
    @JsonAnySetter
    public void setField(String fieldName, Object value){
        this.fields.put(fieldName, value);
    }

    //Need to specify how to create datetime fields or else the object mapper creates them as Strings.
    @JsonSetter("created_at")
    public void setCreatedAt(String fieldName, Object jsonDate) {
        setDateTimeField(fieldName, jsonDate);
    }

    public Object getField(String fieldName){
        return this.hasField(fieldName) ? this.fields.get(fieldName) : "";
    }

    public boolean hasField(String field) {
        return this.fields.containsKey(field);
    }

    public HashMap<String, Object> getFields(){
        return this.fields;
    }

    public void setDateTimeField(String fieldName, Object jsonDate) {
        DateFormat df = new SimpleDateFormat(JSONLoader.dateFormatString);
        try {
            setField(fieldName, df.parse((String) jsonDate));
        } catch (ParseException e){
            //invalid date format in json file
            e.printStackTrace();
        }
    }
}
