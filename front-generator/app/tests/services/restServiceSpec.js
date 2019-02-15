define(['services/restService'], function() {

    describe('Rest Service', function() {
    	var restService, url, $httpBackend, $q;

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
    });
});
