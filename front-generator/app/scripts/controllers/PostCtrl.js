'use strict';
define(['pawddit'], function(pawddit) {

    pawddit.controller('PostCtrl', ['$scope', '$location', '$routeParams', 'restService', 'post', 'comments', 'commentsPageCount', 'url', function($scope, $location, $routeParams, restService, post, comments, commentsPageCount, url) {
        $scope.post = post;
        $scope.comments = comments;
		$scope.commentsPageCount = commentsPageCount.pageCount;

		$scope.page = $routeParams.page || 1;

		$scope.upvotePost = function(name, pid) {
			restService.upvotePost(name, pid).then(function(data) {
				updatePost(name, pid);
			});
		};

		$scope.downvotePost = function(name, pid) {
			restService.downvotePost(name, pid).then(function(data) {
				updatePost(name, pid);
			});
		};

		function updatePost(name, pid) {
			restService.getPost(name, pid).then(function(data) {
				$scope.post = data;
			});
		}

		$scope.upvoteComment = function(name, pid, cid) {
			restService.upvoteComment(name, pid, cid).then(function(data) {
				updateComment(name, pid, cid);
			});
		};

		$scope.downvoteComment = function(name, pid, cid) {
			restService.downvoteComment(name, pid, cid).then(function(data) {
				updateComment(name, pid, cid);
			});
		};

		function updateComment(name, pid, cid) {
			restService.getComment(name, pid, cid).then(function(data) {
				var index = $scope.comments.findIndex(function(comment) {
					return comment.commentid === cid;
				});
				$scope.comments[index] = data;
			});
		}

    }]);

});