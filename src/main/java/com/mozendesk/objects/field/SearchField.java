package com.mozendesk.objects.field;

/**
 * Pairs a search field type with the pretty output name.
 * These are the values in the SearchableFields field type maps.
 */
public class SearchField {
    //Output name e.g.'External Id' instead of external_id
    private String prettyName;
    protected FieldType type;

    public SearchField(String prettyName, FieldType type) {
        this.prettyName = prettyName;
        this.type = type;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public FieldType getType() {
        return type;
    }
}
