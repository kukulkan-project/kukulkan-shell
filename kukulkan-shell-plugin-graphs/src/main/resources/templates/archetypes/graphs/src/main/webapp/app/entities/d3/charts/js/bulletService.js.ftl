(function () {
    'use strict';
    angular
        .module('${project.grammarName}App')
        .factory('bullet', ['$http', function ($http) {
            return {
                options: options,
                data: data,
                data1: data1,
                data2: data2
            };


            /**
             *  Data & Options Generators
             */
            function options() {
                return {
                    chart: {
                        type: 'bulletChart',
                        duration: 500
                    }
                };
            }
            function data() {
                return {
                    "title": "Revenue",
                    "subtitle": "US$, in thousands",
                    "ranges": [150, 225, 300],
                    "measures": [220],
                    "markers": [250]
                };
            }
            function data1() {
                return {
                    "title": "Profit",
                    "subtitle": "%",
                    "ranges": [20, 25, 30],
                    "measures": [15,18],
                    "markers": [26]
                };
            }
            function data2() {
                return {
                    "title": "New Customers",
                    "subtitle": "count",
                    "ranges": [1400, 2000, 2500],
                    "measures": [1000, 1650],
                    "markers": [2100]
                };
            }
        }]);
})();

//[
//    { "title": "Revenue", "subtitle": "US$, in thousands", "ranges": [150, 225, 300], "measures": [220, 270], "markers": [250] },
//    { "title": "Profit", "subtitle": "%", "ranges": [20, 25, 30], "measures": [21, 23], "markers": [26] },
//    { "title": "Order Size", "subtitle": "US$, average", "ranges": [350, 500, 600], "measures": [100, 320], "markers": [550] },
//    { "title": "New Customers", "subtitle": "count", "ranges": [1400, 2000, 2500], "measures": [1000, 1650], "markers": [2100] },
//    { "title": "Satisfaction", "subtitle": "out of 5", "ranges": [3.5, 4.25, 5], "measures": [3.2, 4.7], "markers": [4.4] }
//]
