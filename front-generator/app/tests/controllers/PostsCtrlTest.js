define(['controllers/PostsCtrl', 'angular-mocks'], function() {

	describe('Posts controller test', function() {

    var $controller, restService, navbarService, $q, $rootScope;
    var createGroupModalCtl;
    var $location = {};
    var $scope = {};
    var $translate = {};
    var $window = {document: {title: ''}};
    var postsCtrl;
    var url = '';

    var MOCK_POST = {postid: '1', title: 'My first post', groupname: 'group', content:'My content', userid: '1'};

    var MOCK_POSTS = [MOCK_POST];

    var MOCK_GROUP = {name: 'group', description: 'mockGroup'};

		beforeEach(module('pawddit'));

    beforeEach(inject(function(_$controller_, _restService_, _navbarService_, _$q_, _$rootScope_, _$translate_) {
			$controller = _$controller_;
			restService = _restService_;
      navbarService = _navbarService_;
			$q = _$q_;
			$rootScope = _$rootScope_;
      $translate = _$translate_
      $scope.$on = function(){};

			postsCtrl = $controller('PostsCtrl',
      {$scope: $scope, $rootScope: $rootScope, $location: $location, $translate: $translate,
       $window: $window, restService: restService, navbarService: navbarService,
       group: MOCK_GROUP, posts: MOCK_POSTS, url: url});

      spyOn(restService, 'subscribeGroup').and.returnValue($q.when('mockReturn'));
      spyOn(restService, 'unsubscribeGroup').and.returnValue($q.when('mockReturn'));
		}));

		describe('subscribe to group test', function() {

			beforeEach(function() {
				$scope.subscribe(MOCK_GROUP);
			});

			it('should subscribe to group through the API with valid parameters', function() {
				expect(restService.subscribeGroup).toHaveBeenCalledWith(MOCK_GROUP.name);
			});
		});

		describe('unsubscribe to group test', function() {

			beforeEach(function() {
				$scope.unsubscribe(MOCK_GROUP);
			});

			it('should unsubscribe from group through the API with valid parameters', function() {
				expect(restService.unsubscribeGroup).toHaveBeenCalledWith(MOCK_GROUP.name);
			});
		});
	});
});
