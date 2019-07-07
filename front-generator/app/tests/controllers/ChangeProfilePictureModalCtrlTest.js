define(['controllers/ChangeProfilePictureModalCtrl', 'angular-mocks'], function() {

	describe('Change profile picture modal controller test', function() {

    var $controller, $uibModalInstance, restService, $q, $rootScope;
    var changeProfilePictureModalCtrl, $scope = {};
    var $location = {}, imageid = 1, url = '';

    var MOCK_MODAL = {
      dismiss: function(){}
    };

		var EXPECTED_IMAGE_URL = url + '/images/' + imageid;

		beforeEach(module('pawddit'));

    beforeEach(inject(function(_$controller_, _restService_, _$q_, _$rootScope_) {
			$controller = _$controller_;
			restService = _restService_;
			$q = _$q_;
			$rootScope = _$rootScope_;

			spyOn(restService, 'modifyProfilePicture').and.returnValue($q.when(true));
			spyOn(MOCK_MODAL, 'dismiss');

			changeProfilePictureModalCtrl = $controller('ChangeProfilePictureModalCtrl',
      {$scope: $scope, $rootScope: $rootScope, $location: $location, $uibModalInstance: MOCK_MODAL,
			restService: restService, imageid: imageid, url: url});
		}));

		describe('$scope.doSubmit()', function() {

			beforeEach(function() {
				$scope.doSubmit();
				$rootScope.$apply();
			});

			it('should be defined', function() {
				expect($scope.doSubmit).toBeDefined();
			});

			it('should change the profile picture through the API', function() {
				expect(restService.modifyProfilePicture).toHaveBeenCalled();
			});

			it('should dismiss the modal', function() {
				expect(MOCK_MODAL.dismiss).toHaveBeenCalled();
			});
		});

		describe('$scope.getImageURL', function() {

			it('should return expected image url', function() {
				expect($scope.getImageURL(imageid)).toEqual(EXPECTED_IMAGE_URL);
			});
		});

	});
});
