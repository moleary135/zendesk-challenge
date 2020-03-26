#Zendesk Coding Challenge

Simple search program to search through the objects defined in the .json files located under resources.

To create a fat jar using gradle

	> gradle fatJar

## Usage

	> java -jar jarName.jar [jsonPath]

[jsonPath] is optional and when not given will use the default files under the resources folder.

To quit the program:

    > exit

To run a search:

    > object searchField [searchValue]

object - {organization | user | ticket}

searchField - a field on an object

To see a list of possible search fields for a given object type:
    
	> object -searchFields
searchValue - the value to match, or if omitted searches for where the given searchField has no value set
 
The searchValue includes everything after the searchField, so do not put quotes around multi word searches.


## Search Matching
String - case insensitive matching

Date - expects input of the same format as the json dates, yyyy-MM-ddTHH:mm:ss X 
    e.g. 2016-04-14T08:32:31 -10:00

Boolean - true | false

Integer - a valid integer

Array - match if any of the array values matches the input value

Examples

    > organization created_at 2016-04-14T08:32:31 -10:00     # returns all organizations created at 2016-04-14T08:32:31 -10:00
    > user tags Madison    					 # returns all users whose tags contain Madison
    > ticket due_at						 # returns all tickets whose due_at field is empty

Note: If trying to compile with gradle or gradlew, Gradle is currently incompatible with Java 14!

Versions Used:

Java 13.0.2

Gradle 6.2.2

Jackson (FasterXML) 2.6.3
