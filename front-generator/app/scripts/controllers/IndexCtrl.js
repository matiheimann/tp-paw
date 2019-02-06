'use strict';
define(['pawddit', 'services/restService', 'services/modalService'], function(pawddit) {

	pawddit.controller('IndexCtrl', ['$scope', '$rootScope', '$routeParams', '$location', 'restService', 'modalService', 'url', function($scope, $rootScope, $routeParams, $location, restService, modalService, url) {
		$scope.page = $routeParams.page || 1;
		$scope.sort = $routeParams.sort || 'new';
		$scope.time = $routeParams.time || 'all';

		restService.getLoggedUser().then(function(data) {
			if (data.userid !== undefined) {
				$scope.loggedUser = data;
				$scope.isLoggedIn = true;
				restService.getMySubscribedGroupsPageCount({}).then(function(data) {
					$scope.loggedUser.subscribedGroupsPageCount = data.pageCount;
				});
			}
		}).catch(function(response) {
			$scope.loggedUser = null;
			$scope.isLoggedIn = false;
		});

		$scope.$on('user:updated', function() {
			restService.getLoggedUser().then(function(data) {
				if (data.userid !== undefined) {
					$scope.loggedUser = data;
					$scope.isLoggedIn = true;
					restService.getMySubscribedGroupsPageCount({}).then(function(data) {
						$scope.loggedUser.subscribedGroupsPageCount = data.pageCount;
					});
				}
			}).catch(function(response) {
				$scope.loggedUser = null;
				$scope.isLoggedIn = false;
			});
		});

		$scope.logout = function() {
			restService.logoutUser().then(function(data) {
				$rootScope.$broadcast('user:updated');
			});
		};

		$scope.buildURL = function(params) {
			var url = '#' + $location.path();
			var hasParams = false;

			var page = (params.page || $scope.page);
			if (page !== 1) {
				url += '?page=' + page;
				hasParams = true;
			}
			var sort = (params.sort || $scope.sort);
			if (sort !== 'new') {
				url += (hasParams ? '&' : '?') + 'sort=' + sort;
				hasParams = true;
			}
			var time = (params.time || $scope.time);
			if (time !== 'all') {
				url += (hasParams ? '&' : '?') + 'time=' + time;
				hasParams = true;
			}

			return url;
		};

		$scope.getImageURL = function(imageid) {
			return url + '/images/' + imageid;
		};

		$scope.$on('$locationChangeSuccess', function(event) {
			$scope.currentPath = $location.path();
			console.log($scope.currentPath);
		});

		$scope.gotoProfile = function(name) {
			$location.url('profile/' + name);
		};

		$scope.gotoPost = function(name, pid) {
			$location.url('groups/' + name + '/posts/' + pid);
		};

		$scope.createGroupModal = modalService.createGroupModal;
		$scope.loginModal = modalService.loginModal;

		$scope.test = function(i) {
			console.log(i);
		};

	}]);
});
