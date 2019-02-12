'use strict';
define(['pawddit', 'services/restService', 'directives/fileRead'], function(pawddit) {

	pawddit.controller('CreatePostModalCtrl', ['$scope', '$location', '$uibModalInstance', 'restService', 'subscribedGroups', 'subscribedGroupsPageCount', function($scope, $location, $modal, restService, subscribedGroups, subscribedGroupsPageCount) {
		$scope.newPost = {};
		$scope.subscribedGroups = subscribedGroups;
		$scope.newPost.group = subscribedGroups[0];

		for (var i = 2; i <= subscribedGroupsPageCount.pageCount; i++) {
			restService.getMySubscribedGroups({page: i}).then(function(data) {
				$scope.subscribedGroups.push.apply($scope.subscribedGroups, data);
			});
		}

		$scope.cancel = function() {
			$scope.$dismiss();
		};

		$scope.doSubmit = function() {
			$scope.validImageFormatError = false;
			if ($scope.createPostForm.$valid) {
				restService.createPost($scope.newPost.group.name, $scope.newPost.title, $scope.newPost.content, null).then(function(data) {           
					$modal.dismiss();
					$location.url('/groups/' + data.group.name + '/posts/' + data.postid);
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
					}
				});
			}
		};

	}]);
});