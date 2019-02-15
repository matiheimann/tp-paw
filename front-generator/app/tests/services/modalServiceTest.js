define(['services/modalService', 'angular-mocks'], function() {

  describe('Modal Service', function() {

  var modalService;

  beforeEach(module('pawddit'));
  beforeEach(inject(function(_modalService_) {
    		modalService = _modalService_;
    	}));

  it('Should be defined', function() {
    expect(modalService).toBeDefined();
  });

  });
});
