'use strict';
define(['pawddit', 'controllers/LoginModalCtrl', 'controllers/CreateGroupModalCtrl', 'controllers/CreatePostModalCtrl', 'controllers/DeleteConfirmModalCtrl'], function(pawddit) {

	pawddit.service('modalService', ['$uibModal', function($uibModal) {

		this.loginModal = function() {
			return $uibModal.open({
				templateUrl: 'views/loginModal.html',
				controller: 'LoginModalCtrl',
				size: 'md'
			});
		};

		this.createGroupModal = function() {
			return $uibModal.open({
				templateUrl: 'views/createGroupModal.html',
				controller: 'CreateGroupModalCtrl',
				size: 'md'
			});
		};

		this.createPostModal = function() {
			return $uibModal.open({
				templateUrl: 'views/createPostModal.html',
				controller: 'CreatePostModalCtrl',
				size: 'md'
			});
		};

		this.deleteConfirmModal = function(item, type) {
			return $uibModal.open({
				templateUrl: 'views/deleteConfirmModal.html',
				controller: 'DeleteConfirmModalCtrl',
				size: 'md',
				resolve: {
					item: function() {
						return item;
					},
					type: function() {
						return type;
					}
				}
			});
		};

	}]);
});
