'use strict';
define(['pawddit', 'services/restService'], function(pawddit) {

	pawddit.controller('LoginModalCtrl', ['$scope', '$rootScope', '$location', '$uibModalInstance', 'restService', function($scope, $rootScope, $location, $modal, restService) {
		$scope.loginUser = {};
		$scope.loginUser.rememberMe = false;

		$scope.cancel = function() {
			$scope.$dismiss();
		};

		$scope.doSubmit = function() {
			$scope.loginError = false;
			if ($scope.loginForm.$valid) {
				restService.loginUser($scope.loginUser.username, $scope.loginUser.password, $scope.loginUser.rememberMe).then(function(response) {             
					$modal.dismiss();
					$rootScope.$broadcast('user:updated');
				})
				.catch(function(response) {
					$scope.loginError = true;
				});
			}
		};

	}]);
});