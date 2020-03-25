package com.mozendesk;

import com.mozendesk.objects.Organization;
import com.mozendesk.objects.SearchableObject;
import com.mozendesk.objects.Ticket;
import com.mozendesk.objects.User;
import com.mozendesk.objects.searchable.FieldType;
import com.mozendesk.objects.searchable.SearchableFields;
import com.mozendesk.services.JSONLoader;
import com.mozendesk.services.Searcher;

import java.io.IOException;
import java.util.*;

import static com.mozendesk.services.PrettyPrinter.*;

/**
 * @TODO documentation including readme
 * @TODO make sure absolute paths work as well,
 * @TODO make sure "" searches work and don't break in a comparison against null!
 * @TODO keep map, make sure index on relations
 * TODO make sure parallel streams are thread safe, don't make final because that's not realistic for future extensibility
 *
 * Runs the main program
 */
public class App {
    public static final Set<String> searchableObjectTypes = Set.of("organization", "ticket", "user");

    //java -jar application.jar [jsonPath]
    public static void main(String[] args) {
        JSONLoader loader = new JSONLoader();
        Map<Integer, Organization> organizations = new HashMap<>();
        Map<String, Ticket> tickets = new HashMap<>();
        Map<Integer, User> users = new HashMap<>();
        String jsonFolder = args.length > 0 ? args[0] : "resources/";

        try {
            organizations = loader.loadOrgs(jsonFolder);
            tickets = loader.loadTickets(jsonFolder);
            users = loader.loadUsers(jsonFolder);
        } catch (IOException e) {
            System.out.printf(JSON_DIR_NOT_FOUND_TEXT, jsonFolder);
            System.exit(-1);
        }

        Searcher searcher = new Searcher(tickets, users);

        Scanner scanner = new Scanner(System.in);
        System.out.println(STARTUP_TEXT);

        String input;
        String[] inputArr;
        List<? extends SearchableObject> resultList;

        do {
            input = scanner.nextLine();
            inputArr = input.split(" ", 3);

            if (input.equals("help") || inputArr.length < 2 || (inputArr.length < 3 && !inputArr[1].equals("-searchFields"))) {
                System.out.println(HELP_TEXT);
            } else if (!searchableObjectTypes.contains(inputArr[0])) { //validate object type
                System.out.println(INVALID_OBJECT_TYPE_TEXT);
            } else if (inputArr[1].equals("-searchFields")) {
                System.out.println(SearchableFields.getPrettyPrintFields(inputArr[0]));
            } else {
                //validate field on object and value on field
                FieldType ft = SearchableFields.getType(inputArr[0], inputArr[1]);
                if (ft == null) {
                    System.out.println(INVALID_FIELD_TYPE_TEXT);
                    continue;
                } else if (!searcher.validateInValue(ft, inputArr[2])) {
                    System.out.println(INVALID_VALUE_FOR_TYPE_TEXT);
                    continue;
                }

                //search
                switch (inputArr[0]) {
                    case "organization":
                        resultList = searcher.search(organizations.values(), ft, inputArr[1], inputArr[2]);
                        System.out.printf(SEARCH_RESULTS_TEXT, resultList.size(), input);
                                resultList.forEach(o -> System.out.println(o.prettyString()));
                        break;
                    case "user":
                        (searcher.search(users.values(), ft, inputArr[1], inputArr[2]))
                                .forEach(o -> System.out.println(o.prettyString()));
                        break;
                    case "ticket":
                        (searcher.search(tickets.values(), ft, inputArr[1], inputArr[2]))
                                .forEach(o -> System.out.println(o.prettyString()));
                        break;
                }
            }
        }    while(!input.equals("exit"));

        System.out.println(GOODBYE_TEXT);
        scanner.close();
        System.exit(0);
    }
}