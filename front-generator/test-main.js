var allTestFiles = []
var TEST_REGEXP = /(spec|test)\.js$/i

// Get a list of all the test files to include
Object.keys(window.__karma__.files).forEach(function (file) {
  if (TEST_REGEXP.test(file)) {
    // Normalize paths to RequireJS module names.
    // If you require sub-dependencies of test files to be loaded as-is (requiring file extension)
    // then do not normalize the paths
    var normalizedTestModule = file.replace(/^\/base\/|\.js$/g, '')
    allTestFiles.push('../../' + normalizedTestModule)
  }
})

require.config({
    // Karma serves files under /base, which is the basePath from your config file
    baseUrl: '/base/app/scripts',

    paths: {
        affix: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/affix',
        alert: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/alert',
        angular: '../../bower_components/angular/angular',
        'angular-route': '../../bower_components/angular-route/angular-route',
        'angular-translate': '../../bower_components/angular-translate/angular-translate',
        button: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/button',
        bootstrap: '../../bower_components/bootstrap/dist/js/bootstrap',
        carousel: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/carousel',
        collapse: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/collapse',
        dropdown: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/dropdown',
        'es5-shim': '../../bower_components/es5-shim/es5-shim',
        jquery: '../../bower_components/jquery/dist/jquery',
        json3: '../../bower_components/json3/lib/json3',
        modal: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/modal',
        moment: '../../bower_components/moment/moment',
        popover: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/popover',
        requirejs: '../../bower_components/requirejs/require',
        scrollspy: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/scrollspy',
        tab: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/tab',
        tooltip: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/tooltip',
        transition: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/transition',
        'angular-bootstrap': '../../bower_components/angular-bootstrap/ui-bootstrap-tpls',
        'angular-cookies': '../../bower_components/angular-cookies/angular-cookies',
        'angular-timeago': '../../bower_components/angular-timeago/dist/angular-timeago',
        ngInfiniteScroll: '../../bower_components/ngInfiniteScroll/build/ng-infinite-scroll',
        'angular-mocks': '../../bower_components/angular-mocks/angular-mocks'
    },
    shim: {
        angular: {
            deps: [
                'jquery'
            ]
        },
        'angular-mocks': {
            deps: [
                'angular'
            ]
        },
        'angular-route': {
            deps: [
                'angular'
            ]
        },
        'angular-cookies': {
            deps: [
                'angular'
            ]
        },
        bootstrap: {
            deps: [
                'jquery',
                'modal'
            ]
        },
        modal: {
            deps: [
                'jquery'
            ]
        },
        tooltip: {
            deps: [
                'jquery'
            ]
        },
        'angular-translate': {
            deps: [
                'angular'
            ]
        },
        'angular-bootstrap': {
            deps: [
                'angular'
            ]
        },
        'angular-timeago': {
            deps: [
                'angular'
            ]
        },
        ngInfiniteScroll: {
            deps: [
                'angular'
            ]
        }
    },
    packages: [

    ],

    // ask Require.js to load these files (all our tests)
    deps: allTestFiles,

    // start test run, once Require.js is done
    callback:  window.__karma__.start

});
