package com.mozendesk.objects;

import java.util.List;

public class TicketResult extends SearchResult {
    public Ticket ticket;
    public Organization organization;
    public User assignedTo;
    public User submittedBy;

    public TicketResult(Ticket ticket, Organization organization, User assignedTo, User submittedBy) {
        this.ticket = ticket;
        this.organization = organization;
        this.assignedTo = assignedTo;
        this.submittedBy = submittedBy;
    }

    public String prettyString() {
        StringBuilder sb = new StringBuilder(ticket.prettyString());

        if (organization == null) {
            sb.append("No Associated Organization");
        } else {
            sb.append("Organization: ").append(organization.toSummaryString());
        }

        if (assignedTo == null) {
            sb.append("No Assigned User");
        } else {
            sb.append("Assigned To: ").append(assignedTo.toSummaryString());
        }

        if (submittedBy == null) {
            sb.append("No Submitted User");
        } else {
            sb.append("Submitted By: ").append(submittedBy.toSummaryString());
        }

        sb.append("\n");
        return sb.toString();
    }
}
