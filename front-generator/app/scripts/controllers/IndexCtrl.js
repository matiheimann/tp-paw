'use strict';
define(['pawddit', 'jquery', 'services/restService', 'services/authService', 'services/modalService', 'services/navbarService'], function(pawddit) {

	pawddit.controller('IndexCtrl', ['$scope', '$rootScope', '$translate', '$route', '$location', 'restService', 'authService', 'modalService', 'navbarService', 'url', function($scope, $rootScope, $translate, $route, $location, restService, authService, modalService, navbarService, url) {
		$scope.navbar = navbarService.getNavbar();
		$scope.searchGroup = {};

        restService.isLoggedIn().then(function(data) {
        	$scope.isLoggedIn = data.isLoggedIn;
        	if ($scope.isLoggedIn) {
				restService.getLoggedUser().then(function(data) {
		        	$scope.loggedUser = data;
		        });
				getLoggedUserSubsPageCount();
			} else {
				$scope.loggedUser = null;
			}
        });

		$scope.$on('user:updated', function() {
			restService.isLoggedIn().then(function(data) {
	        	$scope.isLoggedIn = data.isLoggedIn;
	        	if ($scope.isLoggedIn) {
					restService.getLoggedUser().then(function(data) {
			        	$scope.loggedUser = data;
			        });
					getLoggedUserSubsPageCount();
				} else {
					$scope.loggedUser = null;
				}
	        });
		});

		$scope.$on('userSubs:updated', function() {
        	if ($scope.isLoggedIn) {
				getLoggedUserSubsPageCount();
			};
		});

		$scope.logout = function() {
			authService.logoutUser();
			$rootScope.$broadcast('user:updated');
			$scope.home(false);
		};

		$scope.changeSort = function(sort) {
			navbarService.setSort(sort);
			$rootScope.$broadcast('posts:updated');
		};

		$scope.changeTime = function(time) {
			navbarService.setTime(time);
			$rootScope.$broadcast('posts:updated');
		};

		$scope.searchGroups = function() {
			if ($scope.searchGroup.name !== undefined && $scope.searchGroup.name !== '') {
				if ($scope.searchGroup.name.length > 32) {
      				$scope.searchGroup.name = $scope.searchGroup.name.replace(/.$/, '');
				}
    			$scope.searchGroup.name = $scope.searchGroup.name.replace(/[^a-zA-Z0-9]/g, '');
    			restService.getGroups({page: 1, search: $scope.searchGroup.name}).then(function(data) {
    				$scope.foundGroups = data.groups;
    			});
			} else {
				$scope.foundGroups = null;
			}
		};

		$scope.gotoFoundGroup = function(name) {
			$scope.searchGroup.name = '';
			$scope.foundGroups = null;
			$location.url('/groups/' + name);
		};

		$scope.home = function(feed) {
			navbarService.resetParams();
			navbarService.setFeed(feed);
			$location.url('');
			if (feed) {
				navbarService.setCurrentPage('feedPosts', 'dropdown.button.myfeed.message');
			} else {
				navbarService.setCurrentPage('allPosts', 'dropdown.button.all.message');
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
		});
		
		$scope.registerModal = modalService.registerModal;
		$scope.loginModal = modalService.loginModal;
		$scope.createGroupModal = modalService.createGroupModal;
		$scope.createPostModal = modalService.createPostModal;
		$scope.deleteConfirmModal = modalService.deleteConfirmModal;
		$scope.groupsModal = modalService.groupsModal;
		$scope.changeProfilePictureModal = modalService.changeProfilePictureModal;

        function getLoggedUserSubsPageCount() {
        	restService.getMySubscribedGroups({page: 1}).then(function(data) {
				$scope.loggedUser.subscribedGroupsPageCount = data.pageCount;
			});
        }

        $scope.test = function(i) {
			console.log(i);
		};

	}]);
});
