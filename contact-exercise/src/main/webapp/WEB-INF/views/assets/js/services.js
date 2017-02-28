(function() {

	'use strict';
	
	angular.module('contactServices', ['ngResource'])

	.constant('serverConfig', {		
		baseUrl: document.getElementsByTagName('base')[0].href
    })
	
	.factory('Contact', ['$resource', 'serverConfig',
		function ($resource, serverConfig) {
			return $resource('contacts');
		}
	])
	.factory('UploadFile', ['$resource', 'serverConfig',
		function ($resource, serverConfig) {
			return $resource('uploadfile');
		}
	])
	//Upload related functionality..
	.directive('fileModel',['$parse',function($parse){
		return{
			restrict: 'A'
			,link: function(scope, element, attrs){
				var model = $parse(attrs.fileModel);
	            var modelSetter = model.assign;
	            element.bind('change', function(){
	                scope.$apply(function(){
	                    modelSetter(scope, element[0].files[0]);
	                });
	            });
			}
		};
	}]);

})();