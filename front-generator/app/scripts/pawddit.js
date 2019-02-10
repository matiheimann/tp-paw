'use strict';
define(['routes',
	'services/dependencyResolverFor',
	'i18n/i18nLoader!',
	'angular',
	'angular-route',
	'angular-cookies',
	'angular-bootstrap',
	'bootstrap',
	'angular-translate',
	'angular-timeago',
	'ngInfiniteScroll'],
	function(config, dependencyResolverFor, i18n) {
		var pawddit = angular.module('pawddit', [
			'ngRoute',
			'ngCookies',
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
				'$httpProvider',
				function($routeProvider, $controllerProvider, $compileProvider, $filterProvider, $provide, $translateProvider, $qProvider, $locationProvider, $httpProvider) {

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

					// $qProvider.errorOnUnhandledRejections(false);
					$locationProvider.hashPrefix('');
					$httpProvider.defaults.withCredentials = true;
				}])
			.run(['$rootScope', function($rootScope) {
					$rootScope.$on('$routeChangeSuccess', function() {
						document.body.scrollTop = document.documentElement.scrollTop = 0;
					});
			}])
			// .value('url', 'http://pawserver.it.itba.edu.ar/paw-2018b-08/api')
			.value('url', 'http://localhost:8080/webapp/api');
			
		return pawddit;
	}
);
