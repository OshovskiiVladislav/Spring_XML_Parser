angular.module('app').controller('dbController', function ($scope, $http, $rootScope) {
    const contextPath = 'http://localhost:8189/parser';
    const apiPath = contextPath + '/api/v1/xml';

    $scope.uploadFile = function () {
        var fd = new FormData();
        fd.append('file', $scope.fileToUpload);

        $http.post(apiPath + '/upload', fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(function (response) {
            $scope.loadPage();
        });
    };

    $scope.loadPage = function () {
        $http({
            url: apiPath + '/view',
            method: 'GET'
        })
            .then(function (response) {
                $scope.tableData = response.data;
            });

        $scope.toggleViewTo('allHidden');
    };

    $scope.viewFile = function (id) {
        $http({
            url: apiPath + '/view/' + id,
            method: 'GET'
        })
            .then(function (response) {
                $scope.viewFileFromDbTextArea = response.data.data;
            });

        $scope.toggleViewTo('viewFile');
    };

    $scope.viewContent = function (id) {
        $http({
            url: apiPath + '/view/' + id + '/tags',
            method: 'GET'
        })
            .then(function (response) {
                $scope.tags = response.data;
            });

        $scope.toggleViewTo('viewContent');
    };

    $scope.toggleViewTo = function (viewType) {
        $rootScope.isViewFile = viewType === 'viewFile';
        $rootScope.isViewContent = viewType === 'viewContent';
    };

    $scope.loadPage();

}).directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);