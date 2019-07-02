'use strict';
define(['pawddit', 'controllers/RegisterModalCtrl', 'controllers/LoginModalCtrl', 'controllers/CreateGroupModalCtrl', 'controllers/CreatePostModalCtrl', 'controllers/DeleteConfirmModalCtrl', 'controllers/GroupsModalCtrl', 'controllers/ChangeProfilePictureModalCtrl', 'services/restService'], function(pawddit) {

	pawddit.service('modalService', ['$uibModal', 'restService', function($uibModal, restService) {

		this.registerModal = function() {
			return $uibModal.open({
				templateUrl: 'views/registerModal.html',
				controller: 'RegisterModalCtrl',
				size: 'md'
			});
		};

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

		this.createPostModal = function(group) {
			return $uibModal.open({
				templateUrl: 'views/createPostModal.html',
				controller: 'CreatePostModalCtrl',
				size: 'md',
				resolve: {
					subscribedGroups: function() {
						if (!group) {
							return restService.getMySubscribedGroups({page: 1});
						}
					},
					group: function() {
						return group;
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

		this.groupsModal = function(type, search, isLoggedUser) {
			return $uibModal.open({
				templateUrl: 'views/groupsModal.html',
				controller: 'GroupsModalCtrl',
				size: 'md',
				resolve: {
					groups: function() {
						var params = {page: 1};
						if (type === 'allGroups') {
							if (search) {
								params.search = search;
							}
							return restService.getGroups(params);
						} else if (type === 'myGroups') {
							return restService.getMySubscribedGroups(params);
						} else if (type === 'recommendedGroups') {
							return restService.getRecommendedGroups(params);
						}
					},
					type: function() {
						return type;
					},
					search: function() {
						return search;
					},
					isLoggedIn: function() {
						return isLoggedUser;
					}
				}
			});
		};

		this.changeProfilePictureModal = function(imageid) {
			return $uibModal.open({
				templateUrl: 'views/changeProfilePictureModal.html',
				controller: 'ChangeProfilePictureModalCtrl',
				size: 'md',
				resolve: {
					imageid: function() {
						return imageid;
					}
				}
			});
		};

	}]);
});
