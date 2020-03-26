package com.mozendesk.objects.results;

import com.mozendesk.objects.Organization;
import com.mozendesk.objects.Ticket;
import com.mozendesk.objects.User;

import java.util.List;

/**
 * A result of a search of Organization objects
 * Contains the users and tickets that belong to the specified organization.
 */
public class OrganizationResult extends SearchResult {
    public Organization organization;
    public List<User> users;
    public List<Ticket> tickets;

    public OrganizationResult(Organization organization, List<User> users, List<Ticket> tickets) {
        this.organization = organization;
        this.users = users;
        this.tickets = tickets;
    }

    public String prettyString() {
        StringBuilder sb = new StringBuilder(organization.prettyString());
        if (users == null) {
            sb.append("No Associated Users\n\n");
        } else {
            sb.append("Users:\n");
            users.forEach(u -> sb.append(u.toSummaryString()));
        }
        if (tickets == null) {
            sb.append("No Associated Tickets\n\n");
        } else {
            sb.append("Tickets:\n");
            tickets.forEach(t -> sb.append(t.toSummaryString()));
        }
        sb.append("\n");
        return sb.toString();
    }
}
