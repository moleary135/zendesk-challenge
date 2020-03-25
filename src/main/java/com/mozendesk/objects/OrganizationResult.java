package com.mozendesk.objects;

import java.util.List;

public class OrganizationResult {
    public Organization organization;
    public List<User> users;
    public List<Ticket> tickets;

    public String toString(){
        StringBuilder sb = new StringBuilder(organization.prettyString());
        sb.append("Users:\n");
        users.forEach(u -> sb.append(u.toSummaryString()));
        sb.append("Tickets:\n");
        tickets.forEach(t -> sb.append(t.toSummaryString()));
        sb.append("\n");
        return sb.toString();
    }
}
