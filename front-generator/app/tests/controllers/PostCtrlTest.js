define(['controllers/PostCtrl', 'angular-mocks'], function() {

	describe('Post controller test', function() {

    var $controller, restService, navbarService, $q, $rootScope;
    var createGroupModalCtl;
    var $location = {};
    var $scope = {};
    var $window = {document: {title: ''}};
    var url = '';

		var MOCK_COMMENT = {name: 'group', pid: 1, commentid: 1, content: "mock_content1"};

    var MOCK_COMMENTS = {coments: [MOCK_COMMENT]};

    var MOCK_POST = {postid: '1', title: 'Mock post', content:'My content', userid: '1',
                    group: {name: 'group', description: 'the best group'}};

		beforeEach(module('pawddit'));

    beforeEach(inject(function(_$controller_, _restService_, _navbarService_, _$q_, _$rootScope_) {
			$controller = _$controller_;
			restService = _restService_;
      navbarService = _navbarService_;
			$q = _$q_;
			$rootScope = _$rootScope_;
      $scope.$on = function(){};

			postCtrl = $controller('PostCtrl',
      {$scope: $scope, $rootScope: $rootScope, $location: $location, $window: $window, restService: restService,
        navbarService: navbarService, post: MOCK_POST, comments: MOCK_COMMENTS,
      url: url});

      spyOn(restService, 'upvotePost').and.returnValue($q.when('mockReturn'));
      spyOn(restService, 'downvotePost').and.returnValue($q.when('mockReturn'));
      spyOn(restService, 'upvoteComment').and.returnValue($q.when('mockReturn'));
      spyOn(restService, 'downvoteComment').and.returnValue($q.when('mockReturn'));

		}));

		describe('upvote post test', function() {

			beforeEach(function() {
				$scope.upvotePost(MOCK_POST.title, MOCK_POST);
			});

			it('should upvote post through the API with valid parameters', function() {
				expect(restService.upvotePost).toHaveBeenCalledWith(MOCK_POST.title, MOCK_POST.postid);
			});
		});

		describe('downvote post test', function() {

			beforeEach(function() {
				$scope.downvotePost(MOCK_POST.title, MOCK_POST);
			});

			it('should downvote post through the API with valid parameters', function() {
				expect(restService.downvotePost).toHaveBeenCalledWith(MOCK_POST.title, MOCK_POST.postid);
			});
		});

		describe('upvote comment test', function() {

			beforeEach(function() {
				$scope.upvoteComment(MOCK_POST.title, MOCK_POST.postid, MOCK_COMMENT);
			});

			it('should upvote comment through the API with valid parameters', function() {
				expect(restService.upvoteComment).toHaveBeenCalledWith(MOCK_POST.title, MOCK_POST.postid, MOCK_COMMENT.commentid);
			});
		});

		describe('downvote comment test', function() {

			beforeEach(function() {
				$scope.downvoteComment(MOCK_POST.title, MOCK_POST.postid, MOCK_COMMENT);
			});

			it('should downvote comment through the API with valid parameters', function() {
				expect(restService.downvoteComment).toHaveBeenCalledWith(MOCK_POST.title, MOCK_POST.postid, MOCK_COMMENT.commentid);
			});
		});
	});
});
