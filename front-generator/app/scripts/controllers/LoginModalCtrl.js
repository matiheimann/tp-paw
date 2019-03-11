'use strict';
define(['pawddit', 'services/authService'], function(pawddit) {

	pawddit.controller('LoginModalCtrl', ['$scope', '$rootScope', '$location', '$uibModalInstance', 'authService', function($scope, $rootScope, $location, $modal, authService) {
		$scope.loginUser = {};
		$scope.loginUser.rememberMe = false;

		$scope.cancel = function() {
			$scope.$dismiss();
		};

		$scope.doSubmit = function() {
			$scope.loginError = false;
			if ($scope.loginForm.$valid) {
				authService.loginUser($scope.loginUser.username, $scope.loginUser.password, $scope.loginUser.rememberMe).then(function() {             
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