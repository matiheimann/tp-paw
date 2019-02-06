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
				restService.createGroup($scope.newGroup.name, $scope.newGroup.description).then(function(response) {             
					$modal.dismiss();
					$location.url(response.headers.get('Location'));
				})
				.catch(function(response) {
					$scope.groupnameNotRepeatedError = true;
				});
			}
		};

	}]);
});