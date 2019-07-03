'use strict';
define(['pawddit', 'jquery', 'services/restService', 'services/navbarService'], function(pawddit) {

	pawddit.controller('ProfileCtrl', ['$scope', '$rootScope', '$location', '$window', 'restService', 'navbarService', 'profile', 'lastPosts', 'lastComments', 'url', function($scope, $rootScope, $location, $window, restService, navbarService, profile, lastPosts, lastComments, url) {
		$window.document.title = 'Pawddit. | ' + profile.username;
		navbarService.setCurrentPage('profile', profile.username);

		$scope.profile = profile;
		$scope.lastPosts = lastPosts;
		$scope.lastComments = lastComments;

		$scope.selectedOption = 'lastPosts';
		
		$scope.getImageURL = function(imageid) {
			return url + '/images/' + imageid;
		};

		$scope.$on('profile:updated', function() {
        	restService.getProfile(profile.username).then(function(data) {
        		$scope.profile = data;
        	});
		});

	}]);
});
