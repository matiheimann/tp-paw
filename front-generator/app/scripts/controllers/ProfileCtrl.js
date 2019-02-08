'use strict';
define(['pawddit', 'services/restService'], function(pawddit) {

	pawddit.controller('ProfileCtrl', ['$scope', '$location', '$routeParams', 'restService', 'profile', 'lastPosts', 'lastComments', 'url', function($scope, $location, $routeParams, restService, profile, lastPosts, lastComments, url) {
		$scope.profile = profile;
		$scope.lastPosts = lastPosts;
		$scope.lastComments = lastComments;
		
		$scope.getImageURL = function(imageid) {
			return url + '/images/' + imageid;
		};

	}]);
});