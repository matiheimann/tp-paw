'use strict';
define(['pawddit'], function(pawddit) {

    pawddit.controller('PostCtrl', ['$scope', '$location', '$routeParams', 'restService', 'post', 'comments', 'commentsPageCount', 'url', function($scope, $location, $routeParams, restService, post, comments, commentsPageCount, url) {
        $scope.post = post;
        $scope.comments = comments;
		$scope.commentsPageCount = commentsPageCount.pageCount;

		$scope.page = $routeParams.page || 1;
    }]);

});