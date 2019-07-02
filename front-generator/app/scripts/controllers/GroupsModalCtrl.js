'use strict';
define(['pawddit', 'services/restService' , 'services/modalService'], function(pawddit) {

	pawddit.controller('GroupsModalCtrl', ['$scope', '$rootScope', '$location', '$uibModalInstance', 'restService', 'modalService', 'groups', 'type', 'search', 'isLoggedIn', function($scope, $rootScope, $location, $modal, restService, modalService, groups, type, search, isLoggedIn) {
		$scope.type = type;
		$scope.search = search;
		$scope.groupsPage = 1;
		$scope.groups = groups.groups;
		$scope.isLoggedIn = isLoggedIn;

		$scope.cancel = function() {
			$scope.$dismiss();
		};

		$scope.createGroupFromModal = function() {
			$scope.$dismiss();
			modalService.createGroupModal();
		}

		$scope.login = function() {
			$scope.$dismiss();
			modalService.loginModal();
		}
		$scope.loadMoreGroups = function() {
			$scope.loadingGroups = true;
			$scope.groupsPage++;
			var params = {page: $scope.groupsPage};
			if (type === 'allGroups' && search) {
				params.search = search;
				restService.getGroups(params).then(function(data) {
					if (data.groups.length > 0) {
						$scope.groups.push.apply($scope.groups, data.groups);
						$scope.noMoreGroups = data.groups.length < 5;
					} else {
						$scope.noMoreGroups = true;
					}
					$scope.loadingGroups = false;
				}).catch(function(response) {
					$scope.loadingGroups = false;
				});
			} else if (type === 'allGroups' && !search) {
				restService.getGroups(params).then(function(data) {
					if (data.groups.length > 0) {
						$scope.groups.push.apply($scope.groups, data.groups);
						$scope.noMoreGroups = data.groups.length < 5;
					} else {
						$scope.noMoreGroups = true;
					}
					$scope.loadingGroups = false;
				}).catch(function(response) {
					$scope.loadingGroups = false;
				});
			} else if (type === 'myGroups') {
				restService.getMySubscribedGroups(params).then(function(data) {
					if (data.groups.length > 0) {
						$scope.groups.push.apply($scope.groups, data.groups);
						$scope.noMoreGroups = data.groups.length < 5;
					} else {
						$scope.noMoreGroups = true;
					}
					$scope.loadingGroups = false;
				}).catch(function(response) {
					$scope.loadingGroups = false;
				});
			} else if (type === 'recommendedGroups') {
				$scope.noMoreGroups = true;
				$scope.loadingGroups = false;
			}
		};

		$scope.gotoProfile = function(name) {
			$scope.$dismiss();
			$location.url('/profile/' + name);
		};

		$scope.gotoGroup = function(name) {
			$scope.$dismiss();
			$location.url('/groups/' + name);
		};

	}]);
});