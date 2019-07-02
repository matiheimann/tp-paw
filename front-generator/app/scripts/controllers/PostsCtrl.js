'use strict';
define(['pawddit', 'services/restService', 'services/navbarService'], function(pawddit) {

	pawddit.controller('PostsCtrl', ['$scope', '$rootScope', '$location', '$translate', '$window', 'restService', 'navbarService', 'group', 'posts', 'url', function($scope, $rootScope, $location, $translate, $window, restService, navbarService, group, posts, url) {
		navbarService.resetParams();

		if (group) {
			$window.document.title = 'Pawddit. | ' + group.name;
			$scope.group = group;
			navbarService.setCurrentPage('group', group.name);
		} else if (navbarService.feed) {
			$translate('homePage.title').then(function(translatedValue) {
				$window.document.title = 'Pawddit. | ' + translatedValue;
			});
			navbarService.setCurrentPage('feedPosts', 'dropdown.button.myfeed.message');
		} else {
			$translate('homePage.title').then(function(translatedValue) {
				$window.document.title = 'Pawddit. | ' + translatedValue;
			});
			navbarService.setCurrentPage('allPosts', 'dropdown.button.all.message');
		}

		$scope.posts = posts.posts;

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
			navbarService.resetPage();
			document.body.scrollTop = document.documentElement.scrollTop = 0;
			$scope.loadingPosts = false;
			$scope.noMorePosts = false;
			var p = navbarService.getParams();
			var params = {page: p.page, sort: p.sort, time: p.time};
			if (group) {
				restService.getGroupPosts(group.name, params).then(function(data) {
					$scope.posts = data.posts;
				});
			} else if (p.feed) {
				restService.getMyFeedPosts(params).then(function(data) {
					$scope.posts = data.posts;
				});
			} else {
				restService.getPosts(params).then(function(data) {
					$scope.posts = data.posts;
				});
			}
		}

		$scope.$on('posts:updated', function() {
			updatePosts();
		});

		$scope.loadMorePosts = function() {
			$scope.loadingPosts = true;
			navbarService.incPage();
			var p = navbarService.getParams();
			var params = {page: p.page, sort: p.sort, time: p.time};
			if (group) {
				restService.getGroupPosts(group.name, params).then(function(data) {
						if (data.posts.length > 0) {
							$scope.posts.push.apply($scope.posts, data.posts);
							$scope.noMorePosts = data.posts.length < 5;
						} else {
							$scope.noMorePosts = true;
						}
						$scope.loadingPosts = false;
					}).catch(function(response) {
						$scope.loadingPosts = false;
				});
			} else if (p.feed) {
				restService.getMyFeedPosts(params).then(function(data) {
					if (data.posts.length > 0) {
						$scope.posts.push.apply($scope.posts, data.posts);
						$scope.noMorePosts = data.posts.length < 5;
					} else {
						$scope.noMorePosts = true;
					}
					$scope.loadingPosts = false;
				}).catch(function(response) {
					$scope.loadingPosts = false;
				});
			} else {
				restService.getPosts(params).then(function(data) {
					if (data.posts.length > 0) {
						$scope.posts.push.apply($scope.posts, data.posts);
						$scope.noMorePosts = data.posts.length < 5;
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
