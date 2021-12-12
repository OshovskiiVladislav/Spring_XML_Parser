(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'view/view.html',
                controller: 'viewController'
            })
            .when('/db', {
                templateUrl: 'db/db.html',
                controller: 'dbController'
            })
            .when('/view', {
                templateUrl: 'view/view.html',
                controller: 'viewController'
            });
    }

    function run($rootScope, $http, $localStorage) {
    }
})();