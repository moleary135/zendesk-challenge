package com.mozendesk.services;

//Keep main output strings in one place (with the exception of field names)
// for possible future translations
public class PrettyPrinter {
    public static final String HELP_TEXT = "Instructions: \nType 'exit' at anytime to quit the program.\n" +
            "Search for objects using the format:\n" +
            "\t object searchField searchValue\n" +
            "Possible values for object are 'organization', 'user', and 'ticket'.\n" +
            "To see possible search fields for a specific object type use:\n" +
            "\t object -searchFields\n";
    public static final String STARTUP_TEXT = "\nWelcome to Zendesk Search\n\n" + HELP_TEXT;
    public static final String GOODBYE_TEXT = "Goodbye!";
    public static final String INVALID_OBJECT_TYPE_TEXT = "Please specify a valid object type.";
    public static final String INVALID_FIELD_TYPE_TEXT = "Please specify a valid field for this type.";
    public static final String INVALID_VALUE_FOR_TYPE_TEXT = "Please specify a valid value for the given field.";
    public static final String SEARCH_RESULTS_TEXT = "Found %d results for search: %s\n";
}
