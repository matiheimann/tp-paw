'use strict';
define(['pawddit', 'services/messageService'], function(pawddit) {

	pawddit.controller('MessageTemplateCtrl', ['$scope', 'messageService', function($scope, messageService) {

		if (messageService.text && messageService.icon) {
			$scope.message = {text: messageService.text, icon: messageService.icon};
			messageService.text = null;
			messageService.icon = null;
		} else {
			$scope.message = {text: 'errorLinkAccount.message', icon: 'fa-times'};
		}
		
	}]);
	
});