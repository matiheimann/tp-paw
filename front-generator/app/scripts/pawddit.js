'use strict';
define(['routes',
	'services/dependencyResolverFor',
	'i18n/i18nLoader!',
	'angular',
	'angular-route',
	'angular-bootstrap',
	'bootstrap',
	'angular-translate',
	'angular-timeago',
	'ngInfiniteScroll'],
	function(config, dependencyResolverFor, i18n) {
		var pawddit = angular.module('pawddit', [
			'ngRoute',
			'pascalprecht.translate',
			'ui.bootstrap',
			'yaru22.angular-timeago',
			'infinite-scroll'
		]);
		pawddit
			.config(
				['$routeProvider',
				'$controllerProvider',
				'$compileProvider',
				'$filterProvider',
				'$provide',
				'$translateProvider',
				'$qProvider',
				'$locationProvider',
				'timeAgoSettings', 
				function($routeProvider, $controllerProvider, $compileProvider, $filterProvider, $provide, $translateProvider, $qProvider, $locationProvider, timeAgoSettings) {

					pawddit.controller = $controllerProvider.register;
					pawddit.directive = $compileProvider.directive;
					pawddit.filter = $filterProvider.register;
					pawddit.factory = $provide.factory;
					pawddit.service = $provide.service;

					if (config.routes !== undefined) {
						angular.forEach(config.routes, function(route, path) {
							var resolve = dependencyResolverFor(['controllers/' + route.controller]);
							angular.forEach(route.resolve, function(value, key) {
								resolve[key] = value;
							});

							$routeProvider.when(path, {
									templateUrl: route.templateUrl, 
									resolve: resolve,
									controller: route.controller, 
									gaPageTitle: route.gaPageTitle
								}
							);
						});
					}
					if (config.defaultRoutePath !== undefined) {
						$routeProvider.otherwise({redirectTo: config.defaultRoutePath});
					}

					$translateProvider.translations('preferredLanguage', i18n);
					$translateProvider.preferredLanguage('preferredLanguage');

					switch (i18n['Lang.code']) {
						case 'en':
							timeAgoSettings.overrideLang = 'en_US';
							break;
						case 'es':
							timeAgoSettings.overrideLang = 'es_LA';
							break;
						default:
					}

					// $qProvider.errorOnUnhandledRejections(false);
					$locationProvider.hashPrefix('');
				}])
			.run(['$rootScope', function($rootScope) {
					$rootScope.$on('$routeChangeSuccess', function() {
						document.body.scrollTop = document.documentElement.scrollTop = 0;
					});
			}])
			.value('url', 'http://pawserver.it.itba.edu.ar/paw-2018b-08/api')
			// .value('url', 'http://localhost:9000/webapp/api')
			// here we define our unique filter
			.filter('unique', function() {
			   	// we will return a function which will take in a collection
			   	// and a keyname
			   	return function(collection, keyname) {
			      	// we define our output and keys array;
			      	var output = [], 
			        keys = [];
			      
			      	// we utilize angular's foreach function
			      	// this takes in our original collection and an iterator function
			      	angular.forEach(collection, function(item) {
			          	// we check to see whether our object exists
			          	var key = item[keyname];
			          	// if it's not already part of our keys array
			          	if(key !== null && keys.indexOf(key) === -1) {
			              	// add it to our keys array
			              	keys.push(key); 
			              	// push this item to our final output array
			              	output.push(item);
			          	}
			      	});
			      	// return our array which should be devoid of
			      	// any duplicates
			      	return output;
   				};
			});
			
		return pawddit;
	}
);
