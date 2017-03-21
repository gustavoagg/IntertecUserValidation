'use strict';

App.factory('UserService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllUsers: function() {
					return $http.get('http://localhost:8080/user/')
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Error while fetching users');
										return $q.reject(errResponse);
									}
							);
			},
		    
		    createUser: function(user){
					return $http.post('http://localhost:8080/user/', user)
							.then(
									function(response){

										console.error('trajo alternativas'+response.data);
										return response.data;
									}, 
									function(errResponse){
										
										if(errResponse.data!=null){
											console.error('Error conflict with User');
											return errResponse.data;
										}
										else{
											console.error('Error while creating user');
											return $q.reject(errResponse);
										}
										
									}
							);
		    },
		    
		    
			deleteUser: function(id){
					return $http.delete('http://localhost:8080/user/'+id)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Error while deleting user');
										return $q.reject(errResponse);
									}
							);
			}
		
	};

}]);
