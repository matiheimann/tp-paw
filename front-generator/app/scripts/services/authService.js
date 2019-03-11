'use strict';
define(['pawddit', 'jquery', 'services/restService', 'services/storageService'], function(pawddit) {

	pawddit.factory('authService', ['$http', '$q', 'restService', 'storageService', 'url', function($http, $q, restService, storageService, url) {
		function formConfig() {
			return {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			};
		}

		return {
			loginUser: function(username, password, rememberMe) {
				var data = {jUsername: username, jPassword: password};
				return $http.post(url + '/users/login', Object.keys(data).length ? jQuery.param(data) : '', formConfig())
					.then(function(response) {
						storageService.setAuthenticationToken(response.headers('X-AUTH-TOKEN'), rememberMe);
					})
					.catch(function(response) {
						return $q.reject(response);
					});
			},
			logoutUser: function() {
				storageService.destroy();
			}
		};
	}]);
	
});