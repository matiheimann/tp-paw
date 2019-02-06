'use strict';
define(['pawddit'], function(pawddit) {

    pawddit.controller('PostCtrl', ['$scope', '$location', '$routeParams', 'restService', 'post', 'comments', 'commentsPageCount', 'url', function($scope, $location, $routeParams, restService, post, comments, commentsPageCount, url) {
        $scope.post = post.data;
        $scope.comments = comments.data;
		$scope.commentsPageCount = commentsPageCount.data.pageCount;

		$scope.page = $routeParams.page || 1;

		$scope.upvotePost = function(name, pid) {
			restService.upvotePost(name, pid).then(function(response) {
				updatePost(name, pid);
			});
		};

		$scope.downvotePost = function(name, pid) {
			restService.downvotePost(name, pid).then(function(response) {
				updatePost(name, pid);
			});
		};

		function updatePost(name, pid) {
			restService.getPost(name, pid).then(function(response) {
				$scope.post = response.data;
			});
		}

		$scope.upvoteComment = function(name, pid, cid) {
			restService.upvoteComment(name, pid, cid).then(function(response) {
				updateComment(name, pid, cid);
			});
		};

		$scope.downvoteComment = function(name, pid, cid) {
			restService.downvoteComment(name, pid, cid).then(function(response) {
				updateComment(name, pid, cid);
			});
		};

		function updateComment(name, pid, cid) {
			restService.getComment(name, pid, cid).then(function(response) {
				var index = $scope.comments.findIndex(function(comment) {
					return comment.commentid === cid;
				});
				$scope.comments[index] = response.data;
			});
		}

    }]);

});