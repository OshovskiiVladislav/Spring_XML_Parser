angular.module('app').controller('viewController', function ($scope, $http, $rootScope) {
    const contextPath = 'http://localhost:8189/parser';
    const apiPath = contextPath + '/api/v1/xml';

    $scope.viewFile = function () {
        var file = document.getElementById("viewXmlFile").files[0];
        var reader = new FileReader();

        reader.onload = function (e) {
            var textArea = document.getElementById("viewFileTextArea");
            textArea.value = e.target.result;
        };
        reader.readAsText(file);

        $scope.toggleViewTo('viewFile');
    };

    $scope.viewContent = function () {
        $scope.uploadFile(false);
        $scope.toggleViewTo('viewContent');
    };

    $scope.uploadFile = function (saveToDb) {
        var postLink = saveToDb ? '/upload' : '/view/content';

        var fd = new FormData();
        fd.append('file', $scope.fileToUpload);

        $http.post(apiPath + postLink, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(function (response) {
            if (saveToDb) {
                alert('Файл сохранен');
            } else {
                $scope.tags = response.data;
            }
        });
    };

    $scope.toggleViewTo = function (viewType) {
        $rootScope.isViewFile = viewType === 'viewFile';
        $rootScope.isViewContent = viewType === 'viewContent';
    };

    $scope.loadPage = function () {
        $scope.toggleViewTo('allHidden');
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