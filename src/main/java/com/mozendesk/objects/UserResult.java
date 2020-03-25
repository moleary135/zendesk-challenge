package com.mozendesk.objects;

import java.util.List;

public class UserResult extends SearchResult {
    public User user;
    public Organization organization;
    public List<Ticket> assignedTickets;
    public List<Ticket> submittedTickets;

    public UserResult(User user, Organization organization, List<Ticket> assignedTickets, List<Ticket> submittedTickets) {
        this.user = user;
        this.organization = organization;
        this.assignedTickets = assignedTickets;
        this.submittedTickets = submittedTickets;
    }

    public String prettyString() {
        StringBuilder sb = new StringBuilder(user.prettyString());

        if (organization == null) {
            sb.append("No Associated Organization");
        } else {
            sb.append("Organization: ").append(organization.toSummaryString());
        }

        if (assignedTickets == null) {
            sb.append("No Assigned Tickets");
        } else {
            sb.append("Assigned Tickets:\n");
            assignedTickets.forEach(t -> sb.append(t.toSummaryString()));
        }

        if (submittedTickets == null) {
            sb.append("No Submitted Tickets");
        } else {
            sb.append("Submitted Tickets:\n");
            submittedTickets.forEach(t -> sb.append(t.toSummaryString()));
        }

        sb.append("\n");
        return sb.toString();
    }
}
