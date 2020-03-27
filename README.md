#Zendesk Coding Challenge

Simple search program to search through the objects defined in the .json files located under resources.

## Build
Note: If trying to compile with gradle or gradlew, please make sure your JAVA_HOME is set to Java 13

    > export JAVA_HOME=`/usr/libexec/java_home -v 13` #sets to java 13 
    > export PATH=$JAVA_HOME/bin:$PATH 

The build uses Gradle to create a fat jar. A Gradle wrapper is included in the source if you don't already have Gradle and will handle fetching dependencies.

Mac/Linux

    > ./gradlew
Windows

    > gradlew.bat
    
The jar is output to the build/libs folder.

To run unit tests:

    > ./gradlew clean test #Mac/Linux
    > gradlew.bat clean test #Windows

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
    
	> object -fields
searchValue - the value to match, or if omitted matches on empty values
 
The searchValue includes everything after the searchField, so do not put quotes around multi word searches.


## Search Matching
String - case insensitive matching

Date - expects input of the same format as the json dates, yyyy-MM-ddTHH:mm:ss X 
    e.g. 2016-04-14T08:32:31 -10:00

Boolean - true | false

Integer - a valid integer

Array - matches if any of the array values matches the input value

Examples

    > organization created_at 2016-04-14T08:32:31 -10:00     # returns all organizations created at 2016-04-14T08:32:31 -10:00
    > user tags Madison    					 # returns all users whose tags contain Madison
    > ticket due_at						 # returns all tickets whose due_at field is empty


Versions Used:

Java 13.0.2

Gradle 6.2.2

Jackson (FasterXML) 2.6.3

JUnit 5.3.1
