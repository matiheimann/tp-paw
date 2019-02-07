'use strict';
define(['pawddit', 'services/restService'], function(pawddit) {

	pawddit.controller('DeleteConfirmModalCtrl', ['$scope', '$location', '$uibModalInstance', 'restService', 'item', 'type', function($scope, $location, $modal, restService, item, type) {
		$scope.type = type;

		$scope.cancel = function() {
			$scope.$dismiss();
		};

		$scope.doSubmit = function() {
			switch (type) {
				case 'group': 
					restService.deleteGroup(item.group.name).then(function (response) {
						$modal.dismiss();
						$location.url('/');
					});
					break;
				case 'post':
					restService.deletePost(item.group.name, item.post.postid).then(function (response) {
						$modal.dismiss();
						$location.url('/groups/' + item.group.name);
					});
					break;

				case 'comment':
					restService.deleteComment(item.group.name, item.post.postid, item.comment.commentid).then(function (response) {
						$modal.dismiss();
						$location.url('/groups/' + item.group.name + '/posts/' + item.post.postid);
					});
					break;

				default:
			}
		};

	}]);
});