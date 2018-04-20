(function() {
    'use strict';
    angular
        .module('${project.grammarName}App')
        .factory('Graphs', ['$http',  function ($http) {

            return {
                getCharts: getCharts
            };
            /**
             *	Widgets load
             */
            function getCharts() {
                return $http.get('app/entities/d3/defaultCharts.json');
            }

        }]);
})();

