'use strict';
define(['pawddit', 'services/restService'], function(pawddit) {

	pawddit.controller('IndexCtrl', ['$scope', '$routeParams', '$location', 'restService', 'url', function($scope, $routeParams, $location, restService, url) {
		$scope.page = $routeParams.page || 1;
		$scope.sort = $routeParams.sort || 'new';
		$scope.time = $routeParams.time || 'all';

		$scope.buildURL = function(params) {
			var url = '#' + $location.path();
			var hasParams = false;

			var page = (params.page || $scope.page);
			if (page !== 1) {
				url += '?page=' + page;
				hasParams = true;
			}
			var sort = (params.sort || $scope.sort);
			if (sort !== 'new') {
				url += (hasParams ? '&' : '?') + 'sort=' + sort;
				hasParams = true;
			}
			var time = (params.time || $scope.time);
			if (time !== 'all') {
				url += (hasParams ? '&' : '?') + 'time=' + time;
				hasParams = true;
			}

			return url;
		};

		$scope.getImageURL = function(imageid) {
			return url + '/images/' + imageid;
		};

		$scope.$on('$locationChangeSuccess', function(event) {
			$scope.currentPath = $location.path();
		});

		$scope.test = function(i) {
			console.log(i);
		};

		$scope.gotoProfile = function(name) {
			$location.url('profile/' + name);
		};

		$scope.gotoPost = function(name, pid) {
			$location.url('groups/' + name + '/posts/' + pid);
		};
	}]);
});
