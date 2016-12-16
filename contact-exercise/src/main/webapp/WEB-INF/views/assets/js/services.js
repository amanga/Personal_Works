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
	]);

})();