'use strict';
define(['pawddit', 'services/restService'], function(pawddit) {

	pawddit.controller('GroupsModalCtrl', ['$scope', '$rootScope', '$location', '$uibModalInstance', 'restService', 'groups', 'type', 'search', function($scope, $rootScope, $location, $modal, restService, groups, type, search) {
		$scope.type = type;
		$scope.search = search;
		$scope.groupsPage = 1;
		$scope.groups = groups;

		$scope.cancel = function() {
			$scope.$dismiss();
		};

		$scope.loadMoreGroups = function() {
			$scope.loadingGroups = true;
			$scope.groupsPage++;
			var params = {page: $scope.groupsPage};
			if (type === 'allGroups' && search) {
				params.search = search;
				restService.getGroups(params).then(function(data) {
					if (data.length > 0) {
						$scope.groups.push.apply($scope.groups, data);
						$scope.noMoreGroups = data.length < 5;
					} else {
						$scope.noMoreGroups = true;
					}
					$scope.loadingGroups = false;
				}).catch(function(response) {
					$scope.loadingGroups = false;
				});
			} else if (type === 'allGroups' && !search) {
				restService.getGroups(params).then(function(data) {
					if (data.length > 0) {
						$scope.groups.push.apply($scope.groups, data);
						$scope.noMoreGroups = data.length < 5;
					} else {
						$scope.noMoreGroups = true;
					}
					$scope.loadingGroups = false;
				}).catch(function(response) {
					$scope.loadingGroups = false;
				});
			} else if (type === 'myGroups') {
				restService.getMySubscribedGroups(params).then(function(data) {
					if (data.length > 0) {
						$scope.groups.push.apply($scope.groups, data);
						$scope.noMoreGroups = data.length < 5;
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