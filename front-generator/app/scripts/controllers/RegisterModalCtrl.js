'use strict';
define(['pawddit', 'services/restService'], function(pawddit) {

	pawddit.controller('RegisterModalCtrl', ['$scope', '$location', '$uibModalInstance', 'restService', function($scope, $location, $modal, restService) {
		$scope.newUser = {};

		$scope.cancel = function() {
			$scope.$dismiss();
		};

		$scope.doSubmit = function() {
			$scope.emailMatch = false;
			$scope.usernameUsed = false;
			$scope.passwordNotMatch = false;

			if ($scope.newUser.password !== $scope.newUser.confirmPassword) {
				$scope.passwordNotMatch = true;
			}

			if($scope.registerForm.$valid && !$scope.passwordNotMatch){
				restService.registerUser($scope.newUser.email, $scope.newUser.username, 
					$scope.newUser.password, $scope.newUser.confirmPassword);
			}
		};

	}]);
});