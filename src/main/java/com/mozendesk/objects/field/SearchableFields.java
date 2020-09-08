package com.mozendesk.objects.field;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.AbstractMap;

/**
 * Defines the searchable fields and types on each object that extends SearchableObject
 */
public class SearchableFields {
    //Consistent order to print fields in when pretty printing
    public static final List<String> userFieldPrintList = new ArrayList<>(
            Arrays.asList("_id", "name", "alias", "url", "external_id", "created_at",
                    "active", "verified", "shared", "locale", "timezone", "last_login_at", "email",
                    "phone", "signature", "organization_id", "tags", "suspended", "role"));

    public static final Map<String, SearchField> userFieldTypes = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("_id", new SearchField("Id", new IntegerFieldType())),
            new AbstractMap.SimpleEntry<>("url", new SearchField("Url", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("external_id", new SearchField("External Id", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("name", new SearchField("Name", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("alias", new SearchField("Alias", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("created_at", new SearchField("Created At", new TimestampFieldType())),
            new AbstractMap.SimpleEntry<>("active", new SearchField("Active", new BooleanFieldType())),
            new AbstractMap.SimpleEntry<>("verified", new SearchField("Verified", new BooleanFieldType())),
            new AbstractMap.SimpleEntry<>("shared", new SearchField("Shared", new BooleanFieldType())),
            new AbstractMap.SimpleEntry<>("locale", new SearchField("Locale", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("timezone", new SearchField("Timezone", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("last_login_at", new SearchField("Last Login At", new TimestampFieldType())),
            new AbstractMap.SimpleEntry<>("email", new SearchField("Email", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("phone", new SearchField("Phone", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("signature", new SearchField("Signature", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("organization_id", new SearchField("Organization Id", new IntegerFieldType())),
            new AbstractMap.SimpleEntry<>("tags", new SearchField("Tags", new ArrayFieldType(new StringFieldType()))),
            new AbstractMap.SimpleEntry<>("suspended", new SearchField("Suspended", new BooleanFieldType())),
            new AbstractMap.SimpleEntry<>("role", new SearchField("Role", new StringFieldType()))
    );

    public static final List<String> ticketFieldPrintList = new ArrayList<>(
            Arrays.asList("_id", "subject", "description", "created_at", "due_at", "url", "external_id",
                    "priority", "status", "type", "submitter_id", "assignee_id", "organization_id",
                    "tags", "has_incidents", "via"));


    public static final Map<String, SearchField> ticketFieldTypes = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("_id", new SearchField("Id", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("url", new SearchField("Url", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("external_id", new SearchField("External Id", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("created_at", new SearchField("Created At", new TimestampFieldType())),
            new AbstractMap.SimpleEntry<>("subject", new SearchField("Subject", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("description", new SearchField("Description", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("priority", new SearchField("Priority", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("status", new SearchField("Status", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("type", new SearchField("Type", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("submitter_id", new SearchField("Submitter Id", new IntegerFieldType())),
            new AbstractMap.SimpleEntry<>("assignee_id", new SearchField("Assignee Id", new IntegerFieldType())),
            new AbstractMap.SimpleEntry<>("organization_id", new SearchField("Organization Id", new IntegerFieldType())),
            new AbstractMap.SimpleEntry<>("tags", new SearchField("Tags", new ArrayFieldType(new StringFieldType()))),
            new AbstractMap.SimpleEntry<>("has_incidents", new SearchField("Has Incidents", new BooleanFieldType())),
            new AbstractMap.SimpleEntry<>("due_at", new SearchField("Due At", new TimestampFieldType())),
            new AbstractMap.SimpleEntry<>("via", new SearchField("Via", new StringFieldType()))
    );

    public static final List<String> orgFieldPrintList = new ArrayList<>(
            Arrays.asList("_id", "name", "url", "details", "created_at",
                    "external_id", "domain_names", "shared_tickets", "tags"));

    public static final Map<String, SearchField> orgFieldTypes = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("_id", new SearchField("Id", new IntegerFieldType())),
            new AbstractMap.SimpleEntry<>("url", new SearchField("Url", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("external_id", new SearchField("External Id", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("name", new SearchField("Name", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("domain_names", new SearchField("Domain Names", new ArrayFieldType(new StringFieldType()))),
            new AbstractMap.SimpleEntry<>("created_at", new SearchField("Created At", new TimestampFieldType())),
            new AbstractMap.SimpleEntry<>("details", new SearchField("Details", new StringFieldType())),
            new AbstractMap.SimpleEntry<>("shared_tickets", new SearchField("Shared Tickets", new BooleanFieldType())),
            new AbstractMap.SimpleEntry<>("tags", new SearchField("Tags", new ArrayFieldType(new StringFieldType())))
    );
}
