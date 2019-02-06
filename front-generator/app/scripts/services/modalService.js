'use strict';
define(['pawddit', 'controllers/LoginModalCtrl', 'controllers/CreateGroupModalCtrl'], function(pawddit) {

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

	}]);
});
