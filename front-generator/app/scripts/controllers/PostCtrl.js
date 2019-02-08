'use strict';
define(['pawddit', 'jquery'], function(pawddit) {

    pawddit.controller('PostCtrl', ['$scope', '$rootScope', '$location', '$routeParams', 'restService', 'post', 'comments', 'commentsPageCount', 'url', function($scope, $rootScope, $location, $routeParams, restService, post, comments, commentsPageCount, url) {
        $scope.post = post;
        $scope.comments = comments;
		$scope.commentsPageCount = commentsPageCount.pageCount;

		$scope.page = $routeParams.page || 1;

		$scope.newComment = {};
		$scope.newCommentReply = {};
		var activeReplyForm;

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

		$scope.doSubmit = function(form, replyTo) {
			if (form.$valid) {
				if (!replyTo) {
					restService.createComment($scope.post.group.name, $scope.post.postid, $scope.newComment.content, null).then(function(data) {            
						$scope.newComment.content = '';
						$rootScope.$broadcast('comments:updated');
					});
				} else {
					restService.createComment($scope.post.group.name, $scope.post.postid, $scope.newCommentReply.content, replyTo).then(function(data) {            
						$scope.newCommentReply.content = ''; 
						activeReplyForm.slideUp();
      					activeReplyForm = null;
      					$rootScope.$broadcast('comments:updated');
					});
				} 
			}
		};

		$scope.showReplyForm = function($event) {
			var replyForm = $($event.currentTarget).parent().parent().find('.reply-comment-form');
      		if (replyForm.is(':hidden')) {
      			if (activeReplyForm) {
      				activeReplyForm.slideUp();
      				activeReplyForm = null;
      			}
      			$scope.newCommentReply.content = ''; 
        		replyForm.slideDown();
        		activeReplyForm = replyForm;
      		} else {
        		replyForm.slideUp();
        		activeReplyForm = null;
      		}
		};

		$scope.$on('comments:updated', function() {
			restService.getPostComments(post.group.name, post.postid, {page: $scope.page}).then(function(data) {
				$scope.comments = data;
			});
			restService.getPostCommentsPageCount(post.group.name, post.postid, {}).then(function(data) {
				$scope.commentsPageCount = data.pageCount;
			});
		});

    }]);
});
