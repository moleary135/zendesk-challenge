package com.mozendesk.objects.searchable;

public class SearchField {
    //Output name e.g.'External Id' instead of external_id
    private String prettyName;
    protected FieldType type;

    public SearchField(String prettyName, FieldType type){
        this.prettyName = prettyName;
        this.type = type;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public FieldType getType(){
        return type;
    }
}
