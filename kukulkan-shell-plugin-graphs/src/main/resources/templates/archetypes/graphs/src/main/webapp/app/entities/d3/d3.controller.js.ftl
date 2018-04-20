(function () {
    'use strict';

    angular
        .module('${project.grammarName}App')
        .controller('GraphsController', GraphsController);

    GraphsController.$inject = ['$state', 'Graphs'];

    function GraphsController($state, Graphs) {
        var vm = this;

    
        Graphs.getCharts().then(function (response) {
            vm.charts = response.data.charts;
        });
       
    }
})();
