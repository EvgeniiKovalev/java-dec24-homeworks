angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189';

    $scope.fillTable = function () {
        $http.get(contextPath + '/items')
            .then(function (response) {
                $scope.ProductsList = response.data;
            });
    };

    $scope.submitCreateNewProduct = function () {
        $http.post(contextPath + '/items', $scope.newProduct)
            .then(function (response) {
                $scope.fillTable();
                $scope.newProduct = {};
            })
            .catch(function (errorResponse) {
                console.error('Ошибка при создании товара:', errorResponse);
                const errorData = errorResponse.data;
                let errorMessage = 'Произошла неизвестная ошибка при создании товара';

                if (errorData) {
                    if (errorData.description) {
                        errorMessage = errorData.description;
                    }
                    else if (errorData.message) {
                        errorMessage = errorData.message;
                    }
                    else if (typeof errorData === 'string') {
                        errorMessage = errorData;
                    }
                }
                alert('Ошибка: ' + errorMessage);
            });
    };

    $scope.deleteProductById = function(productId) {
        $http({
            url: contextPath + '/items/' + productId,
            method: "DELETE"
        }).then(function (response) {
            $scope.fillTable();
        })
        .catch(function (errorResponse) {
            console.error('Ошибка при удалении товара:', errorResponse);
            const errorData = errorResponse.data;
            let errorMessage = 'Произошла неизвестная ошибка при удалении товара';

            if (errorData) {
                if (errorData.description) {
                    errorMessage = errorData.description;
                }
                else if (errorData.message) {
                    errorMessage = errorData.message;
                }
                else if (typeof errorData === 'string') {
                    errorMessage = errorData;
                }
            }
            alert('Ошибка: ' + errorMessage);
        });
    }

    $scope.fillTable();
});