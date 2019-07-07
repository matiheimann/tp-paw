define(['controllers/CreateGroupModalCtrl', 'angular-mocks'], function() {

	describe('Create group modal controller test', function() {

    var $controller, $uibModalInstance, restService, $q, $rootScope;
    var createGroupModalCtl;
    var $location = {};
    var $scope = {};

    var MOCK_MODAL = {};

		beforeEach(module('pawddit'));

    beforeEach(inject(function(_$controller_, _restService_, _$q_, _$rootScope_) {
			$controller = _$controller_;
			restService = _restService_;
			$q = _$q_;
			$rootScope = _$rootScope_;

			spyOn(restService, 'createGroup');

			createGroupModalCtl = $controller('CreateGroupModalCtrl',
      {$scope: $scope, $rootScope: $rootScope, $location: $location, $uibModalInstance: MOCK_MODAL,
			restService: restService});
		}));

		describe('$scope.doSubmit()', function() {

			beforeEach(function() {
        $scope.createGroupForm = {};
				$scope.doSubmit();
				$rootScope.$apply();
			});

			it('should be defined', function() {
				expect($scope.doSubmit).toBeDefined();
			});

			it('should not call restService.createGroup() given an invalid form', function() {
				expect(restService.createGroup).not.toHaveBeenCalled();
			});
		});
	});
});
