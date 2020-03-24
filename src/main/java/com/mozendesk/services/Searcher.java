package com.mozendesk.services;

import com.mozendesk.objects.SearchableObject;
import com.mozendesk.objects.searchable.SearchableFields;

import java.util.List;
import java.util.stream.Stream;

public class Searcher {

    //Yours passes because you're not using generics.
    //If SubClass extends ParentClass then Subclass IS A ParentClass and can be used as such.
    // List<Subclass> however is NOT a subtype of List<ParentClass> -
    // and hence any such methods that want to take a List of objects as if they were a List<ParentClass>
    // has to declare it as List<? extends ParentClass>, which allows you pass a List<SubClass> in


    //@TODO format stream out put and need to get related items for each match

    /**
     * @return a filtered stream of matches
     */
    public Stream<? extends SearchableObject> search(List<? extends SearchableObject> objs, String inObject, String inField, String inValue) {
        switch(SearchableFields.getType(inObject, inField)) {
            case STRING:
                return objs.stream().filter(o -> ((String)o.getField(inField)).equalsIgnoreCase(inValue));
            case INTEGER:
                int i = Integer.parseInt(inValue); //@TODO validate elsewhere???
                return objs.stream().filter(o -> ((Integer)o.getField(inField)) == i);
        }

        return null; //@TODO throw
    }


    public Stream<? extends SearchableObject> addRelations(List<? extends SearchableObject> objs, String inObject) {
        switch(inObject) {
            case "organization":
                //@TODO add list of users and tickets to print
                break;

        }
        return objs.stream();
    }

    }
