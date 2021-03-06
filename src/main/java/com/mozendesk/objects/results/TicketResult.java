package com.mozendesk.objects.results;

import com.mozendesk.objects.Organization;
import com.mozendesk.objects.Ticket;
import com.mozendesk.objects.User;

/**
 * A result of a search on Ticket objects
 * Contains the organization the ticket belongs to, as well as,
 * the user it is assigned to and the user it was submitted by.
 */
public class TicketResult extends SearchResult {
    public final Ticket ticket;
    public final Organization organization;
    public final User assignedTo;
    public final User submittedBy;

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
        return sb.toString();
    }
}
