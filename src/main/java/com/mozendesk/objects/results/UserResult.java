package com.mozendesk.objects.results;

import com.mozendesk.objects.Organization;
import com.mozendesk.objects.Ticket;
import com.mozendesk.objects.User;

import java.util.List;

/**
 * A result of a search of User objects.
 * Contains the organization that the User belongs to, as well as,
 * the list of tickets assigned to the user and the list of tickets
 * submitted by the user.
 */
public class UserResult extends SearchResult {
    public final User user;
    public final Organization organization;
    public final List<Ticket> assignedTickets;
    public final List<Ticket> submittedTickets;

    public UserResult(User user, Organization organization, List<Ticket> assignedTickets, List<Ticket> submittedTickets) {
        this.user = user;
        this.organization = organization;
        this.assignedTickets = assignedTickets;
        this.submittedTickets = submittedTickets;
    }

    public String prettyString() {
        StringBuilder sb = new StringBuilder(user.prettyString());
        if (organization == null) {
            sb.append("No Associated Organization\n\n");
        } else {
            sb.append("Organization:\n").append(organization.toSummaryString());
        }
        if (assignedTickets == null) {
            sb.append("No Assigned Tickets\n\n");
        } else {
            sb.append("Assigned Tickets:\n");
            assignedTickets.forEach(t -> sb.append(t.toSummaryString()));
        }
        if (submittedTickets == null) {
            sb.append("No Submitted Tickets\n\n");
        } else {
            sb.append("Submitted Tickets:\n");
            submittedTickets.forEach(t -> sb.append(t.toSummaryString()));
        }
        return sb.toString();
    }
}
