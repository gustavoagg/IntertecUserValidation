'use strict';

App.controller('UserController', ['$scope', 'UserService', function($scope, UserService) {
          var self = this;
          self.user={id:null,username:''};
          self.users=[];
          self.suggested=null;
              
          self.fetchAllUsers = function(){
              UserService.fetchAllUsers()
                  .then(
      					       function(d) {
      						        self.users = d;
      					       },
            					function(errResponse){
            						console.error('Error while fetching Users');
            					}
      			       );
          };
           
          self.createUser = function(user){
              UserService.createUser(user)
		              .then(
		            		  function(d) {
    						        self.suggested = d;
    						        self.fetchAllUsers();
    					       }
                      , 
				              function(errResponse){
					               console.error('Error while creating User.');
				              }	
                  );
          };


         self.deleteUser = function(id){
              UserService.deleteUser(id)
		              .then(
				              self.fetchAllUsers, 
				              function(errResponse){
					               console.error('Error while deleting User.');
				              }	
                  );
          };

          self.fetchAllUsers();

          self.submit = function() {
              if(self.user.id==null){
                  console.log('Saving New User', self.user);    
                  self.createUser(self.user);
              }else{
                  self.updateUser(self.user, self.user.id);
                  console.log('User updated with id ', self.user.id);
              }
              self.reset();
          };
              
              
          self.remove = function(id){
              console.log('id to be deleted', id);
              if(self.user.id === id) {//clean form if the user to be deleted is shown there.
                 self.reset();
              }
              self.deleteUser(id);
          };
          
          self.suggest = function(name){
        	  self.reset();
        	  console.log("nombre es"+name);
              self.user.username = name;
          };

          
          self.reset = function(){
              self.user={id:null,username:''};
              $scope.myForm.$setPristine(); //reset Form
          };

      }]);
