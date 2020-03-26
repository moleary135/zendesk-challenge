package com.mozendesk;

import com.mozendesk.objects.IllegalSearchException;
import com.mozendesk.objects.results.SearchResult;
import com.mozendesk.objects.field.FieldType;
import com.mozendesk.services.PrettyPrinter;
import com.mozendesk.services.Searcher;

import java.io.IOException;
import java.util.*;

import static com.mozendesk.services.PrettyPrinter.*;

/**
 * Note: If using gradle, need to make sure JAVA_HOME doesn't point to Java 14!
 * Runs the main program
 */
public class App {
    //java -jar application.jar [jsonPath]
    public static void main(String[] args) {
        String jsonFolder = args.length <= 0 ? "resources/" : args[0]; //default contains given json files

        Searcher searcher = new Searcher();
        try {
            searcher.init(jsonFolder);
        } catch (IOException e) {
            System.err.printf(JSON_DIR_NOT_FOUND_TEXT, jsonFolder);
            System.exit(-1);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println(STARTUP_TEXT);

        String input;
        String[] inputArr;
        List<? extends SearchResult> resultList;
        String inValue;

        do {
            input = scanner.nextLine();
            inputArr = input.split(" ", 3);

            if (input.equals("help") || inputArr.length < 2) {
                System.out.println(HELP_TEXT);
            } else if (!searcher.isValidObjectType(inputArr[0])) { //validate object type
                System.out.println(INVALID_OBJECT_TYPE_TEXT);
            } else if (inputArr[1].equals("-searchFields")) {
                System.out.println(PrettyPrinter.getPrettyPrintFields(inputArr[0]));
            } else {
                inValue = inputArr.length != 3 ? "" : inputArr[2];
                //validate field on object and value on field

                FieldType ft;
                try {
                    ft = searcher.getType(inputArr[0], inputArr[1]);
                } catch (IllegalSearchException e) {
                    System.out.println(INVALID_FIELD_TYPE_TEXT);
                    continue;
                }
                if (ft == null) {
                    System.out.println(INVALID_FIELD_TYPE_TEXT);
                    continue;
                } else if (!searcher.validateInValue(ft, inValue)) {
                    System.out.println(INVALID_VALUE_FOR_TYPE_TEXT);
                    continue;
                }

                //search and print results
                resultList = searcher.search(inputArr[0], ft, inputArr[1], inValue);
                System.out.printf(SEARCH_RESULTS_TEXT, resultList.size(), input);
                System.out.println(SEPARATOR_TEXT);
                resultList.forEach(o -> {
                    System.out.print(o.prettyString());
                    System.out.println(SEPARATOR_TEXT);
                });
            }
        }    while(!input.equals("exit"));

        System.out.println(GOODBYE_TEXT);
        scanner.close();
        System.exit(0);
    }
}