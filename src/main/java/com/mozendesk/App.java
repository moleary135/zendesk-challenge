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

        String input = scanner.nextLine();

        while(!input.equals("exit")) {
            System.out.println(doSearch(input, searcher));
            input = scanner.nextLine();
        }

        System.out.println(GOODBYE_TEXT);
        scanner.close();
        System.exit(0);
    }

    public static String doSearch(String input, Searcher searcher) {

        List<? extends SearchResult> resultList;
        String[] inputArr = input.split(" ", 3);

        if (input.equals("help") || inputArr.length < 2) {
            return HELP_TEXT;
        } else if (inputArr[1].equals("-fields")) {
           return PrettyPrinter.getPrettyPrintFields(inputArr[0]);
        } else {
            String inValue = inputArr.length != 3 ? "" : inputArr[2];
            try {
                //validate search parameters
                FieldType ft = searcher.getType(inputArr[0], inputArr[1]);
                searcher.validateInValue(ft, inValue);

                //search and print results
                resultList = searcher.search(inputArr[0], ft, inputArr[1], inValue);
                return PrettyPrinter.getPrettyResults(resultList, input);
            } catch (IllegalSearchException e) {
                return e.getMessage();
            }
        }
    }
}