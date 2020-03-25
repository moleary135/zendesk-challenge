package com.mozendesk.objects;

import java.util.List;

public class OrganizationResult extends SearchResult{
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
