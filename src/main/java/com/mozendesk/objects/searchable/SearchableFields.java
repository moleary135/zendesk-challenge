package com.mozendesk.objects.searchable;

import com.mozendesk.services.PrettyPrinter;

import java.util.AbstractMap;
import java.util.Map;

/**
 * Defines the searchable fields and types on each object type
 */
public class SearchableFields {

    //Defines the names and types of the searchable fields for User objects
    public static final Map<String, SearchField> userFieldTypes = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("_id", new SearchField("Id", FieldType.INTEGER)),
            new AbstractMap.SimpleEntry<>("url", new SearchField("Url", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("external_id", new SearchField("External Id", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("name", new SearchField("Name", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("alias", new SearchField("Alias", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("created_at", new SearchField("Created At", FieldType.TIMESTAMP)),
            new AbstractMap.SimpleEntry<>("active", new SearchField("Active", FieldType.BOOLEAN)),
            new AbstractMap.SimpleEntry<>("verified", new SearchField("Verified", FieldType.BOOLEAN)),
            new AbstractMap.SimpleEntry<>("shared", new SearchField("Shared", FieldType.BOOLEAN)),
            new AbstractMap.SimpleEntry<>("locale", new SearchField("Locale", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("timezone", new SearchField("Timezone", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("last_login_at", new SearchField("Last Login At", FieldType.TIMESTAMP)),
            new AbstractMap.SimpleEntry<>("email", new SearchField("Email", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("phone", new SearchField("Phone", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("signature", new SearchField("Signature", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("organization_id", new SearchField("Organization Id", FieldType.INTEGER)),
            new AbstractMap.SimpleEntry<>("tags", new SearchField("Tags", FieldType.SARRAY)),
            new AbstractMap.SimpleEntry<>("suspended", new SearchField("Suspended", FieldType.BOOLEAN)),
            new AbstractMap.SimpleEntry<>("role", new SearchField("Role", FieldType.STRING))
    );

    public static final Map<String, SearchField> ticketFieldTypes = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("_id", new SearchField("Id", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("url", new SearchField("Url", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("external_id", new SearchField("External Id", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("created_at", new SearchField("Created At", FieldType.TIMESTAMP)),
            new AbstractMap.SimpleEntry<>("subject", new SearchField("Subject", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("description", new SearchField("Description", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("priority", new SearchField("Priority", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("status", new SearchField("Status", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("type", new SearchField("Type", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("submitter_id", new SearchField("Submitter Id", FieldType.INTEGER)),
            new AbstractMap.SimpleEntry<>("assignee_id", new SearchField("Assignee Id", FieldType.INTEGER)),
            new AbstractMap.SimpleEntry<>("organization_id", new SearchField("Organization Id", FieldType.INTEGER)),
            new AbstractMap.SimpleEntry<>("tags", new SearchField("Tags", FieldType.SARRAY)),
            new AbstractMap.SimpleEntry<>("has_incidents", new SearchField("Has Incidents", FieldType.BOOLEAN)),
            new AbstractMap.SimpleEntry<>("due_at", new SearchField("Due At", FieldType.TIMESTAMP)),
            new AbstractMap.SimpleEntry<>("via", new SearchField("Via", FieldType.STRING))
    );

    public static final Map<String, SearchField> orgFieldTypes = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("_id", new SearchField("Id", FieldType.INTEGER)),
            new AbstractMap.SimpleEntry<>("url", new SearchField("Url", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("external_id", new SearchField("External Id", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("name", new SearchField("Name", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("domain_names", new SearchField("Domain Names", FieldType.SARRAY)),
            new AbstractMap.SimpleEntry<>("created_at", new SearchField("Created At", FieldType.TIMESTAMP)),
            new AbstractMap.SimpleEntry<>("details", new SearchField("Details", FieldType.STRING)),
            new AbstractMap.SimpleEntry<>("shared_tickets", new SearchField("Shared Tickets", FieldType.BOOLEAN)),
            new AbstractMap.SimpleEntry<>("tags", new SearchField("Tags", FieldType.SARRAY))
    );

    /**
     * Fetches the type of the field on the given object
     * @return the FieldType of the field on the given object or
     * @throws if nonsensical values
     */
    public static FieldType getType(String object, String field) {
        switch(object) {
            case "organization":
                if (orgFieldTypes.containsKey(field)) {
                    return orgFieldTypes.get(field).getType();
                }
                break;
            case "user" :
                if (userFieldTypes.containsKey(field)) {
                    return userFieldTypes.get(field).getType();
                }
                break;
            case "ticket" :
                if (ticketFieldTypes.containsKey(field)) {
                    return ticketFieldTypes.get(field).getType();
                }
        }
        return null;
    }

    public static String getPrettyPrintFields(String objectType) {
        StringBuilder sb = new StringBuilder(100);
        switch(objectType) {
            case "organization":
                orgFieldTypes.keySet().forEach(key -> sb.append(key).append(" "));
                break;
            case "user":
                userFieldTypes.keySet().forEach(key -> sb.append(key).append(" "));
                break;
            case "ticket":
                ticketFieldTypes.keySet().forEach(key -> sb.append(key).append(" "));
                break;
            default:
                return PrettyPrinter.INVALID_OBJECT_TYPE_TEXT;
        }


        return sb.toString();
    }
}
