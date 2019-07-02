'use strict';
define(['pawddit'], function(pawddit) {

	pawddit.factory('messageService', [function() {
		var _message = {text: 'errorLinkAccount.message', icon: 'fa-times'};
		return {
			getMessage: function() {
				return {text: _message.text, icon: _message.icon};
			},
			setMessage: function(text, icon) {
				_message.text = text;
				_message.icon = icon;
			},
			clear: function() {
				_message.text = 'errorLinkAccount.message';
				_message.icon = 'fa-times';
			}
		};
	}]);
	
});
