package com.mozendesk;

import com.mozendesk.services.PrettyPrinter;
import com.mozendesk.services.Searcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.mozendesk.services.PrettyPrinter.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    public static Searcher searcher;

    @BeforeAll
    static void setUp(){
        searcher = new Searcher();
        try {
            searcher.init("testresources");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDoSearchHelp() {
        assertEquals(HELP_TEXT, App.doSearch("", searcher));
        assertEquals(HELP_TEXT, App.doSearch("help", searcher));
    }

    @Test
    public void testDoSearchPrintFields() {
        assertEquals(INVALID_OBJECT_TYPE_TEXT, App.doSearch("search -fields", searcher));
        assertEquals(PrettyPrinter.getPrettyPrintFields("organization"), App.doSearch("organization -fields", searcher));
        assertEquals(PrettyPrinter.getPrettyPrintFields("ticket"), App.doSearch("ticket -fields yadda yadda yadda", searcher));
        assertEquals(PrettyPrinter.getPrettyPrintFields("user"), App.doSearch("user -fields", searcher));
        assertEquals(INVALID_FIELD_TYPE_TEXT, App.doSearch("user -searchfields", searcher));
    }

    @Test
    public void testDoSearchErrorMessages() {
        assertEquals(INVALID_OBJECT_TYPE_TEXT, App.doSearch("users _id 13", searcher));
        assertEquals(INVALID_FIELD_TYPE_TEXT, App.doSearch("user id 13", searcher));
        assertEquals(INVALID_INTEGER_VALUE_TEXT, App.doSearch("user _id Maggie", searcher));
        assertEquals(INVALID_TIMESTAMP_VALUE_TEXT, App.doSearch("user created_at now()", searcher));
        assertEquals(INVALID_BOOLEAN_VALUE_TEXT, App.doSearch("user verified 1", searcher));
    }
    @Test
    public void testDoSearch() {
        String result = "\n" +
                "Found 1 result(s) for search: organization _id 103\n" +
                "\n" +
                "----------------------------------------------------------\n" +
                "\n" +
                "Id                   103                 \n" +
                "Name                 Plasmos             \n" +
                "Url                  http://initech.zendesk.com/api/v2/organizations/103.json\n" +
                "Details              Non profit          \n" +
                "Created At           Sat May 28 09:40:37 CDT 2016\n" +
                "External Id          e73240f3-8ecf-411d-ad0d-80ca8a84053d\n" +
                "Domain Names         [comvex.com, automon.com, verbus.com, gogol.com]\n" +
                "Shared Tickets       false               \n" +
                "Tags                 [Parrish, Lindsay, Armstrong, Vaughn]\n" +
                "\n" +
                "Users:\n" +
                "\tId                   11                  \n" +
                "\tName                 Shelly Clements     \n" +
                "\n" +
                "\tId                   18                  \n" +
                "\tName                 Adriana Ryan        \n" +
                "\n" +
                "\tId                   46                  \n" +
                "\tName                 Finley Conrad       \n" +
                "\n" +
                "Tickets:\n" +
                "\tId                   3584e2c9-ccd4-4acb-9419-9245891cf398\n" +
                "\tSubject              A Catastrophe in Azerbaijan\n" +
                "\tStatus               closed              \n" +
                "\n" +
                "\tId                   d4c901be-7094-4f65-8a9b-43df949d5344\n" +
                "\tSubject              A Catastrophe in Palau\n" +
                "\tStatus               solved              \n" +
                "\n" +
                "\tId                   4af3bbbd-661f-4348-be25-47c6f7d36009\n" +
                "\tSubject              A Catastrophe in Yugoslavia\n" +
                "\tStatus               hold                \n" +
                "\n" +
                "\tId                   eb169da9-43f9-471e-97de-5f3f424e819f\n" +
                "\tSubject              A Problem in Malaysia\n" +
                "\tStatus               solved              \n" +
                "\n" +
                "\tId                   50dfc8bc-31de-411e-92bf-a6d6b9dfa490\n" +
                "\tSubject              A Problem in South Africa\n" +
                "\tStatus               hold                \n" +
                "\n" +
                "\tId                   25c518a8-4bd9-435a-9442-db4202ec1da4\n" +
                "\tSubject              A Drama in Iraq     \n" +
                "\tStatus               pending             \n" +
                "\n" +
                "----------------------------------------------------------\n" +
                "\n";
        assertEquals(result, App.doSearch("organization _id 103", searcher));
    }
}
