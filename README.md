# gr-assignment

This is the homework assignment for Guaranteed Rate.

### Run Unit Tests

    make test

### Create an executable jar

    make uberjar

To run the command line application:

- java -cp target/gr-sol.jar clojure.main -m gr.command`

To run the HTTP application:

- java -cp target/gr-sol.jar clojure.main -m gr.http

### Run the 3 views on the table.txt file

    make testclj

### Run the 3 views on the table.txt using the uberjar

    make testjar

### Start the standalone webserver on port 8080

    make http

#### HTTP Requests

- **POST** */records* - Post a single data line in any of the 3 formats supported by your existing code

- **GET** */* - returns "Guaranteed Rate Homework Assignment"

- **GET** */records/color* - returns records sorted by favorite color

- **GET** */records/birthdate* - returns records sorted by birthdate

- **GET** */records/name* - returns records sorted by last name

- **GET** */records* - returns internal record store
