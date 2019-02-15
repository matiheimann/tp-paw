define(['controllers/DeleteConfirmModalCtrl', 'angular-mocks'], function() {

	describe('Delete Modal Controller', function() {

		var $controller, $uibModalInstance, restService, $q, $rootScope;
		var deleteModalCtrl, $scope = {};
		var MOCK_GROUP = {name: 'group', description: 'my awesome group'};
		var MOCK_MODAL_INSTANCE = {
			close: function(value){},
			dismiss: function(value){}
		};

		beforeEach(module('pawddit'));

		beforeEach(inject(function(_$controller_, _restService_, _$q_, _$rootScope_) {
			$controller = _$controller_;
			restService = _restService_;
			$q = _$q_;
			$rootScope = _$rootScope_;
		}));

		describe('$scope.doSubmit()', function() {

			$scope.type = 'group';

			spyOn(restService, 'deleteGroup').and.returnValue($q.when(MOCK_GROUP));
			spyOn(MOCK_MODAL_INSTANCE, 'close');
			spyOn(MOCK_MODAL_INSTANCE, 'dismiss');

			deleteModalCtrl = $controller('DeleteModalCtrl', {$scope: $scope, $uibModalInstance: MOCK_MODAL_INSTANCE, 
			restService: restService, group: MOCK_GROUP});

			beforeEach(function() {
				$scope.delete();
				$rootScope.$apply(); 
			});

			it('should be defined', function() {
				expect($scope.delete).toBeDefined();
			});

			it('should delete the product through the API', function() {
				expect(restService.deleteGroup).toHaveBeenCalledWith(MOCK_GROUP.name);
			});

			it('should close the modal indicating the product has been deleted', function() {
				expect(MOCK_MODAL_INSTANCE.close).toHaveBeenCalledWith(true);
			});
		});

		describe('$scope.cancel()', function() {

			beforeEach(function() {
				$scope.cancel();
			});

			it('should be defined', function() {
				expect($scope.cancel).toBeDefined();
			});

			it('should dismiss the modal', function() {
				expect(MOCK_MODAL_INSTANCE.dismiss).toHaveBeenCalledWith('cancel');
			});
		});
	});
});