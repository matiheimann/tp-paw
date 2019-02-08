'use strict';
define(['pawddit', 'services/restService'], function(pawddit) {

	pawddit.controller('CreateGroupModalCtrl', ['$scope', '$location', '$uibModalInstance', 'restService', function($scope, $location, $modal, restService) {
		$scope.newGroup = {};

		$scope.cancel = function() {
			$scope.$dismiss();
		};

		$scope.doSubmit = function() {
			$scope.groupnameNotRepeatedError = false;
			if ($scope.createGroupForm.$valid) {
				restService.createGroup($scope.newGroup.name, $scope.newGroup.description).then(function(data) {             
					$modal.dismiss();
					$location.url('/groups/' + data.name);
				})
				.catch(function(response) {
					$scope.groupnameNotRepeatedError = true;
				});
			}
		};

	}]);
});