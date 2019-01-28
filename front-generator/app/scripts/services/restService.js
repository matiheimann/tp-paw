'use strict';
define(['pawddit', 'jquery'], function(pawddit) {

	pawddit.factory('restService', ['$http', 'url', function($http, url) {

		function httpGet(path, params) {
			var params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
			return $http.get(path + params).success(function(data) { return data; });
		}

		function httpPut(path, data, params) {
			var params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
			return $http.put(path + params, JSON.stringify(data)).success(function(data) { return data; });
		}

		function httpDelete(path, params) {
			var params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
			return $http.delete(path + params).success(function(data) { return data; });
		}

		function httpPost(path, data, params) {
			var params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
			return $http.post(path + params, JSON.stringify(data)).success(function(data) { return data; });
		}

		return {
			getPosts: function(params) {
				return httpGet(url + '/posts', params);
			},
			getPostsPageCount: function(params) {
				params.page = 0;
				return httpGet(url + '/posts', params);
			},
			getGroups: function(params) {
				return httpGet(url + '/groups', params);
			},
			getGroupsPageCount: function(params) {
				params.page = 0;
				return httpGet(url + '/groups', params);
			},
			getGroup: function(name) {
				return httpGet(url + '/groups/' + name);
			},
			getGroupPosts: function(name, params) {
				return httpGet(url + '/groups/' + name + '/posts', params);
			},
			getGroupPostsPageCount: function(name, params) {
				params.page = 0;
				return httpGet(url + '/groups/' + name + '/posts', params);
			},
			getPost: function(name, id) {
				return httpGet(url + '/groups/' + name + '/posts/' + id);
			},
			getPostComments: function(name, id, params) {
				return httpGet(url + '/groups/' + name + '/posts/' + id + '/comments', params);
			},
			getPostCommentsPageCount: function(name, id, params) {
				params.page = 0;
				return httpGet(url + '/groups/' + name + '/posts/' + id + '/comments', params);
			},
			getComment: function(name, pid, cid) {
				return httpGet(url + '/groups/' + name + '/posts/' + pid + '/comments/' + cid);
			},
			getProfile: function(name) {
				return httpGet(url + '/users/profile/' + name);
			},
			getProfileLastPosts: function(name) {
				return httpGet(url + '/users/profile/' + name + '/lastPosts');
			},
			getProfileLastComments: function(name) {
				return httpGet(url + '/users/profile/' + name + '/lastComments');
			},
			getMyProfile: function() {
				return httpGet(url + '/users/me');
			}
			getMySubscribedGroups: function(params) {
				return httpGet(url + '/users/me/subscribedGroups', params);
			},
			getMySubscribedGroupsPageCount: function(params) {
				params.page = 0;
				return httpGet(url + '/users/me/subscribedGroups', params);
			},
			getConfirmation: function(params) {
				return httpGet(url + '/users/confirm', params);
			},
			getMyFeedPosts: function(params) {
				return httpGet(url + '/users/me/feedPosts', params);
			},
			getMyFeedPostsPageCount: function(params) {
				params.page = 0;
				return httpGet(url + '/users/me/feedPosts', params);
			},
			getRecommendedGroups: function() {
				return httpGet(url + '/users/me/recommendedGroups');
			},
			subscribeGroup: function(name) {
				return httpPut(url + '/groups/' + name + '/subscribe');
			},
			unsubscribeGroup: function(name) {
				return httpPut(url + '/groups/' + name + '/unsubscribe');
			},
			upvotePost: function(name, id) {
				return httpPut(url + '/groups/' + name + '/posts/' + id + '/upvote');
			},
			downvotePost: function(name, id) {
				return httpPut(url + '/groups/' + name + '/posts/' + id + '/downvote');
			},
			upvoteComment: function(name, pid, cid) {
				return httpPut(url + '/groups/' + name + '/posts/' + pid + '/comments/' + cid + '/upvote');
			},
			downvoteComment: function(name, pid, cid) {
				return httpPut(url + '/groups/' + name + '/posts/' + pid + '/comments/' + cid + '/downvote');
			},
			deleteGroup: function(name) {
				return httpDelete(url + '/groups/' + name);
			},
			deletePost: function(name, id) {
				return httpDelete(url + '/groups/' + name + '/posts/' + id);
			},
			deleteComment: function(name, pid, cid) {
				return httpDelete(url + '/groups/' + name + '/posts/' + pid + '/comments/' + cid);
			}
		}
	}]);
});
