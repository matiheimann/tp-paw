'use strict';
define(['pawddit', 'services/restService', 'services/messageService'], function(pawddit) {

	pawddit.controller('RegisterModalCtrl', ['$scope', '$location', '$uibModalInstance', 'restService', 'messageService', function($scope, $location, $modal, restService, messageService) {
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

			if ($scope.registerForm.$valid && !$scope.passwordNotMatch) {
				restService.registerUser($scope.newUser.email, $scope.newUser.username, 
					$scope.newUser.password, $scope.newUser.confirmPassword).then(function(data) {             
						$modal.dismiss();
						messageService.text = 'verifyAccount.message';
						messageService.icon = 'fa-envelope';
						$location.url('/info');
				});
			}
		};

	}]);
});