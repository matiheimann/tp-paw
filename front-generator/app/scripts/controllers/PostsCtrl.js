'use strict';
define(['pawddit', 'services/restService', 'services/navbarService'], function(pawddit) {

	pawddit.controller('PostsCtrl', ['$scope', '$rootScope', '$location', '$window', 'restService', 'navbarService', 'group', 'posts', 'url', function($scope, $rootScope, $location, $window, restService, navbarService, group, posts, url) {
		navbarService.page = 1;
		navbarService.sort = 'new';
		navbarService.time = 'all';
		navbarService.feed = navbarService.feed || false;

		if (group) {
			$window.document.title = 'Pawddit. | ' + group.name;
			$scope.group = group;
			navbarService.currentPage = 'group';
			navbarService.currentPageText = group.name;
		} else if (navbarService.feed) {
			$window.document.title = 'Pawddit. | Home';
			navbarService.currentPage = 'feedPosts';
			navbarService.currentPageText = 'dropdown.button.myfeed.message';
		} else {
			$window.document.title = 'Pawddit. | Home';
			navbarService.currentPage = 'allPosts';
			navbarService.currentPageText = 'dropdown.button.all.message';
		}

		$scope.posts = posts;

		$scope.subscribe = function(name) {
			restService.subscribeGroup(name).then(function(data) {
				updateGroup(name);
				$rootScope.$broadcast('userSubs:updated');
			});
		};

		$scope.unsubscribe = function(name) {
			restService.unsubscribeGroup(name).then(function(data) {
				updateGroup(name);
				$rootScope.$broadcast('userSubs:updated');
			});
		};

		function updateGroup(name) {
			restService.getGroup(name).then(function(data) {
				$scope.group = data;
			});
		}

		function updatePosts() {
			navbarService.page = 1;
			document.body.scrollTop = document.documentElement.scrollTop = 0;
			$scope.loadingPosts = false;
			$scope.noMorePosts = false;
			var params = {page: navbarService.page, sort: navbarService.sort, time: navbarService.time};
			if (navbarService.feed) {
				restService.getMyFeedPosts(params).then(function(data) {
					$scope.posts = data;
				});
			} else {
				restService.getPosts(params).then(function(data) {
					$scope.posts = data;
				});
			}
		}

		$scope.$on('posts:updated', function() {
			updatePosts();
		});

		$scope.loadMorePosts = function() {
			$scope.loadingPosts = true;
			navbarService.page++;
			var params = {page: navbarService.page, sort: navbarService.sort, time: navbarService.time};
			if (group) {
				restService.getGroupPosts(group.name, params).then(function(data) {
						if (data.length > 0) {
							$scope.posts.push.apply($scope.posts, data);
							$scope.noMorePosts = data.length < 5;
						} else {
							$scope.noMorePosts = true;
						}
						$scope.loadingPosts = false;
					}).catch(function(response) {
						$scope.loadingPosts = false;
				});
			} else if (navbarService.feed) {
				restService.getMyFeedPosts(params).then(function(data) {
					if (data.length > 0) {
						$scope.posts.push.apply($scope.posts, data);
						$scope.noMorePosts = data.length < 5;
					} else {
						$scope.noMorePosts = true;
					}
					$scope.loadingPosts = false;
				}).catch(function(response) {
					$scope.loadingPosts = false;
				});
			} else {
				restService.getPosts(params).then(function(data) {
					if (data.length > 0) {
						$scope.posts.push.apply($scope.posts, data);
						$scope.noMorePosts = data.length < 5;
					} else {
						$scope.noMorePosts = true;
					}
					$scope.loadingPosts = false;
				}).catch(function(response) {
					$scope.loadingPosts = false;
				});
			}
		};

	}]);
});
