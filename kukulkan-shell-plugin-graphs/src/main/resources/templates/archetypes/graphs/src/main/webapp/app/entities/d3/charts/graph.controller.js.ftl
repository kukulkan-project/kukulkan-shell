(function () {
    'use strict';

    angular
        .module('${project.grammarName}App')
        .controller('ViewGraphController', ViewGraphController);

    ViewGraphController.$inject = ['$stateParams','$scope','$state',
        'lineChart','cumulativeLine','stackedArea','multiBar','bullet','discreteBar',
        'historicalBar','multiBarHorizontal','pieChart','scatter','lineFocus','scatterLine',
        'lineBar','donutChart','sparkLine','paralell','multiChart','candlestick',
        'sunburst','ohcl','boxPlot','forceDirected'];

    function ViewGraphController($stateParams, $scope, $state, lineChart,
        cumulativeLine, stackedArea, multiBar, bullet, discreteBar, historicalBar, multiBarHorizontal, pieChart,
        scatter, lineFocus, scatterLine, lineBar, donutChart, sparkLine, paralell, multiChart, candlestick,
        sunburst, ohcl, boxPlot, forceDirected) {
        var vm = this;

        
        vm.type = $stateParams.type;
        

        switch (vm.type) {
            case 'lineChart':
                vm.options = lineChart.options();
                vm.data = lineChart.data();
                break;
            case 'cumulativeLine':
                vm.options = cumulativeLine.options();
                vm.data = cumulativeLine.data();
                break;
            case 'stackedArea':
                vm.options = stackedArea.options();
                vm.data = stackedArea.data();
                break;
            case 'multiBar':
                vm.options = multiBar.options();
                vm.data = multiBar.data();
                break;
            case 'bullet':
                vm.options = bullet.options();
                vm.data = bullet.data();
                vm.data1 = bullet.data1();
                vm.data2 = bullet.data2();
                break;
            case 'discreteBar':
                vm.options = discreteBar.options();
                vm.data = discreteBar.data();
                break;
            case 'historicalBar':
                vm.options = historicalBar.options();
                vm.data = historicalBar.data();
                break;
            case 'multiBarHorizontal':
                vm.options = multiBarHorizontal.options();
                vm.data = multiBarHorizontal.data();
                break;
            case 'pieChart':
                vm.options = pieChart.options();
                vm.data = pieChart.data();
                break;
            case 'scatter':
                vm.options = scatter.options();
                vm.data = scatter.data();
                break;
            case 'lineFocus':
                vm.options = lineFocus.options();
                vm.data = lineFocus.data();
                break;
            case 'scatterLine':
                vm.options = scatterLine.options();
                vm.data = scatterLine.data();
                break;
            case 'lineBar':
                vm.options = lineBar.options();
                vm.data = lineBar.data();
                break;
            case 'donutChart':
                vm.options = donutChart.options();
                vm.data = donutChart.data();
                break;
            case 'sparkLine':
                vm.options = sparkLine.options();
                vm.data = sparkLine.data();
                break;
            case 'paralell':
                vm.options = paralell.options();
                vm.data = paralell.data();
                break;
            case 'multiChart':
                vm.options = multiChart.options();
                vm.data = multiChart.data();
                break;
            case 'candlestick':
                vm.options = candlestick.options();
                vm.data = candlestick.data();
                break;
            case 'sunburst':
                vm.options = sunburst.options();
                vm.data = sunburst.data();
                break;
            case 'ohcl':
                vm.options = ohcl.options();
                vm.data = ohcl.data();
                break;
            case 'boxPlot':
                vm.options = boxPlot.options();
                vm.data = boxPlot.data();
                break;
            case 'forceDirected':
                vm.options = forceDirected.options();
                vm.data = forceDirected.data();
                break;
            default:

        }
        
    }
})();
