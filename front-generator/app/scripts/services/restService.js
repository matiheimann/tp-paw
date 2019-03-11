'use strict';
define(['pawddit', 'jquery', 'services/storageService', 'services/messageService'], function(pawddit) {

	pawddit.factory('restService', ['$http', '$rootScope', '$location', '$q', 'storageService', 'messageService', 'url', function($http, $rootScope, $location, $q, storageService, messageService, url) {

		function httpGet(path, params) {
			params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
			return $http.get(url + path + params, authConfig())
				.then(function(response) { 
					return response.data; 
				})
				.catch(function(response) {
					redirect(response);
					return $q.reject(response);
				});
		}

		function httpPost(path, data, params, isMultipart) {
			params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
			if (isMultipart) {
				return $http.post(url + path + params, data, multipartConfig())
					.then(function(response) { 
						return response.data; 
					})
					.catch(function(response) {
						redirect(response);
						return $q.reject(response);
					});
			} else {
				return $http.post(url + path + params, Object.keys(data).length ? jQuery.param(data) : '', formConfig())
					.then(function(response) {
						return response.data; 
					})
					.catch(function(response) {
						redirect(response);
						return $q.reject(response);
					});
			}
		}

		function httpPut(path, data, params, isMultipart) {
			params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
			if (isMultipart) {
				return $http.put(url + path + params, data, multipartConfig())
					.then(function(response) { 
						return response.data; 
					})
					.catch(function(response) {
						redirect(response);
						return $q.reject(response);
					});
			} else {
				return $http.put(url + path + params, Object.keys(data).length ? jQuery.param(data) : '', formConfig())
					.then(function(response) { 
						return response.data; 
					})
					.catch(function(response) {
						redirect(response);
						return $q.reject(response);
					});
			}
		}

		function httpDelete(path, params) {
			params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
			return $http.delete(url + path + params, authConfig())
				.then(function(response) { 
					return response.data; 
				})
				.catch(function(response) {
					redirect(response);
					return $q.reject(response);
				});
		}

		function authConfig() {
			var authenticationToken = storageService.getAuthenticationToken();
			if (authenticationToken) {
				return {
					headers: {
						'X-AUTH-TOKEN': authenticationToken
					}
				};
			} else {
				return {};
			}
		}

		function formConfig() {
			var authenticationToken = storageService.getAuthenticationToken();
			if (authenticationToken) {
				return {
					transformRequest: angular.identity,
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded',
						'X-AUTH-TOKEN': authenticationToken
					}
				};
			} else {
				return {
					transformRequest: angular.identity,
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded'
					}
				};
			}
		}

		function multipartConfig() {
			var authenticationToken = storageService.getAuthenticationToken();
			if (authenticationToken) {
				return {
					transformRequest: angular.identity,
					headers: {
						'Content-Type': undefined,
						'X-AUTH-TOKEN': authenticationToken
					}
				};
			} else {
				return {
					transformRequest: angular.identity,
					headers: {
						'Content-Type': undefined,
					}
				};
			}
		}

		function dataURItoBlob(dataURI) {
			try {
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
			} catch (err) {
				return null;
			}
		}

		function redirect(response) {
			if (response.status === 403) {
				$rootScope.$broadcast('user:updated');
				$location.url('');
			} else if (response.status === 404) {
				switch (response.data.message) {
					case 'GroupNotFound':
						messageService.text = 'errorGroupNotFound.message';
						break;
					case 'PostNotFound':
						messageService.text = 'errorPostNotFound.message';
						break;
					case 'CommentNotFound':
						messageService.text = 'errorCommentNotFound.message';
						break;
					case 'UserNotFound':
						messageService.text = 'errorUserNotFound.message';
						break;
					case 'ImageNotFound':
						messageService.text = 'errorImageNotFound.message';
						break;
					case 'VerificationTokenNotFound':
						messageService.text = 'errorVerificationTokenNotFound.message';
						break;
					default:
				}
				messageService.icon = 'fa-times';
				$location.url('/info');
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
			getCommentReplies: function(name, pid, cid, params) {
				return httpGet('/groups/' + name + '/posts/' + pid + '/comments/' + cid + '/replies', {page: params.page});
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
			isLoggedIn: function() {
				return httpGet('/users/me', {});
			},
			getLoggedUser: function() {
				return httpGet('/users/me/profile', {});
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
				var data = {title: title, content: content};
				var formData = new FormData();
				formData.append('createPost', new Blob([JSON.stringify(data)], {type: 'application/json'}));
				if (file) {
					formData.append('image', dataURItoBlob(file));
				}
				return httpPost('/groups/' + groupname + '/posts', formData, {}, true);
			},
			createComment: function(groupname, pid, content, replyTo) {
				var data;
				if (replyTo) {
					data = {content: content, replyTo: replyTo};
				} else {
					data = {content: content};
				}
				
				var formData = new FormData();
				formData.append('createComment', new Blob([JSON.stringify(data)], {type: 'application/json'}));
				return httpPost('/groups/' + groupname + '/posts/' + pid + '/comments', formData, {}, true);
			},
			registerUser: function(email, username, password, repeatPassword) {
				var data = {email: email, username: username, password: password, repeatPassword: repeatPassword};
				var formData = new FormData();
				formData.append('createUser', new Blob([JSON.stringify(data)], {type: 'application/json'}));
				return httpPost('/users/register', formData, {}, true);
			},
			modifyProfilePicture: function(file) {
				var formData = new FormData();
				formData.append('image', dataURItoBlob(file));
				return httpPut('/users/me', formData, {}, true);
			}
		};
	}]);
});
