'use strict';
define(['pawddit', 'services/restService'], function(pawddit) {

	pawddit.controller('CreatePostModalCtrl', ['$scope', '$location', '$uibModalInstance', 'restService', function($scope, $location, $modal, restService) {
		$scope.newPost = {};

		$scope.cancel = function() {
			$scope.$dismiss();
		};

		$scope.doSubmit = function() {
			if ($scope.createPostForm.$valid) {
				restService.createPost($scope.newPost.groupNname, $scope.newPost.title, $scope.newPost.content, $scope.newPost.file).then(function(response) {             
					$modal.dismiss();
					$location.url('/groups/' + response.data.group.name + '/posts/' + response.data.postid);
				});
			}
		};



	}]);
});