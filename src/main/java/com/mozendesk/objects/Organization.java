package com.mozendesk.objects;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.mozendesk.objects.searchable.SearchableFields;

public class Organization extends SearchableObject{

    public Organization(){}

    @Override
    public String toString() {
        return getField("name") + " " + getField("_id");
    }

    //@TODO also need to add relationships so provs need separate class to represent search results?
    public String prettyString() {
        StringBuilder sb = new StringBuilder(200);
        for (String key : SearchableFields.orgFieldTypes.keySet()) {
            sb.append(String.format("%-20s %-20s%n", SearchableFields.orgFieldTypes.get(key).getPrettyName(), getField(key)));
        }
        sb.append("\n");
        return sb.toString();
    }

    //@TODO formatting better, pretty print w/ field names too
    public String toSummaryString() {
        return getField("name") + " " + getField("_id") + "\n";
    }

}
