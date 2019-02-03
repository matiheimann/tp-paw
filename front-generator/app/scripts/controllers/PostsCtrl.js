'use strict';
define(['pawddit', 'services/restService'], function(pawddit) {

	pawddit.controller('PostsCtrl', ['$scope', '$location', '$routeParams', 'restService', 'group', 'posts', 'postsPageCount', 'url', function($scope, $location, $routeParams, restService, group, posts, postsPageCount, url) {
		if (group) {
			$scope.group = group;
		}
		$scope.posts = posts;
		$scope.postsPageCount = postsPageCount.pageCount;

		$scope.page = $routeParams.page || 1;
		$scope.sort = $routeParams.sort || 'new';
		$scope.time = $routeParams.time || 'all';
	}]);
});
