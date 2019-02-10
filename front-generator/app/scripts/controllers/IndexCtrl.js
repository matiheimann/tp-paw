'use strict';
define(['pawddit', 'services/restService', 'services/modalService', 'services/navbarService'], function(pawddit) {

	pawddit.controller('IndexCtrl', ['$scope', '$rootScope', '$translate', '$route', '$location', 'restService', 'modalService', 'navbarService', 'url', 'timeAgoSettings', function($scope, $rootScope, $translate, $route, $location, restService, modalService, navbarService, url, timeAgoSettings) {
		$scope.navbar = navbarService;

		$translate('Lang.code').then(function(translatedValue) {
            switch (translatedValue) {
				case 'en':
					timeAgoSettings.overrideLang = 'en_US';
					break;
				case 'es':
					timeAgoSettings.overrideLang = 'es_LA';
					break;
				default:
			}
        });

        restService.isLoggedIn().then(function(data) {
        	$scope.isLoggedIn = data.isLoggedIn;
        	if ($scope.isLoggedIn) {
				getLoggedUser();
			}
		});

		$scope.$on('user:updated', function() {
			restService.isLoggedIn().then(function(data) {
				$scope.isLoggedIn = data.isLoggedIn;
        		if ($scope.isLoggedIn) {
					getLoggedUser();
				}
			});
		});

		$scope.logout = function() {
			restService.logoutUser().then(function(data) {
				$rootScope.$broadcast('user:updated');
			});
		};

		$scope.changeSort = function(sort) {
			navbarService.sort = sort;
			$rootScope.$broadcast('posts:updated');
		};

		$scope.changeTime = function(time) {
			navbarService.time = time;
			$rootScope.$broadcast('posts:updated');
		};

		$scope.home = function(feed) {
			navbarService.page = 1;
			navbarService.sort = 'new';
			navbarService.time = 'all';
			navbarService.feed = feed;
			$location.url('');
			if (feed) {
				navbarService.currentPage = 'feedPosts';
				navbarService.currentPageText = 'dropdown.button.myfeed.message';
			} else {
				navbarService.currentPage = 'allPosts';
				navbarService.currentPageText = 'dropdown.button.all.message';
			}
			$rootScope.$broadcast('posts:updated');
		};

		$scope.getImageURL = function(imageid) {
			return url + '/images/' + imageid;
		};

		$scope.gotoProfile = function(name) {
			$location.url('profile/' + name);
		};

		$scope.gotoPost = function(name, pid) {
			$location.url('groups/' + name + '/posts/' + pid);
		};

		$scope.$on('$locationChangeSuccess', function(event) {
			$scope.currentPath = $location.path();
			console.log($scope.currentPath);
		});

		$scope.createGroupModal = modalService.createGroupModal;
		$scope.loginModal = modalService.loginModal;
		$scope.createPostModal = modalService.createPostModal;
		$scope.deleteConfirmModal = modalService.deleteConfirmModal;
		$scope.registerModal= modalService.registerModal;

		function getLoggedUser() {
			restService.getLoggedUser().then(function(data) {
				$scope.loggedUser = data;
				$scope.isLoggedIn = true;
				restService.getMySubscribedGroupsPageCount({}).then(function(data) {
					$scope.loggedUser.subscribedGroupsPageCount = data.pageCount;
				});
			}).catch(function(response) {
				$scope.loggedUser = null;
				$scope.isLoggedIn = false;
			});
        }

        $scope.test = function(i) {
			console.log(i);
		};

	}]);
});
