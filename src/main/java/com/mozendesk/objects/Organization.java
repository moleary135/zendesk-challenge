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
        return sb.toString();
    }
}

//            new AbstractMap.SimpleEntry<>("_id", FieldType.INTEGER),
//            new AbstractMap.SimpleEntry<>("url", FieldType.STRING),
//            new AbstractMap.SimpleEntry<>("external_id", FieldType.STRING),
//            new AbstractMap.SimpleEntry<>("name", FieldType.STRING),
//            new AbstractMap.SimpleEntry<>("domain_names", FieldType.SARRAY),
//            new AbstractMap.SimpleEntry<>("created_at", FieldType.TIMESTAMP),
//            new AbstractMap.SimpleEntry<>("details", FieldType.STRING),
//            new AbstractMap.SimpleEntry<>("shared_tickets", FieldType.BOOLEAN),
//            new AbstractMap.SimpleEntry<>("tags", FieldType.SARRAY)