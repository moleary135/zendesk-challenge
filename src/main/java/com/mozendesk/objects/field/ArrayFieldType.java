package com.mozendesk.objects.field;

import java.util.List;

public class ArrayFieldType extends SearchableField {

    private SearchableField arrayType;

    public ArrayFieldType(SearchableField sf) {
        this.arrayType = sf;
    }

    public SearchableField getArrayType() {
        return arrayType;
    }

    @Override
    public boolean validateInputValue(String inValue) {
        return this.getArrayType().validateInputValue(inValue);
    }

    @Override
    public boolean isMatch(Object val, String inValue) {
        List<?> list = (List<?>)val;
        return list.stream().anyMatch(e -> this.getArrayType().isMatch(e, inValue));
    }

    @Override
    public String getPrettyTypeName() {
        return "ARRAY[" + this.arrayType.getPrettyTypeName() + "]";
    }
}
