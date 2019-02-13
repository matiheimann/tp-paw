'use strict';
define(['pawddit', 'services/messageService', 'services/restService', 'services/navbarService'], function(pawddit) {

	pawddit.controller('ConfirmAccountCtrl', ['$scope', '$rootScope', '$route', '$location', '$translate', '$window', 'restService', 'navbarService', function($scope, $rootScope, $route, $location, $translate, $window, restService, navbarService) {
		$translate('info.title').then(function(translatedValue) {
			$window.document.title = 'Pawddit. | ' + translatedValue;
		});
		navbarService.currentPage = 'info';
		navbarService.currentPageText = 'dropdown.button.info.message';
		
		var token = $route.current.params.token;
		restService.getConfirmation({token: token}).then(function(data) {
			$scope.message = {text: 'verifiedAccount.message', icon: 'fa-check'};
			$rootScope.$broadcast('user:updated');
		});

	}]);

});