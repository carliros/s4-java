# s4-java

## Compile and Run

You can compile with **Maven**

````
mvn package
java -jar target/s4-java-0.1.0.jar
````

And then call the API, for example
````
curl localhost:8080/student/list
````

## API available

### For Students

1. Get List of students
 * Path: `/students` method GET
 * Optional: search parameters
    - firstName : String
    - lastName : String
    - classId : Integer
 * Example: `/students?lastName=Gabriel&classId=1`
   That will return all students of classId "1" with lastName "Gabriel".
2. Create a student
 * Path: `/students` method POST
 * Register the fields on the body request (firstName, lastName).
3. Edit a student
 * Path: `/students/{studentId}` method PUT
 * Register the fields on the body request (firstName, lastName).
4. Delete a student
 * Path: `/students/{studentId}` method DELETE

### For Classes

1. Get list of classes
 * Path `/classes` method GET
 * Optional: search parameters
    - code: String
    - title: String
    - description: String
    - studentId: Integer
 * Example: `/classes?studentId=2`
   That will return al the classes that the student "2" has.
2. Create a class 
 * Path `/classes` method POST
 * Register the fields in the body request (code, title, description)
3. Edit a class 
 * Path: `/classes/{classId}` method PUT
 * Register the fields in the body request (code, title, description)
4. Delete a class
 * Path `/classes/{classId}` method DELETE
5. Register a student into a class
 * Path: `/register/{classId}/{studentId}` method POST
