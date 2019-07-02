'use strict';
define(['pawddit', 'jquery', 'services/restService', 'services/navbarService'], function(pawddit) {

	pawddit.controller('ProfileCtrl', ['$scope', '$rootScope', '$location', '$window', 'restService', 'navbarService', 'profile', 'lastPosts', 'lastComments', 'url', function($scope, $rootScope, $location, $window, restService, navbarService, profile, lastPosts, lastComments, url) {
		$window.document.title = 'Pawddit. | ' + profile.username;
		navbarService.setCurrentPage('profile', profile.username);

		$scope.profile = profile;
		$scope.lastPosts = lastPosts;
		$scope.lastComments = lastComments;
		
		$scope.getImageURL = function(imageid) {
			return url + '/images/' + imageid;
		};

		$scope.$on('profile:updated', function() {
        	restService.getProfile(profile.username).then(function(data) {
        		$scope.profile = data;
        	});
		});

		$scope.lastNumberComments = function() {
			$("#lastNComments").addClass("selected-history");
			$("#lastNPosts").removeClass("selected-history");
			if(!$("#profile-comments").is(":visible") && $("#profile-posts").is(":visible")){
				$("#profile-posts").toggle(400);
				$("#profile-comments").toggle(400);
			}
		};
		
		$scope.lastNumberPosts = function() {
			$("#lastNPosts").addClass("selected-history");
			$("#lastNComments").removeClass("selected-history");
			if(!$("#profile-posts").is(":visible") && $("#profile-comments").is(":visible")){	
				$("#profile-comments").toggle(400);
				$("#profile-posts").toggle(400);
			}
		};

	}]);
});
