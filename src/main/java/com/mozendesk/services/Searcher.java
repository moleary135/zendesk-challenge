package com.mozendesk.services;

import com.mozendesk.objects.Organization;
import com.mozendesk.objects.SearchableObject;
import com.mozendesk.objects.searchable.FieldType;
import com.mozendesk.objects.searchable.SearchableFields;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Searcher {

    //@TODO format stream out put and need to get related items for each match

//    public Stream<? extends SearchableObject> addRelations(List<? extends SearchableObject> objs, String inObject) {
//        switch(inObject) {
//            case "organization":
//                //@TODO add list of users and tickets to print
//                break;
//
//        }
//        return objs.stream();
//    }

    /**
     * @return true if inValue is a possible value given the FieldType, else false
     */
    public boolean validateInValue(FieldType ft, String inValue) {
        switch(ft) {
            case INTEGER:
                try {
                    Integer.parseInt(inValue);
                } catch (NumberFormatException e){
                    return false;
                }
                break;
            case BOOLEAN:
                return (inValue.equalsIgnoreCase("true") || inValue.equalsIgnoreCase("false"));
            case TIMESTAMP:
                DateFormat df = new SimpleDateFormat(JSONLoader.dateFormatString);
                try {
                    df.parse(inValue);
                } catch (ParseException e) {
                    return false;
                }
        }
        return true;
    }

    public List<? extends SearchableObject> search(List<? extends SearchableObject> objs, FieldType ft, String inField, String inValue) {
        switch(ft) {
            case STRING:
                return objs.stream().filter(o -> ((String)o.getField(inField)).equalsIgnoreCase(inValue)).collect(Collectors.toList());
            case INTEGER:
                int i = Integer.parseInt(inValue);
                return objs.stream().filter(o -> ((Integer)o.getField(inField)) == i).collect(Collectors.toList());
            case BOOLEAN:
                boolean b = Boolean.parseBoolean(inValue);
                return objs.stream().filter(o -> ((Boolean)o.getField(inField)) == b).collect(Collectors.toList());
            case TIMESTAMP:
                DateFormat df = new SimpleDateFormat(JSONLoader.dateFormatString);
                try {
                    Date d = df.parse(inValue);
                    return objs.stream().filter(o -> o.getField(inField).equals(d)).collect(Collectors.toList());
                } catch (ParseException e){
                    e.printStackTrace();
                }
            case SARRAY: //@TODO make custom searchablearraylist type that extend searchableobject?
                return objs.stream().filter(o -> ((ArrayList<String>)o.getField(inField)).stream().anyMatch(e -> e.equalsIgnoreCase(inValue))).collect(Collectors.toList());
        }
        return null; //@TODO throw
    }
}
