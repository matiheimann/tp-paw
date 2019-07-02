'use strict';
define(['pawddit'], function(pawddit) {

	pawddit.factory('navbarService', [function() {
		var _navbar = {};
		return {
			getNavbar: function() {
				return _navbar;
			},
			setCurrentPage: function(page, text) {
				_navbar.currentPage = page;
				_navbar.currentPageText = text; 
			},
			getParams: function() {
				return {page: _navbar.page, sort: _navbar.sort, time: _navbar.time, feed: _navbar.feed};
			},
			incPage: function() {
				_navbar.page++;
			},
			setSort: function(sort) {
				_navbar.sort = sort;
			},
			setTime: function(time) {
				_navbar.time = time;
			},
			setFeed: function(feed) {
				_navbar.feed = feed;
			},
			resetPage: function() {
				_navbar.page = 1;
			},
			resetParams: function() {
				_navbar.page = 1;
				_navbar.sort = 'new';
				_navbar.time = 'all';
				_navbar.feed = _navbar.feed || false;
			}
		};
	}]);
	
});
