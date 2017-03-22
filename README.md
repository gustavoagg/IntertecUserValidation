# User Validation App
 This application was made with services using Spring-Boot, AngularJS and Boostrap. 
 
 ## How it works
 It has a form to add users with just username and a list of added user to manage them.  You can add new user to the list, but if the username is already used it generates random suggestions. Using the given name it adds to it a "."(point) and 3 random chars from the given username, (Ex. "Jeannette.J3a"), it also has a list of restricted words that can not be contain by the usernames, in this case all the username is regenerated using the same letters in a random selection and aleatory numbers.
 
 ## Requirments: 
 Download or Clone the repository
 
 ## To Test 
 run on a console: `git clone https://github.com/gustavoagg/IntertecUserValidation.git` 
 then: `mvn clean spring-boot:run`
 this will start the server and you can access it on the navigator:
 http://localhost:8080/ 
 
 
