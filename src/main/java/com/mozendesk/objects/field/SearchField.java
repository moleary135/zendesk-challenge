package com.mozendesk.objects.field;

/**
 * Pairs a search field type with the pretty output name.
 * These are the values in the SearchableFields field type maps.
 */
public class SearchField {
    //Output name e.g.'External Id' instead of external_id
    private final String prettyName;
    protected final SearchableField type;

    public SearchField(String prettyName, SearchableField sf) {
        this.prettyName = prettyName;
        this.type = sf;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public SearchableField getFieldType() {
        return type;
    }
}
