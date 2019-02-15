'use strict'
define(['pawddit'], [function(pawddit) {
	pawddit.directive('commentDirective', [function() {
		return {
			restrict: 'E',
			templateURL: 'views/comment.html',
			replace: true,
			scope: {user: '='},
			controller: ['$scope', function($scope) {
				var comment = $scope.comment;
			}]
		}
	}]);
}]);