package com.mozendesk.objects;

import com.mozendesk.objects.searchable.SearchableFields;

public class Organization extends SearchableObject{

    public Organization() {}

    public String prettyString() {
        StringBuilder sb = new StringBuilder(200);
        for (String key : SearchableFields.orgFieldTypes.keySet()) {
            sb.append(String.format("%-20s %-20s%n", SearchableFields.orgFieldTypes.get(key).getPrettyName(), getField(key)));
        }
        sb.append("\n");
        return sb.toString();
    }

    //Only show most important fields when looking at in terms of relationship to ticket or user
    public String toSummaryString() {
        StringBuilder sb = new StringBuilder(200);
        String[] fieldsToPrint = new String[] {"_id", "name"};
        for (String key : fieldsToPrint) {
            sb.append(String.format("%-20s %-20s%n", SearchableFields.orgFieldTypes.get(key).getPrettyName(), getField(key)));
        }
        sb.append("\n");
        return sb.toString();
    }
}
