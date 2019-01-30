'use strict';
define(['pawddit', 'jquery'], function(pawddit) {

	pawddit.factory('restService', ['$http', 'url', function($http, url) {

		function httpGet(path, params) {
			var params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
			return $http.get(path + params).then(function(response) { return response.data; });
		}

		function httpPost(path, data, params) {
			var params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
			return $http.post(path + params, data, formDataConfig()).then(function(response) { return response.data; });
		}

		function httpPut(path, data, params) {
			var params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
			return $http.put(path + params, data, formDataConfig()).then(function(response) { return response.data; });
		}

		function httpDelete(path, params) {
			var params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
			return $http.delete(path + params).then(function(response) { return response.data; });
		}

		function formDataConfig() {
			return {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			};
		}

		function dataURItoBlob(dataURI) {
			if (dataURI != '') {
				// convert base64 to raw binary data held in a string
				var byteString = atob(dataURI.split(',')[1]);

				// separate out the mime component
				var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]

				// write the bytes of the string to an ArrayBuffer
				var ab = new ArrayBuffer(byteString.length);

				// create a view into the buffer
				var ia = new Uint8Array(ab);

				// set the bytes of the buffer to the correct values
				for (var i = 0; i < byteString.length; i++) {
					ia[i] = byteString.charCodeAt(i);
				}

				// write the ArrayBuffer to a blob, and you're done
				var blob = new Blob([ab], {type: mimeString});
				return blob;
			}
			else 
				return null;
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
			},
			createGroup: function(name, description) {
				var data = {name: name, description: description};
				var formData = new FormData();
				formData.append('createGroup', data);
				return httpPost(url + '/groups/', formData);
			},
			createPost: function(groupname, title, content, file) {
				var data = {title: title, content: content, file: file};
				var formData = new FormData();
				formData.append('createPost', data);
				return httpPost(url + '/groups/' + groupname + '/posts', formData);
			},
			createComment: function(groupname, pid, content, replyTo) {
				var data = {content: content, replyTo: replyTo};
				var formData = new FormData();
				formData.append('createComment', data);
				return httpPost(url + '/groups/' + groupname + '/posts/' + pid + '/comments', formData);
			},
			registerUser: function(email, username, password, repeatPassword) {
				var data = {email: email, username: username, password: password, password: password};
				var formData = new FormData();
				formData.append('createUser', data);
				return httpPost(url + '/users/register', formData);
			},
			modifyProfilePicture: function(file) {
				var data = {file: file};
				var formData = new FormData();
				formData.append('modifyUser', data);
				return httpPost(url + '/users/me', formData);
			}
		}
	}]);
});
