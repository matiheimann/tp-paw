define(['controllers/DeleteConfirmModalCtrl', 'angular-mocks'], function() {

	describe('Delete confirm modal controller test', function() {

    var $controller, $uibModalInstance, restService, $q, $rootScope;
    var deleteConfirmModalCtrl;
    var $location = {};
    var $scope = {};
    var type = {};

    var MOCK_MODAL = {
      dismiss: function(){}
    };

    var MOCK_ITEM = {
      group: {name: 'mockGroupName'},
      post: {postid: 1},
      comment: {commentid: 1}
    };

		beforeEach(module('pawddit'));

    beforeEach(inject(function(_$controller_, _restService_, _$q_, _$rootScope_, _$location_) {
			$controller = _$controller_;
			restService = _restService_;
			$q = _$q_;
			$rootScope = _$rootScope_;
      $location = _$location_;

			spyOn(restService, 'deleteGroup').and.returnValue($q.when('mockReturn'));
      spyOn(restService, 'deletePost').and.returnValue($q.when('mockReturn'));
      spyOn(restService, 'deleteComment').and.returnValue($q.when('mockReturn'));
      spyOn(MOCK_MODAL, 'dismiss');
    }));

		describe('Delete group test', function() {

			beforeEach(function() {
        type = 'group';
        deleteConfirmModalCtrl = $controller('DeleteConfirmModalCtrl',
        {$scope: $scope, $rootScope: $rootScope, $location: $location, $uibModalInstance: MOCK_MODAL, restService: restService,
        item: MOCK_ITEM, type: type});
				$scope.doSubmit();
				$rootScope.$apply();
			});

			it('should delete group through the api with valid parameters', function() {
				expect(restService.deleteGroup).toHaveBeenCalledWith(MOCK_ITEM.group.name);
			});

      it('should dismiss modal', function() {
        expect(MOCK_MODAL.dismiss).toHaveBeenCalled();
      });
		});

    describe('Delete post test', function() {

      beforeEach(function() {
        type = 'post';
        deleteConfirmModalCtrl = $controller('DeleteConfirmModalCtrl',
        {$scope: $scope, $rootScope: $rootScope, $location: $location, $uibModalInstance: MOCK_MODAL, restService: restService,
        item: MOCK_ITEM, type: type});
				$scope.doSubmit();
				$rootScope.$apply();
			});

      it('should delete post through the api with valid parameters', function() {
        expect(restService.deletePost).toHaveBeenCalledWith(MOCK_ITEM.group.name, MOCK_ITEM.post.postid);
      });

      it('should dismiss modal', function() {
        expect(MOCK_MODAL.dismiss).toHaveBeenCalled();
      });
    });

    describe('Delete comment test', function() {

      beforeEach(function() {
        type = 'comment';
        deleteConfirmModalCtrl = $controller('DeleteConfirmModalCtrl',
        {$scope: $scope, $rootScope: $rootScope, $location: $location, $uibModalInstance: MOCK_MODAL, restService: restService,
        item: MOCK_ITEM, type: type});
				$scope.doSubmit();
				$rootScope.$apply();
			});

      it('should delete comment through the api with valid parameters', function() {
        expect(restService.deleteComment).toHaveBeenCalledWith(MOCK_ITEM.group.name, MOCK_ITEM.post.postid,
          MOCK_ITEM.comment.commentid);
      });

      it('should dismiss modal', function() {
        expect(MOCK_MODAL.dismiss).toHaveBeenCalled();
      });
      
    });
	});
});
