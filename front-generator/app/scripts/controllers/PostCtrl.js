'use strict';
define(['pawddit', 'jquery', 'services/restService', 'services/navbarService', 'directives/commentItem'], function(pawddit) {

    pawddit.controller('PostCtrl', ['$scope', '$rootScope', '$location', '$window', 'restService', 'navbarService', 'post', 'comments', 'url', function($scope, $rootScope, $location, $window, restService, navbarService, post, comments, url) {
        $window.document.title = 'Pawddit. | ' + post.title;
		navbarService.setCurrentPage('post', post.group.name);

        $scope.post = post;
        $scope.comments = comments.comments;
		$scope.commentsPage = 1;

		$scope.newComment = {};
		$scope.newCommentReply = {};
		var activeReplyForm;

		$scope.upvotePost = function(name, post) {
			restService.upvotePost(name, post.postid).then(function(data) {
				updatePost(name, post);
			});
		};

		$scope.downvotePost = function(name, post) {
			restService.downvotePost(name, post.postid).then(function(data) {
				updatePost(name, post);
			});
		};

		function updatePost(name, post) {
			restService.getPost(name, post.postid).then(function(data) {
				post = Object.assign(post, data);
			});
		}

		$scope.upvoteComment = function(name, pid, comment) {
			restService.upvoteComment(name, pid, comment.commentid).then(function(data) {
				updateComment(name, pid, comment);
			});
		};

		$scope.downvoteComment = function(name, pid, comment) {
			restService.downvoteComment(name, pid, comment.commentid).then(function(data) {
				updateComment(name, pid, comment);
			});
		};

		function updateComment(name, pid, comment) {
			restService.getComment(name, pid, comment.commentid).then(function(data) {
				comment = Object.assign(comment, data);
			});
		}

		$scope.$on('comment:deleted', function(event, deletedComment) {
			var replyToId = deletedComment.replyTo.commentid;
			deletedComment = Object.assign(deletedComment, {commentid: null})
			updatePost($scope.post.group.name, $scope.post);
			var replyTo = findComment($scope.comments, replyToId);
			replyTo.replies--;
		});

		$scope.doSubmit = function(form, replyTo) {
			$scope.submitted = true;
			if (form.$valid) {
				if (!replyTo) {
					restService.createComment($scope.post.group.name, $scope.post.postid, $scope.newComment.content, null).then(function(data) {            
						$scope.newComment.content = '';
						$scope.comments.unshift(data);
						$scope.post.comments++;
						var commentsStartPos = $('.post-component-comments').offset().top;
						document.body.scrollTop = document.documentElement.scrollTop = commentsStartPos;
					});
				} else {
					restService.createComment($scope.post.group.name, $scope.post.postid, $scope.newCommentReply.content, replyTo.commentid).then(function(data) {            
						$scope.newCommentReply.content = ''; 
						activeReplyForm.slideUp();
      					activeReplyForm = null;
      					var index = $scope.comments.findIndex(function(comment) {
							return comment.commentid === replyTo;
						});
						if (!replyTo.repliesList) {
							replyTo.repliesList = [];
						}
						replyTo.repliesList.unshift(data);
						replyTo.replies++;
						$scope.post.comments++;
					});
				}
				$scope.submitted = false;
			}
		};

		$scope.showReplyForm = function($event) {
			var replyForm = $($event.currentTarget).parent().parent().find('.reply-comment-form').first();
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
				if (data.comments.length > 0) {
					$scope.comments.push.apply($scope.comments, data.comments);
					$scope.noMoreComments = data.comments.length < 5;
				} else {
					$scope.noMoreComments = true;
				}
				$scope.loadingComments = false;
			}).catch(function(response) {
				$scope.loadingComments = false;
			});
		};

		$scope.loadMoreReplies = function(comment) {
			comment.repliesPage = comment.repliesPage || 1;
			var params = {page: comment.repliesPage};
			restService.getCommentReplies(post.group.name, post.postid, comment.commentid, params).then(function(data) {
				if (comment.repliesList) {
					comment.repliesList.push.apply(comment.repliesList, data.comments);
				} else {
					comment.repliesList = data.comments;
				}
				comment.repliesPage++;
			});
		};

		function findComment(comments, cid) {
			var i = 0;
			while(i < comments.length) {
				if (comments[i].commentid == cid)
					return comments[i];
				var comment = comments[i].repliesList ? findComment(comments[i].repliesList, cid) : null;
				if (comment != null)
					return comment;
				i++;
			}
			return null;
		}

    }]);
});
