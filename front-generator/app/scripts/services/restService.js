'use strict';
define(['pawddit', 'jquery'], function(pawddit) {

	pawddit.factory('restService', ['$http', '$cookies', '$q', 'url', function($http, $cookies, $q, url) {

		function httpGet(path, params) {
			params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
			return $http.get(url + path + params)
				.then(function(response) { 
					return response; 
				})
				.catch(function(response) {
					return $q.reject(response);
				});
		}

		function httpPost(path, data, params, isMultipart) {
			params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
			if (isMultipart) {
				return $http.post(url + path + params, data, multipartConfig())
					.then(function(response) { 
						return response; 
					})
					.catch(function(response) {
						return $q.reject(response);
					});
			} else {
				return $http.post(url + path + params, Object.keys(data).length ? jQuery.param(data) : '', formConfig())
					.then(function(response) { 
						return response; 
					})
					.catch(function(response) {
						return $q.reject(response);
					});
			}
		}

		function httpPut(path, data, params, isMultipart) {
			params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
			if (isMultipart) {
				return $http.put(url + path + params, data, multipartConfig())
					.then(function(response) { 
						return response; 
					})
					.catch(function(response) {
						return $q.reject(response);
					});
			} else {
				return $http.put(url + path + params, Object.keys(data).length ? jQuery.param(data) : '', formConfig())
					.then(function(response) { 
						return response; 
					})
					.catch(function(response) {
						return $q.reject(response);
					});
			}
		}

		function httpDelete(path, params) {
			params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
			return $http.delete(url + path + params)
				.then(function(response) { 
					return response; 
				})
				.catch(function(response) {
					return $q.reject(response);
				});
		}

		function formConfig() {
			return {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			};
		}

		function multipartConfig() {
			return {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			};
		}

		function dataURItoBlob(dataURI) {
			if (dataURI !== '') {
				// convert base64 to raw binary data held in a string
				var byteString = atob(dataURI.split(',')[1]);

				// separate out the mime component
				var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];

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
			} else {
				return null;
			}
		}

		return {
			getPosts: function(params) {
				return httpGet('/posts', {page: params.page, sort: params.sort, time: params.time});
			},
			getPostsPageCount: function(params) {
				params.page = 0;
				return httpGet('/posts', {page: params.page, sort: params.sort, time: params.time});
			},
			getGroups: function(params) {
				return httpGet('/groups', {page: params.page, search: params.search});
			},
			getGroupsPageCount: function(params) {
				params.page = 0;
				return httpGet('/groups', {page: params.page, search: params.search});
			},
			getGroup: function(name) {
				return httpGet('/groups/' + name, {});
			},
			getGroupPosts: function(name, params) {
				return httpGet('/groups/' + name + '/posts', {page: params.page, sort: params.sort, time: params.time});
			},
			getGroupPostsPageCount: function(name, params) {
				params.page = 0;
				return httpGet('/groups/' + name + '/posts', {page: params.page, sort: params.sort, time: params.time});
			},
			getPost: function(name, id) {
				return httpGet('/groups/' + name + '/posts/' + id, {});
			},
			getPostComments: function(name, id, params) {
				return httpGet('/groups/' + name + '/posts/' + id + '/comments', {page: params.page});
			},
			getPostCommentsPageCount: function(name, id, params) {
				params.page = 0;
				return httpGet('/groups/' + name + '/posts/' + id + '/comments', {page: params.page});
			},
			getComment: function(name, pid, cid) {
				return httpGet('/groups/' + name + '/posts/' + pid + '/comments/' + cid, {});
			},
			getImage: function(imageid) {
				return httpGet('/images/' + imageid, {});
			},
			getProfile: function(name) {
				return httpGet('/users/profile/' + name, {});
			},
			getProfileLastPosts: function(name) {
				return httpGet('/users/profile/' + name + '/lastPosts', {});
			},
			getProfileLastComments: function(name) {
				return httpGet('/users/profile/' + name + '/lastComments', {});
			},
			getLoggedUser: function() {
				return httpGet('/users/me', {});
			},
			getMySubscribedGroups: function(params) {
				return httpGet('/users/me/subscribedGroups', {page: params.page});
			},
			getMySubscribedGroupsPageCount: function(params) {
				params.page = 0;
				return httpGet('/users/me/subscribedGroups', {page: params.page});
			},
			getConfirmation: function(params) {
				return httpGet('/users/confirm', {token: params.token});
			},
			getMyFeedPosts: function(params) {
				return httpGet('/users/me/feedPosts', {page: params.page, sort: params.sort, time: params.time});
			},
			getMyFeedPostsPageCount: function(params) {
				params.page = 0;
				return httpGet('/users/me/feedPosts', {page: params.page, sort: params.sort, time: params.time});
			},
			getRecommendedGroups: function() {
				return httpGet('/users/me/recommendedGroups', {});
			},
			subscribeGroup: function(name) {
				return httpPut('/groups/' + name + '/subscribe', {}, false);
			},
			unsubscribeGroup: function(name) {
				return httpPut('/groups/' + name + '/unsubscribe', {}, false);
			},
			upvotePost: function(name, id) {
				return httpPut('/groups/' + name + '/posts/' + id + '/upvote', {}, false);
			},
			downvotePost: function(name, id) {
				return httpPut('/groups/' + name + '/posts/' + id + '/downvote', {}, false);
			},
			upvoteComment: function(name, pid, cid) {
				return httpPut('/groups/' + name + '/posts/' + pid + '/comments/' + cid + '/upvote', {}, false);
			},
			downvoteComment: function(name, pid, cid) {
				return httpPut('/groups/' + name + '/posts/' + pid + '/comments/' + cid + '/downvote', {}, false);
			},
			deleteGroup: function(name) {
				return httpDelete('/groups/' + name, {});
			},
			deletePost: function(name, id) {
				return httpDelete('/groups/' + name + '/posts/' + id, {});
			},
			deleteComment: function(name, pid, cid) {
				return httpDelete('/groups/' + name + '/posts/' + pid + '/comments/' + cid, {});
			},
			createGroup: function(name, description) {
				var data = {name: name, description: description};
				var formData = new FormData();
				formData.append('createGroup', new Blob([JSON.stringify(data)], {type: 'application/json'}));
				return httpPost('/groups', formData, {}, true);
			},
			createPost: function(groupname, title, content, file) {
				var data = {title: title, content: content, file: file};
				var formData = new FormData();
				formData.append('createPost', data);
				return httpPost('/groups/' + groupname + '/posts', formData, {}, true);
			},
			createComment: function(groupname, pid, content, replyTo) {
				var data = {content: content, replyTo: replyTo};
				var formData = new FormData();
				formData.append('createComment', data);
				return httpPost('/groups/' + groupname + '/posts/' + pid + '/comments', formData, {}, false);
			},
			registerUser: function(email, username, password, repeatPassword) {
				var data = {email: email, username: username, password: password, repeatPassword: repeatPassword};
				var formData = new FormData();
				formData.append('createUser', data);
				return httpPost('/users/register', formData, {}, false);
			},
			loginUser: function(username, password, rememberMe) {
				var data;
				if (rememberMe) {
					data = {jUsername: username, jPassword: password, jRememberme: 'on'};
				} else {
					data = {jUsername: username, jPassword: password};
				}
				return httpPost('/users/login', data, {}, false);
			},
			logoutUser: function() {
				return httpGet('/users/logout', {});
			},
			modifyProfilePicture: function(file) {
				var data = {file: file};
				var formData = new FormData();
				formData.append('modifyUser', data);
				return httpPost('/users/me', formData, {}, true);
			}
		};
	}]);
});
