'use strict';
define(['pawddit', 'services/restService', 'directives/fileRead'], function(pawddit) {

	pawddit.controller('CreatePostModalCtrl', ['$scope', '$location', '$uibModalInstance', 'restService', 'subscribedGroups', 'subscribedGroupsPageCount', 'group', function($scope, $location, $modal, restService, subscribedGroups, subscribedGroupsPageCount, group) {
		$scope.newPost = {};
		$scope.subscribedGroups = subscribedGroups;
		$scope.group = group;
		if (group) {
			$scope.newPost.group = group;
			$scope.subscribedGroups = [group];
		} else {
			$scope.newPost.group = subscribedGroups[0];
			for (var i = 2; i <= subscribedGroupsPageCount.pageCount; i++) {
				restService.getMySubscribedGroups({page: i}).then(function(data) {
					$scope.subscribedGroups.push.apply($scope.subscribedGroups, data);
				});
			}
		}

		$scope.cancel = function() {
			$scope.$dismiss();
		};

		$scope.doSubmit = function() {
			$scope.validImageFormatError = false;
			if ($scope.createPostForm.$valid) {
				restService.createPost($scope.newPost.group.name, $scope.newPost.title, $scope.newPost.content, $scope.newPost.file).then(function(data) {           
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
						if (response.data.message === 'InvalidFormat') {
							$scope.validImageFormatError = true;
						}
					}
				});
			}
		};

	}]);
});