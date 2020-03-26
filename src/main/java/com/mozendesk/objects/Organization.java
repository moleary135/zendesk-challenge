package com.mozendesk.objects;

import com.mozendesk.objects.field.SearchableFields;

public class Organization extends SearchableObject{

    public Organization() {}

    //Returns the String of the object containing all fields
    public String prettyString() {
        StringBuilder sb = new StringBuilder(200);
        for (String key : SearchableFields.orgFieldTypes.keySet()) {
            sb.append(String.format("%-20s %-20s%n", SearchableFields.orgFieldTypes.get(key).getPrettyName(), getField(key)));
        }
        sb.append("\n");
        return sb.toString();
    }

    //Returns the String of the object with only most important fields for use in object relationships
    public String toSummaryString() {
        StringBuilder sb = new StringBuilder(200);
        String[] fieldsToPrint = new String[] {"_id", "name"};
        for (String key : fieldsToPrint) {
            sb.append(String.format("\t%-20s %-20s%n", SearchableFields.orgFieldTypes.get(key).getPrettyName(), getField(key)));
        }
        sb.append("\n");
        return sb.toString();
    }
}
