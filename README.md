## Java Web Application
This project is to have developers follow coding standard for Java Web Application. Please follow the assignment file JWI_Assignment.docx for more details about the features.


## Introduction on Script Migration - Flyway
## System prerequisites

The migration process requires Maven 3.0.x and Git to be able to work.
Check out http://maven.apache.org/download.cgi and http://git-scm.com/download/linux to install these packages if they are not existed

## As a developer, please follow these steps to create sql files

Checkout JavaWebIntroduction project

Create fresh database for the project in case you don't have one. This is for developers to recreate project database environment for development and debugging

* Ensure a user/schema has been created
* Update 'url', 'username' and 'password' properties in /src/main/resources/db/environments/flyway.properties.

Create your SQL files for your new feature or fix

Create new file `V<timestamp>__your_description_goes_here.sql` in /src/main/resources/db/scripts folder.

Update your local development database with SQL files created above

```
$ mvn flyway:migrate
```

Please visit http://flywaydb.org/
