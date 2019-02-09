'use strict';
define(['pawddit', 'services/restService'], function(pawddit) {

	pawddit.controller('RegisterModalCtrl', ['$scope', '$location', '$uibModalInstance', 'restService', function($scope, $location, $modal, restService) {
		$scope.newGroup = {};

		$scope.cancel = function() {
			$scope.$dismiss();
		};

		$scope.doSubmit = function() {

		};

	}]);
});