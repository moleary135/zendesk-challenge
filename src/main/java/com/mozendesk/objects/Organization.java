package com.mozendesk.objects;

import com.mozendesk.objects.searchable.SearchableFields;

public class Organization extends SearchableObject{

    public Organization(){}

    @Override
    public String toString() {
        return getField("name") + " " + getField("_id");
    }

    //@TODO pretty values aka datetimes and arrays
    public String prettyString() {
        StringBuilder sb = new StringBuilder(200);
        for (String key : SearchableFields.orgFieldTypes.keySet()) {
            sb.append(SearchableFields.orgFieldTypes.get(key).getPrettyName())
                    .append("\t\t")
                    .append(getField(key))
                    .append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}
