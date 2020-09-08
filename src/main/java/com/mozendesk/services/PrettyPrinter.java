package com.mozendesk.services;

import com.mozendesk.objects.field.SearchableFields;
import com.mozendesk.objects.results.SearchResult;

import java.util.List;

import static com.mozendesk.services.JSONLoader.dateFormatString;

/**
 * Keep non-object related output strings in one place
 */
public class PrettyPrinter {
    public static final String HELP_TEXT = "\nInstructions: \nType 'exit' at anytime to quit the program.\n" +
            "Search for objects using the format:\n" +
            "\t object searchField [searchValue]\n" +
            "Possible values for object are 'organization', 'user', and 'ticket'.\n" +
            "Leaving searchValue blank with find matches whose given searchField is empty." +
            "To see possible search fields for a specific object type use:\n" +
            "\t object -fields\n";
    public static final String STARTUP_TEXT = "\nWelcome to Zendesk Search\n" + HELP_TEXT;
    public static final String GOODBYE_TEXT = "Goodbye!\n";
    public static final String INVALID_OBJECT_TYPE_TEXT = "Please specify a valid object type.\n";
    public static final String INVALID_FIELD_TYPE_TEXT = "Please specify a valid field for this type.\n";
    public static final String INVALID_TIMESTAMP_VALUE_TEXT = "Please specify a timestamp with format: " + dateFormatString + "\n";
    public static final String INVALID_BOOLEAN_VALUE_TEXT = "Please use true or false for boolean values.\n";
    public static final String INVALID_INTEGER_VALUE_TEXT = "Please use value integer values for numerical fields.\n";
    public static final String SEARCH_RESULTS_TEXT = "%nFound %d result(s) for search: %s%n%n";
    public static final String JSON_DIR_NOT_FOUND_TEXT = "'organizations.json', 'tickets.json', and/or 'users.json' were not found in given directory '%s'%n";
    public static final String SEPARATOR_TEXT = "----------------------------------------------------------\n\n";
    public static final String DOUBLE_COLUMNS_TEXT = "%-20s %-20s%n";
    public static final String INDENTED_DOUBLE_COLUMNS_TEXT = "\t%-20s %-20s%n";

    /**
     * Returns String of possible search fields for an object.
     */
    public static String getPrettyPrintFields(String objectType) {
        StringBuilder sb = new StringBuilder(100);
        sb.append(SEPARATOR_TEXT);
        switch(objectType) {
            case "organization":
                SearchableFields.orgFieldTypes.keySet().forEach(key ->
                        sb.append(String.format(DOUBLE_COLUMNS_TEXT, key, SearchableFields.orgFieldTypes.get(key).getFieldType().getPrettyTypeName())));
                break;
            case "user":
                SearchableFields.userFieldTypes.keySet().forEach(key ->
                        sb.append(String.format(DOUBLE_COLUMNS_TEXT, key, SearchableFields.userFieldTypes.get(key).getFieldType().getPrettyTypeName())));
                break;
            case "ticket":
                SearchableFields.ticketFieldTypes.keySet().forEach(key ->
                        sb.append(String.format(DOUBLE_COLUMNS_TEXT, key, SearchableFields.ticketFieldTypes.get(key).getFieldType().getPrettyTypeName())));
                break;
            default:
                return INVALID_OBJECT_TYPE_TEXT;
        }
        return sb.toString();
    }

    /**
     * Given search results, returns a pretty human readable string
     */
    public static String getPrettyResults(List<? extends SearchResult> resultList, String input) {
        StringBuilder sb = new StringBuilder(300);
        sb.append(String.format(SEARCH_RESULTS_TEXT, resultList.size(), input));
        sb.append(SEPARATOR_TEXT);
        resultList.forEach(o -> sb.append(o.prettyString()).append(SEPARATOR_TEXT));
        return sb.toString();
    }
}
