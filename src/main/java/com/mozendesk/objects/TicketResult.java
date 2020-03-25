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
            sb.append("No Associated Organization\n\n");
        } else {
            sb.append("Organization:\n").append(organization.toSummaryString());
        }
        if (assignedTo == null) {
            sb.append("No Assigned User\n\n");
        } else {
            sb.append("Assigned To:\n").append(assignedTo.toSummaryString());
        }
        if (submittedBy == null) {
            sb.append("No Submitted User\n\n");
        } else {
            sb.append("Submitted By:\n").append(submittedBy.toSummaryString());
        }
        sb.append("\n");
        return sb.toString();
    }
}
