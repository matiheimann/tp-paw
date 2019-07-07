define(['services/restService', 'angular-mocks'], function() {

    describe('Rest Service', function() {

      var restService, url, $httpBackend, $q;

      var MOCK_GROUP = {name: 'group', description: 'the best group'};

      var MOCK_GROUPS = [{name: 'group1', description: 'first group'},
                           {name: 'group2', description: 'second group'},
                           {name: 'group3', description: 'third group'}];

      var MOCK_COMMENT = {name: 'group', pid: 1, cid: 1, content: "mock_content"};

      var MOCK_COMMENTS = [{name: 'group', pid: 1, cid: 1, content: "mock_content1"},
                           {name: 'group', pid: 1, cid: 2, content: "mock_content2"},
                           {name: 'group', pid: 1, cid: 3, content: "mock_content3"}];

      var MOCK_POST = {postid: '1', title: 'Mock post', groupname: 'group', content:'My content', userid: '1'};

      var MOCK_POSTS = [{postid: '1', title: 'My first post', groupname: 'group', content:'My content', userid: '1'},
                        {postid: '2', title: 'My second post', groupname: 'group', content: 'My content', userid: '1'},
                        {postid: '3', title: 'My third post', groupname: 'group', content: 'Lorem ipsum', userid: '1'}];

      var MOCK_IMAGE = {imageid: 1};

      var RESPONSE_ERROR = {detail: 'Not found.'};

      var DEFAULT_GETGROUPS_PARAMETERS = {page: 1, search: 'all'};

      var INVALID_GETGROUPS_PARAMETERS = {page: -1, search: 'all'};

      var DEFAULT_GETPOSTS_PARAMETERS = {page: 1, sort: 'new', time: 'all'};

      var INVALID_GETPOSTS_PARAMETERS = {page: -1, sort: 'new', time: 'all'};

      var DEFAULT_GETPOSTCOMMENTS_PARAMETERS = {page: 1};

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

            it('should be defined', function() {
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

        describe('.getGroups()', function() {

            var response;

            it('should be defined', function() {
                expect(restService.getGroups).toBeDefined();
            });

            it('should return a valid list of groups given default parameters', function() {
                $httpBackend.whenGET(url + '/groups?page=1&search=all').respond(200, $q.when(MOCK_GROUPS));

                restService.getGroups(DEFAULT_GETGROUPS_PARAMETERS)
                .then(function(res) {
                    response = res;
                });

                $httpBackend.flush();

                expect(response).toEqual(MOCK_GROUPS);
            });

            it('should return with a 404 when called with invalid parameters', function() {

                $httpBackend.whenGET(url + '/groups?page=-1&search=all').respond(404, RESPONSE_ERROR);

                restService.getGroups(INVALID_GETGROUPS_PARAMETERS)
                .catch(function(res) {
                    response = res.data;
                });

                $httpBackend.flush();

                expect(response).toEqual(RESPONSE_ERROR);
            });
        });

        describe('.getPost()', function(){

            var response;

            it('should be defined', function() {
                expect(restService.getPost).toBeDefined();
            });

            it('should return a valid post given a group name and a post id', function() {

                $httpBackend.whenGET(url + '/groups/' + MOCK_GROUP.name + '/posts/' + MOCK_POST.postid)
                .respond(200, $q.when(MOCK_POST));

                restService.getPost(MOCK_GROUP.name, MOCK_POST.postid)
                .then(function(res) {
                    response = res;
                });

                $httpBackend.flush();

                expect(response).toEqual(MOCK_POST);
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

            it('should return with a 404 when called with invalid parameters', function() {

                $httpBackend.whenGET(url + '/posts?page=-1&sort=new&time=all').respond(404, RESPONSE_ERROR);

                restService.getPosts(INVALID_GETPOSTS_PARAMETERS)
                .catch(function(res) {
                    response = res.data;
                });

                $httpBackend.flush();

                expect(response).toEqual(RESPONSE_ERROR);
            });
        });

        describe('.getGroupPosts', function() {

          var response;

          it('should be defined', function() {
            expect(restService.getGroupPosts).toBeDefined();
          });

          it('should return a valid list of posts given a group name and default parameters', function() {

            $httpBackend.whenGET(url + '/groups/' + MOCK_GROUP.name + '/posts?page=1&sort=new&time=all')
            .respond(200, $q.when(MOCK_POSTS));

            restService.getGroupPosts(MOCK_GROUP.name, DEFAULT_GETPOSTS_PARAMETERS)
            .then(function(res) {
                response = res;
            });

            $httpBackend.flush();

            expect(response).toEqual(MOCK_POSTS);
          });
        });

        describe('.getImage()', function() {

          var response;

          it('should return a valid image given a valid image id', function() {

            $httpBackend.whenGET(url + '/images/1').respond(200, $q.when(MOCK_IMAGE));

            restService.getImage(MOCK_IMAGE.imageid)
            .then(function(res) {
                response = res;
            });

            $httpBackend.flush();

            expect(response).toEqual(MOCK_IMAGE);

          });
        });

        describe('.getComment()', function() {

          var response;

          it('should return a valid comment given a valid group name, post id and comment id', function() {

            $httpBackend.whenGET(url + '/groups/' + MOCK_GROUP.name + '/posts/' + MOCK_POST.postid + '/comments/' + MOCK_COMMENT.cid)
            .respond(200, $q.when(MOCK_COMMENT));

            restService.getComment(MOCK_GROUP.name, MOCK_POST.postid, MOCK_COMMENT.cid)
            .then(function(res) {
                response = res;
            });

            $httpBackend.flush();

            expect(response).toEqual(MOCK_COMMENT);

          });
        });

        describe('.getPostComments()', function() {

          var response;

          it('should return a valid list of comments given a valid group name, post id and parameters', function() {

            $httpBackend.whenGET(url + '/groups/' + MOCK_GROUP.name + '/posts/' + MOCK_POST.postid + '/comments?page=1')
            .respond(200, $q.when(MOCK_COMMENTS));

            restService.getPostComments(MOCK_GROUP.name, MOCK_POST.postid, DEFAULT_GETPOSTCOMMENTS_PARAMETERS)
            .then(function(res) {
                response = res;
            });

            $httpBackend.flush();

            expect(response).toEqual(MOCK_COMMENTS);

          });
        });
    });
});
