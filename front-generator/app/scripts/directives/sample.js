'use strict';
define(['pawddit'], function(pawddit) {

	pawddit.directive('sample', function() {
		return {
			restrict: 'E',
			template: '<span>Sample</span>'
		};
	});
});
