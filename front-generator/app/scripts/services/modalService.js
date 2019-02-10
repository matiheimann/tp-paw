'use strict';
define(['pawddit', 'controllers/RegisterModalCtrl', 'controllers/LoginModalCtrl', 'controllers/CreateGroupModalCtrl', 'controllers/CreatePostModalCtrl', 'controllers/DeleteConfirmModalCtrl', 'services/restService'], function(pawddit) {

	pawddit.service('modalService', ['$uibModal', 'restService', function($uibModal, restService) {

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
				size: 'md',
				resolve: {
					subscribedGroups: function() {
						return restService.getMySubscribedGroups({page: 1});
					},
					subscribedGroupsPageCount: function() {
						return restService.getMySubscribedGroupsPageCount({});
					}
				}
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

		this.registerModal = function() {
			return $uibModal.open({
				templateUrl: 'views/registerModal.html',
				controller: 'RegisterModalCtrl',
				size: 'md'
			});
		};

	}]);
});
