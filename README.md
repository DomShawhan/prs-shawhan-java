# PRS System  

This is my PRS System capstone for the MAX Technical Training Maximum Coding Bootcamp.  

## Description  
This is a Purchase Requisition System that enables the users to place requests for necessary work items. Reviewers can view the requests once they are submitted by the user. If a request is under $50, it will be automatically approved, otherwise it will be added to the list of requests to review.

## Getting Started  
### Dependencies  
- Java 17
- Maven
- MySQL Driver
- Spring Data JPA
- Spring Web
- MySql

### Installing
- In MySql Workbench run the file mysqlScripts/prs-create-mysql.sql
- In MySql Workbench run the file mysqlScripts/prs-insert-my-data-mysql.sql
- In MySql Workbench run the file mysqlScripts/prs-user-create-mysql.sql
- In src/main/resources/application.properties, update the `spring.datasource.url` field to the path to the database

### Executing Program
- Open in a SpringToolSuite(Eclipse) workspace
- Right click on src/main/java/com.prs/PrsWebApplication.java
- Click Run As > Spring Boot App

## Authors
- [Dominic Shawhan](https://github.com/DomShawhan)
    - Built to the specifications of [Sean Blessing](https://github.com/sean-blessing)  

## Version History
* v0.3
    * Added getProductByVendorAndPartNumber route
    * Added more validation
* 0.2
    * Added more validation
    * Fixed some bugs
* 0.1
    * Initial Release