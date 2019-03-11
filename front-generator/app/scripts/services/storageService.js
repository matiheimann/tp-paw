'use strict';
define(['pawddit'], function(pawddit) {

	pawddit.factory('storageService', ['$window', function($window) {
		var _authenticationToken = JSON.parse($window.localStorage.getItem('session.authenticationToken')) || JSON.parse($window.sessionStorage.getItem('session.authenticationToken'));

		return {
			getAuthenticationToken: function() {
				return _authenticationToken;
			},
			setAuthenticationToken: function(authenticationToken, rememberMe) {
				_authenticationToken = authenticationToken;
				
				if (rememberMe)
					$window.localStorage.setItem('session.authenticationToken', JSON.stringify(authenticationToken));
				else
					$window.sessionStorage.setItem('session.authenticationToken', JSON.stringify(authenticationToken));
			},
			destroy: function() {
				this.setAuthenticationToken(null, false);
				this.setAuthenticationToken(null, true);
			}
		};
	}]);
	
});
