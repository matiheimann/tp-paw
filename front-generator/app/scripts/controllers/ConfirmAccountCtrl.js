'use strict';
define(['pawddit', 'services/messageService', 'services/restService'], function(pawddit) {

	pawddit.controller('ConfirmAccountCtrl', ['$scope', '$rootScope', '$route', '$location', 'restService', function($scope, $rootScope, $route, $location, restService) {

		var token = $route.current.params.token;
		restService.getConfirmation({token: token}).then(function(data) {
			$scope.message = {text: 'verifiedAccount.message', icon: 'fa-check'};
			$rootScope.$broadcast('user:updated');
		}).catch(function(response) {
			$scope.message = {text: 'errorVerificationTokenNotFound.message', icon: 'fa-times'};
		});

	}]);

});