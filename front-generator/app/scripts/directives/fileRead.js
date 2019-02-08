'use strict';
define(['pawddit'], function(pawddit) {
	pawddit.directive('fileRead', [function () {
		return {
			scope: {
				fileRead: '='
			},
			link: function (scope, element, attributes) {
				element.bind('change', function (changeEvent) {
					console.log(changeEvent.target.files[0]);
					var reader = new FileReader();
					reader.onload = function (loadEvent) {
						scope.$apply(function () {
							scope.fileRead = loadEvent.target.result;
						});
					};
					reader.readAsDataURL(changeEvent.target.files[0]);
				});
			}
		};
	}]);
});