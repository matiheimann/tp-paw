'use strict';
define(['pawddit', 'services/messageService', 'services/navbarService'], function(pawddit) {

	pawddit.controller('MessageTemplateCtrl', ['$scope', '$window', 'messageService', 'navbarService', function($scope, $window, messageService, navbarService) {
		$window.document.title = 'Pawddit. | Info';
		navbarService.currentPage = 'info';
		navbarService.currentPageText = 'dropdown.button.info.message';

		if (messageService.text && messageService.icon) {
			$scope.message = {text: messageService.text, icon: messageService.icon};
			messageService.text = null;
			messageService.icon = null;
		} else {
			$scope.message = {text: 'errorLinkAccount.message', icon: 'fa-times'};
		}
		
	}]);
	
});