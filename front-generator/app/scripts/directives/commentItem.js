'use strict'
define(['pawddit'], function(pawddit) {
	pawddit.directive('commentItem', function() {
		return {
			restrict: 'E',
			templateUrl: 'views/commentItem.html'
		}
	});
});