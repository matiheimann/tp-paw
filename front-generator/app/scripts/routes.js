'use strict';

define([], function() {
    return {
        defaultRoutePath: '/',
        routes: {
            '/': {
                templateUrl: '/views/home.html',
                controller: 'HomeCtrl'
            },
            '/groups/:name': {
                templateUrl: '/views/group.html',
                controller: 'GroupCtrl'
            },
            '/groups/:name/posts/:id': {
                templateUrl: '/views/post.html',
                controller: 'PostCtrl'
            }
            /* ===== yeoman hook ===== */
            /* Do not remove these commented lines! Needed for auto-generation */
        }
    };
});
