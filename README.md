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
 
 To add restricted word you can use this url:
  `http://localhost:8080/restricted/add/{word} ` just change {word} for the one you want, example to add the restricted word "drunk", use this link http://localhost:8080/restricted/add/drunk
  
  ## Use with Mysql (Optional)
  Aplicattion is ready to be use with Mysql you just need to follow the next steps:
  1. Install Mysql and create a new database
  2. Uncomment the next code in the aplicattion properties
  ```
#spring.datasource.url=jdbc:mysql://localhost/{bd_name}
#spring.datasource.username={user}
#spring.datasource.password={password}
#spring.datasource.driver-class-name=com.mysql.jdbc.Driver
  ```
  3. Change the values `{bd_name}`,`{user}` and `{password}` with yours
  4. The app will create the schema the first time it runs. 
 
 
