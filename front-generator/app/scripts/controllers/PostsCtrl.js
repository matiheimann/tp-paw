'use strict';
define(['pawddit', 'services/restService', 'services/navbarService'], function(pawddit) {

	pawddit.controller('PostsCtrl', ['$scope', '$rootScope', '$location', '$translate', '$window', 'restService', 'navbarService', 'group', 'posts', 'url', function($scope, $rootScope, $location, $translate, $window, restService, navbarService, group, posts, url) {
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
			$translate('homePage.title').then(function(translatedValue) {
				$window.document.title = 'Pawddit. | ' + translatedValue;
			});
			navbarService.currentPage = 'feedPosts';
			navbarService.currentPageText = 'dropdown.button.myfeed.message';
		} else {
			$translate('homePage.title').then(function(translatedValue) {
				$window.document.title = 'Pawddit. | ' + translatedValue;
			});
			navbarService.currentPage = 'allPosts';
			navbarService.currentPageText = 'dropdown.button.all.message';
		}

		$scope.posts = posts;

		$scope.subscribe = function(group) {
			restService.subscribeGroup(group.name).then(function(data) {
				updateGroup(group);
				$rootScope.$broadcast('userSubs:updated');
			});
		};

		$scope.unsubscribe = function(group) {
			restService.unsubscribeGroup(group.name).then(function(data) {
				updateGroup(group);
				$rootScope.$broadcast('userSubs:updated');
			});
		};

		function updateGroup(group) {
			restService.getGroup(group.name).then(function(data) {
				group = Object.assign(group, data);
			});
		}

		function updatePosts() {
			navbarService.page = 1;
			document.body.scrollTop = document.documentElement.scrollTop = 0;
			$scope.loadingPosts = false;
			$scope.noMorePosts = false;
			var params = {page: navbarService.page, sort: navbarService.sort, time: navbarService.time};
			if (group) {
				restService.getGroupPosts(group.name, params).then(function(data) {
					$scope.posts = data;
				});
			} else if (navbarService.feed) {
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
