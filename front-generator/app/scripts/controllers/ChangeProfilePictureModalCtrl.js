'use strict';
define(['pawddit', 'services/restService'], function(pawddit) {

	pawddit.controller('ChangeProfilePictureModalCtrl', ['$scope', '$rootScope', '$location', '$uibModalInstance', 'restService', 'imageid', 'url', function($scope, $rootScope, $location, $modal, restService, imageid, url) {
		$scope.imageid = imageid;
		$scope.newPicture = {};

		$scope.cancel = function() {
			$scope.$dismiss();
		};

		$scope.doSubmit = function() {
			$scope.validImageFormatError = false;
			$scope.submitted = true;
			restService.modifyProfilePicture($scope.newPicture.file).then(function(data) {   
				$rootScope.$broadcast('profile:updated');
				$modal.dismiss();
			}).catch(function(response) {
				if (response.status === 409) {
					angular.forEach(response.data.errors, function(error, key) {
  						switch (error.field) {
  							case 'file':
  								$scope.validImageFormatError = true;
  								break;
  							default:
  						}
					});
					if (response.data.message === 'InvalidImage') {
						$scope.validImageFormatError = true;
					}
				}
			});
		};

		$scope.getImageURL = function(imageid) {
			return url + '/images/' + imageid;
		};

	}]);
});