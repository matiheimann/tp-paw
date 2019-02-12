'use strict';
define(['pawddit', 'services/messageService', 'services/restService'], function(pawddit) {

	pawddit.controller('ConfirmAccountCtrl', ['$scope', '$route', '$location', 'restService', function($scope, $route, $location, restService) {

		var token = $route.current.params.token;
		restService.getConfirmation({token: token}).then(function(data) {
			$scope.message.text = 'verifiedAccount.message';
			$scope.message.code = 'fa-check';
		}).catch(function(response) {
			$scope.message.text = 'errorVerificationTokenNotFound.message';
			$scope.message.code = 'fa-times';
		});
		
	}]);

});