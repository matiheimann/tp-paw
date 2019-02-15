'use strict';
define(['pawddit', 'services/restService'], function(pawddit) {

	pawddit.controller('DeleteConfirmModalCtrl', ['$scope', '$rootScope', '$location', '$uibModalInstance', 'restService', 'item', 'type', function($scope, $rootScope, $location, $modal, restService, item, type) {
		$scope.type = type;

		$scope.cancel = function() {
			$scope.$dismiss();
		};

		$scope.doSubmit = function() {
			switch (type) {
				case 'group': 
					restService.deleteGroup(item.group.name).then(function(data) {
						$modal.dismiss();
						$rootScope.$broadcast('userSubs:updated');
						$location.url('/');
					});
					break;
				case 'post':
					restService.deletePost(item.group.name, item.post.postid).then(function(data) {
						$modal.dismiss();
						$location.url('/groups/' + item.group.name);
					});
					break;

				case 'comment':
					restService.deleteComment(item.group.name, item.post.postid, item.comment.commentid).then(function(data) {
						$modal.dismiss();
						$rootScope.$broadcast('comment:deleted', item.comment);
					});
					break;

				default:
			}
		};

	}]);
});