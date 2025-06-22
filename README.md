## Local Development

### Prerequisites

Ensure you have the following installed on your machine:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase/jdk22-archive-downloads.html) (version 22)
- [Maven](https://maven.apache.org/download.cgi)(version 3.8 or above)
- [PostgreSQL](https://www.postgresql.org/download/)

To build the project, navigate to the root directory of the project and run:

```bash
mvn clean install
```
This command will compile the code, run tests, and package

### Running the Services

#### Be sure that port 8081 isn't already in use before running.

#### Be sure PostgrSQL Server is running on port: 5432

To run the project buy Maven run the command
```bash
mvn spring-boot:run 
```
or you can run a jar file. To run the jar file navigate to directory called "target" from root directory of the project and run:
```bash
java -jar CNAFS-0.0.1-SNAPSHOT.jar
```
