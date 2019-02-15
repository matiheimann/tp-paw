define(['controllers/IndexCtrl', 'angular-mocks'], function() {

	describe('Index Controller', function() {
		
		var controller, scope;
		
		beforeEach(module('pawditt'));
		
		beforeEach(inject(function($injector, $rootScope, $controller) {
			scope = $rootScope.$new();
			controller = $controller('IndexCtrl', { $scope: scope });
		}));
		
		describe('$scope.isLoggedIn', function() {
			it('should be false', function() {
				getLoggedUser();
				expect(scope.isLoggedIn).toBe(undefined);
			});
			
		});
	});
});