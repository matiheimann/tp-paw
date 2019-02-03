'use strict';

define(function() {
    return {
        defaultRoutePath: '/404',
        routes: {
            '/': {
                templateUrl: 'views/posts.html',
                controller: 'PostsCtrl',
                resolve: {
                    group: [function() {
                        return null;
                    }],
                    posts: ['$route', 'restService', function($route, restService) {
                        var params = $route.current.params;
                        params.page = params.page || 1;
                        params.sort = params.sort || 'new';
                        params.time = params.time || 'all';
                        return restService.getPosts(params);
                    }],
                    postsPageCount: ['$route', 'restService', function($route, restService) {
                        var params = $route.current.params;
                        params.page = params.page || 1;
                        params.sort = params.sort || 'new';
                        params.time = params.time || 'all';
                        return restService.getPostsPageCount(params);
                    }]
                }
            },
            '/groups/:name': {
                templateUrl: 'views/posts.html',
                controller: 'PostsCtrl',
                resolve: {
                    group: ['$route', 'restService', function($route, restService) {
                        var params = $route.current.params;
                        return restService.getGroup(params.name);
                    }],
                    posts: ['$route', 'restService', function($route, restService) {
                        var params = $route.current.params;
                        params.page = params.page || 1;
                        params.sort = params.sort || 'new';
                        params.time = params.time || 'all';
                        return restService.getGroupPosts(params.name, params);
                    }],
                    postsPageCount: ['$route', 'restService', function($route, restService) {
                        var params = $route.current.params;
                        params.page = params.page || 1;
                        params.sort = params.sort || 'new';
                        params.time = params.time || 'all';
                        return restService.getGroupPostsPageCount(params.name, params);
                    }]
                }
            },
            '/groups/:name/posts/:id': {
                templateUrl: 'views/post.html',
                controller: 'PostCtrl',
                resolve: {
                    post: ['$route', 'restService', function($route, restService) {
                        var params = $route.current.params;
                        return restService.getPost(params.name, params.id);
                    }],
                    comments: ['$route', 'restService', function($route, restService) {
                        var params = $route.current.params;
                        params.page = params.page || 1;
                        return restService.getPostComments(params.name, params.id, params);
                    }],
                    commentsPageCount: ['$route', 'restService', function($route, restService) {
                        var params = $route.current.params;
                        params.page = params.page || 1;
                        return restService.getPostCommentsPageCount(params.name, params.id, params);
                    }]
                }
            },
            '/profile/:name': {
                templateUrl: 'views/profile.html',
                controller: 'ProfileCtrl',
                resolve: {
                    profile: ['$route', 'restService', function($route, restService) {
                        var params = $route.current.params;
                        return restService.getProfile(params.name);
                    }],
                    lastPosts: ['$route', 'restService', function($route, restService) {
                        var params = $route.current.params;
                        return restService.getProfileLastPosts(params.name);
                    }],
                    lastComments: ['$route', 'restService', function($route, restService) {
                        var params = $route.current.params;
                        return restService.getProfileLastComments(params.name);
                    }]
                }
            },
            '/404': {
                templateUrl: '404.html',
                controller: 'NotFoundCtrl'
            }
            /* ===== yeoman hook ===== */
            /* Do not remove these commented lines! Needed for auto-generation */
        }
    };
});
