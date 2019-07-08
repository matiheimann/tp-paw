define(['controllers/CreatePostModalCtrl', 'angular-mocks'], function() {

	describe('Create post modal controller test', function() {

    var $controller, $uibModalInstance, restService, $q, $rootScope;
    var createPostModalCtrl;
    var $location = {};
    var $scope = {};
    var subscribedGroups = {};
    var group = {};

    var MOCK_MODAL = {};

		beforeEach(module('pawddit'));

    beforeEach(inject(function(_$controller_, _restService_, _$q_, _$rootScope_) {
			$controller = _$controller_;
			restService = _restService_;
			$q = _$q_;
			$rootScope = _$rootScope_;

			spyOn(restService, 'createPost');

			createPostModalCtrl = $controller('CreatePostModalCtrl',
      {$scope: $scope, $location: $location, $uibModalInstance: MOCK_MODAL, restService: restService,
      subscribedGroups: subscribedGroups, group: group});
    }));

		describe('$scope.doSubmit()', function() {

			beforeEach(function() {
        $scope.createPostForm = {};
				$scope.doSubmit();
				$rootScope.$apply();
			});

			it('should be defined', function() {
				expect($scope.doSubmit).toBeDefined();
			});

			it('should not call restService.createPost() given an invalid form', function() {
				expect(restService.createPost).not.toHaveBeenCalled();
			});
		});
	});
});
