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

1. `/student/list` method GET
2. `/student/create` method POST
2. `/student/edit` method POST
2. `/student/delete/{id}` method POST
2. `/student/searchByFirstName/{firstName}` method POST
2. `/student/searchByLastName/{lastName}` method POST
2. `/student/retrieveStudentsForClass/{subjectId}` method POST

### For Classes or Subjects

1. `/subject/list` method GET
1. `/subject/create` method POST
1. `/subject/edit` method POST
1. `/subject/delete/{id}` method POST
1. `/subject/register/{subjectId}/{studentId}` method POST
1. `/subject/retrieveSubjectsForStudent/{studentId}` method POST
1. `/subject/searchByCode/{code}` method POST
1. `/subject/searchByTitle/{title}` method POST
1. `/subject/searchByDescription/{description}` method POST