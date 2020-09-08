package com.mozendesk.objects.field;

/**
 * Represents the type of a field on a searchable object.
 */
public abstract class SearchableField {

    /**
     * Validates the search value against the field type
     * @return true if inValue is a possible value,
     * else throws IllegalSearchException
     */
    public abstract boolean validateInputValue(String inValue);

    /**
     * Compares two values
     * Requires that validation on input is already done.
     * @param val from the Searchable object
     * @param inValue user input value
     * @return true if the values are equivalent
     */
    public abstract boolean isMatch(Object val, String inValue);

    // return pretty name for UI search type list
    public abstract String getPrettyTypeName();
}
