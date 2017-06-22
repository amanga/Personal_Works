(function() {
	
	'use strict';

	angular.module('contactApp', [
        'contactControllers',
        'contactServices',
        'ui.router',
        'angular-loading-bar'
    ])
    
    .config(function($stateProvider, $urlRouterProvider) {
		$stateProvider
			.state('contact', {
				url: '/contact',
				templateUrl: 'views/assets/subviews/contact.htm'
			})
			.state('contact.viewContact', {
				views:{
					"rightView":{
						url: '/viewContacts',
						templateUrl: 'views/assets/subviews/viewContacts.htm',
						controller : 'ContactInfoController',
						resolve :{
							contacts : function(Contact){
								return Contact.query().$promise;
							}
						}
					}
				}
			})
			.state('contact.viewContact.editContact1', {
				url: '/editContact1',
				templateUrl: 'views/assets/subviews/editContact.htm',
				controller : 'EditContactInfoController'
			})
			.state('contact.editContact', {
				views:{
					"rightView":{
						url: '/editContact',
						templateUrl: 'views/assets/subviews/editContact.htm',
						controller : 'EditContactInfoController'
					}
				}
			}).state('contact.viewUpload', {
				views:{
					"rightView":{
						url: '/upload',
						templateUrl: 'views/assets/subviews/upload.htm'
					}
				}
			}).state('contact.viewWebsocket', {
				views:{
					"rightView":{
						url: '/websocket',
						templateUrl: 'views/assets/subviews/websocket.htm',
						controller : "WebsocketController"
					}
				}
			});
			
			$urlRouterProvider.otherwise('/contact');
    });
	
})();
