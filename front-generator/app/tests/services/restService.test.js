define(['services/restService', 'angular-mocks'], function() {

    describe('Rest Service', function() {

    	var restService, url, $httpBackend, $q;
        var MOCK_GROUP = {name: 'group', description: 'the best group'};
        var MOCK_POSTS = [{postid: '1', title: 'My first post', groupname: 'group', content:'My content', userid: '1'},
                          {postid: '2', title: 'My second post', groupname: 'group', content: 'My content', userid: '1'},
                          {postid: '3', title: 'My third post', groupname: 'group', content: 'Lorem ipsum', userid: '1'}];

        var RESPONSE_ERROR = {detail: 'Not found.'};

        var DEFAULT_GETPOSTS_PARAMETERS = {page: 1, sort: 'new', time: 'all'};

    	beforeEach(module('pawddit'));

    	beforeEach(inject(function(_restService_, _url_, _$httpBackend_, _$q_) {
    		restService = _restService_;
    		url = _url_;
    		$httpBackend = _$httpBackend_;
    		$q = _$q_;
    	}));

        it('should be defined', function() {
            expect(restService).toBeDefined();
        });

        describe('.getGroup()', function(){

            var response;

            it('should be define', function() {
                expect(restService.getGroup).toBeDefined();
            });

            it('should return a valid group given a groupname', function() {

                $httpBackend.whenGET(url + '/groups/' + MOCK_GROUP.name).respond(200, $q.when(MOCK_GROUP));

                restService.getGroup(MOCK_GROUP.name)
                .then(function(res) {
                    response = res;
                });

                $httpBackend.flush();

                expect(response).toEqual(MOCK_GROUP);
            });

            it('should return with a 404 when called with an non-existent groupname', function() {

                $httpBackend.whenGET(url + '/groups/' + MOCK_GROUP.name).respond(404, RESPONSE_ERROR);

                restService.getGroup(MOCK_GROUP.name)
                .catch(function(res) {
                    response = res.data;
                });

                $httpBackend.flush();

                expect(response).toEqual(RESPONSE_ERROR);
            });

        });

        describe('.getPosts()', function() {

            var response;

            it('should be defined', function() {
                expect(restService.getPosts).toBeDefined();
            });

            it('should return a valid list of posts given default parameters', function() {
                $httpBackend.whenGET(url + '/posts?page=1&sort=new&time=all').respond(200, $q.when(MOCK_POSTS));

                restService.getPosts(DEFAULT_GETPOSTS_PARAMETERS)
                .then(function(res) {
                    response = res;
                });

                $httpBackend.flush();

                expect(response).toEqual(MOCK_POSTS);
            });
        });
    });
});
