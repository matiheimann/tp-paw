'use strict';
define(['pawddit', 'jquery', 'services/restService', 'services/navbarService'], function(pawddit) {

    pawddit.controller('PostCtrl', ['$scope', '$rootScope', '$location', 'restService', 'navbarService', 'post', 'comments', 'url', function($scope, $rootScope, $location, restService, navbarService, post, comments, url) {
        navbarService.currentPage = 'post';
		navbarService.currentPageText = post.group.name;

        $scope.post = post;
        $scope.comments = comments;

		$scope.commentsPage = 1;

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

		$scope.$on('comment:deleted', function(event, deletedComment) {
			var index = $scope.comments.findIndex(function(comment) {
				return comment.commentid === deletedComment.commentid;
			});
			$scope.comments.splice(index, 1);
			if (deletedComment.replyTo) {
				index = $scope.comments.findIndex(function(comment) {
					return comment.commentid === deletedComment.replyTo.commentid;
				});
				$scope.comments[index].replies--;
			}
		});

		$scope.doSubmit = function(form, replyTo) {
			$scope.submitted = true;
			if (form.$valid) {
				if (!replyTo) {
					restService.createComment($scope.post.group.name, $scope.post.postid, $scope.newComment.content, null).then(function(data) {            
						$scope.newComment.content = '';
						$scope.comments.unshift(data);
						var commentsStartPos = $('.post-component-comments').offset().top;
						document.body.scrollTop = document.documentElement.scrollTop = commentsStartPos;
					});
				} else {
					restService.createComment($scope.post.group.name, $scope.post.postid, $scope.newCommentReply.content, replyTo).then(function(data) {            
						$scope.newCommentReply.content = ''; 
						activeReplyForm.slideUp();
      					activeReplyForm = null;
      					$scope.comments.unshift(data);
      					var index = $scope.comments.findIndex(function(comment) {
							return comment.commentid === replyTo;
						});
						$scope.comments[index].replies++;
      					var commentsStartPos = $('.post-component-comments').offset().top;
      					document.body.scrollTop = document.documentElement.scrollTop = commentsStartPos;
					});
				}
				$scope.submitted = false;
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

		$scope.loadMoreComments = function() {
			$scope.loadingComments = true;
			$scope.commentsPage++;
			var params = {page: $scope.commentsPage};
			restService.getPostComments(post.group.name, post.postid, params).then(function(data) {
				if (data.length > 0) {
					$scope.comments.push.apply($scope.comments, data);
					$scope.noMoreComments = data.length < 5;
				} else {
					$scope.noMoreComments = true;
				}
				$scope.loadingComments = false;
			}).catch(function(response) {
				$scope.loadingComments = false;
			});
		};

    }]);
});
