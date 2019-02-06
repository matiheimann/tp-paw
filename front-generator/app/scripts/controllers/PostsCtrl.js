'use strict';
define(['pawddit', 'services/restService'], function(pawddit) {

	pawddit.controller('PostsCtrl', ['$scope', '$location', '$routeParams', 'restService', 'group', 'posts', 'postsPageCount', 'url', function($scope, $location, $routeParams, restService, group, posts, postsPageCount, url) {
		if (group) {
			$scope.group = group.data;
		}

		$scope.posts = posts.data;
		$scope.postsPageCount = postsPageCount.data.pageCount;

		$scope.page = $routeParams.page || 1;
		$scope.sort = $routeParams.sort || 'new';
		$scope.time = $routeParams.time || 'all';

		$scope.subscribe = function(name) {
			restService.subscribeGroup(name).then(function(response) {
				updateGroup(name);
			});
		};

		$scope.unsubscribe = function(name) {
			restService.unsubscribeGroup(name).then(function(response) {
				updateGroup(name);
			});
		};

		function updateGroup(name) {
			restService.getGroup(name).then(function(response) {
				$scope.group = response.data;
			});
		}

	}]);
});
