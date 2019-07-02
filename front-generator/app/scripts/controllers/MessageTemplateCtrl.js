'use strict';
define(['pawddit', 'services/messageService', 'services/navbarService'], function(pawddit) {

	pawddit.controller('MessageTemplateCtrl', ['$scope', '$translate', '$window', 'messageService', 'navbarService', function($scope, $translate, $window, messageService, navbarService) {
		$translate('info.title').then(function(translatedValue) {
			$window.document.title = 'Pawddit. | ' + translatedValue;
		});
		navbarService.setCurrentPage('info', 'dropdown.button.info.message');

		$scope.message = messageService.getMessage();
		messageService.clear();
	}]);
	
});