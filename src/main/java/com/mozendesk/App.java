package com.mozendesk;

import com.mozendesk.objects.Organization;
import com.mozendesk.objects.Ticket;
import com.mozendesk.objects.User;
import com.mozendesk.services.JSONLoader;
import com.mozendesk.services.Searcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @TODO documentation including readme
 * @TODO make sure absolute paths work as well
 * @TODO make sure "" searches work and don't break in a comparison against null!
 * @TODO keep map, make sure index on relations
 * TODO make sure parallel streams are thread safe, don't make final because that's not realistic for future extensibility
 *
 * Runs the main program
 */
public class App {

    public static void main(String[] args) {
        //@TODO index on relationships between objects, or convert list to map of objects/ create map
        //@TODO output to std out instead of requiring console so can run in IDEs normally

        JSONLoader loader = new JSONLoader();
        Searcher searcher = new Searcher();

        List<Organization> organizations = new ArrayList<>();
        List<Ticket> tickets = new ArrayList<>();
        List<User> users = new ArrayList<>();

        String jsonFolder = "resources/"; //organizations.json

        try {
            organizations = loader.loadOrgs(jsonFolder);
            tickets = loader.loadTickets(jsonFolder);
            users = loader.loadUsers(jsonFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Zendesk Search\n");
        System.out.println("Instructions: \n Type 'exit' at anytime to quit the program.");
        System.out.println("Search objects using the format:");
        System.out.println("\t object searchField searchValue");
        System.out.println("Possible values for object are 'organization', 'user', and 'ticket'.");
        System.out.println("To see possible search fields for a specific object type use:");
        System.out.println("\t object -searchFields");

        String input = scanner.nextLine();

        String inObject= "organization";
        String inField= "name";
        String inValue = "Nutralab";
        while(!input.equals("exit")) {
            if (input.contains("searchFields")) {
                //list possible search fields
            } else {
                searcher.search(users, inObject, inField, inValue);
            }
            input = scanner.nextLine();
        }

        System.out.println("Goodbye!");
        scanner.close();
        System.exit(0);
    }
}