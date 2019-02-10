'use strict';
define(['pawddit', 'services/restService', 'services/navbarService'], function(pawddit) {

	pawddit.controller('ProfileCtrl', ['$scope', '$rootScope', '$location', '$routeParams', 'restService', 'navbarService', 'profile', 'lastPosts', 'lastComments', 'url', function($scope, $rootScope, $location, $routeParams, restService, navbarService, profile, lastPosts, lastComments, url) {
		navbarService.currentPage = 'profile';
		navbarService.currentPageText = profile.username;

		$scope.profile = profile;
		$scope.lastPosts = lastPosts;
		$scope.lastComments = lastComments;
		
		$scope.getImageURL = function(imageid) {
			return url + '/images/' + imageid;
		};

	}]);
});