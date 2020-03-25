package com.mozendesk;

import com.mozendesk.objects.*;
import com.mozendesk.objects.searchable.FieldType;
import com.mozendesk.objects.searchable.SearchableFields;
import com.mozendesk.services.Searcher;

import java.util.*;

import static com.mozendesk.services.PrettyPrinter.*;

/**
 * @TODO documentation including readme
 * @TODO make sure "" searches work and don't break in a comparison against null!
 * @TODO summary strings on objects
 * @TODO optimize performance?
 * Runs the main program
 */
public class App {
    public static final Set<String> searchableObjectTypes = Set.of("organization", "ticket", "user");

    //java -jar application.jar [jsonPath]
    public static void main(String[] args) {
        String jsonFolder = args.length <= 0 ? "resources/" : args[0]; //default contains given json files

        Searcher searcher = new Searcher(jsonFolder);

        Scanner scanner = new Scanner(System.in);
        System.out.println(STARTUP_TEXT);

        String input;
        String[] inputArr;
        List<? extends SearchResult> resultList;

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

                //search and print results
                resultList = searcher.search(inputArr[0], ft, inputArr[1], inputArr[2]);
                System.out.printf(SEARCH_RESULTS_TEXT, resultList.size(), input);
                resultList.forEach(o -> System.out.println(o.prettyString()));
            }
        }    while(!input.equals("exit"));

        System.out.println(GOODBYE_TEXT);
        scanner.close();
        System.exit(0);
    }
}