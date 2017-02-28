(function() {
	
	'use strict';
	
	angular.module('contactControllers', ['ngResource', 'ngSanitize', 'contactServices', 'ui.select', 'ui.bootstrap'])
	
    .controller('ContactInfoController', ['$scope','$modal', '$log','contacts', 
		function ($scope,$modal,$log,contacts) {
			$log.info("Contact Information Controller.");
			$log.info(contacts);
			$scope.contactItems = contacts;
		}
	])
	.controller('ModelContactInfoController', ['$scope','$modal', '$log', 
		function ($scope,$modal,$log,contacts) {
			$log.info("Edit Contact Information Controller.");			
		
			$scope.add = function(){
				$log.info("Add new Contact Information");
				var modalInstance = $modal.open({
		  	        templateUrl: 'views/assets/subviews/editContact.htm',
		  	        controller: 'EditContactInfoController',
		  	        size: 'lg',
		  	        resolve: {
		  	        	selectedContact: function() {
		  	        		return angular.copy(null);
		  	        	},
		  	        	createContact: function(){
		  	        		return true;
		  	        	}
		  	        }
		  	      });
			}
			$scope.selectedContact = function(contactItem){
				$log.info("Selected Contact Information");
				var modalInstance = $modal.open({
		  	        templateUrl: 'views/assets/subviews/editContact.htm',
		  	        controller: 'EditContactInfoController',
		  	        size: 'lg',
		  	        resolve: {
		  	        	selectedContact: function() {
		  	        		return angular.copy(contactItem);
		  	        	},
		  	        	createContact: function(){
		  	        		return false;
		  	        	}
		  	        }
		  	      });
			};
		}
	])
	.controller('EditContactInfoController', 
		function($scope, $modalInstance,$log, $resource, selectedContact,createContact) {
		
			$scope.isCreate = createContact;
			if(createContact){
				var ContactInformation = $resource("contacts");
  	  			var newContactInformation = new ContactInformation();
  	  			var adress = new Array();
  	  			newContactInformation.adress = adress;
  	  			$scope.contactItem = newContactInformation;
			}else{
				$scope.contactItem = selectedContact;
			}
			
			$scope.save = function(){
				$scope.contactItem.$save().then(function(){
					$log.info("Save is done.");
					$scope.cancel();
				},function(error){
					$log.info("Error while saving.");
				});
			};
			
			$scope.update = function(){
				$scope.contactItem.$save().then(function(){
					$log.info("update is done.");
					$scope.cancel();
				},function(error){
					$log.info("Error while saving.");
				});
			};
			
			$scope.cancel = function() {
				$modalInstance.dismiss('cancel');
			};
			
		}
	).controller('UploadController', function($scope, $modal, $log,$resource,$http){
		$log.info("Upload Controller.");
		
		$scope.uploadFile = function(){
			$log.info("Upload File...");
			var myFile = $scope.myFile;
			console.log("file is :");
			console.dir(myFile);
			
			var uploadUrl = document.getElementsByTagName('base')[0].href+"uploadfile";
			console.log(uploadUrl);
  			var newUploadRequest = new FormData();
  			newUploadRequest.append('file', myFile);
  			newUploadRequest.append('firstname', $scope.firstName);
  			newUploadRequest.append('lastname', $scope.lastName);
  			newUploadRequest.append('email', $scope.email);
  			newUploadRequest.append('clientid', "xyzClient");
  			
  			$http.post(uploadUrl, newUploadRequest,{transformRequest: angular.identity,
  	            headers: {'Content-Type': undefined}
  			})
  			.success(function(){
  				console.log("success");
  			})
  			.error(function(){
  				console.log("error");
  			});
  			
		};
	});
})();
