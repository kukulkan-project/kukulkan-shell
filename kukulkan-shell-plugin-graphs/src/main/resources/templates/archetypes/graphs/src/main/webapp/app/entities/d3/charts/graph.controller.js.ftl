(function () {
    'use strict';

    angular
        .module('${project.grammarName}App')
        .controller('ViewGraphController', ViewGraphController);

    ViewGraphController.$inject = ['$stateParams','$scope','$state'
    <#list listGraphs as name>
        <#if name == "LINE_CHART">
            ,'lineChart'
        <#elseif  name == "CUMULATIVE_LINE">
            ,'cumulativeLine'
        <#elseif  name == "STACKED_AREA">
            ,'stackedArea'
        <#elseif  name == "MULTIBAR">
            ,'multiBar'
        <#elseif name == "BULLET">
            ,'bullet'
        <#elseif name == "DISCRETE_BAR">
            ,'discreteBar'
        <#elseif name == "HISTORICAL_BAR">
            ,'historicalBar'
        <#elseif name  == "MULTIBAR_HORIZONTAL">
            ,'multiBarHorizontal'
        <#elseif name == "PIE_CHART">
            ,'pieChart'
        <#elseif name == "SCATTER">
            ,'scatter'
        <#elseif name == "LINE_FOCUS">
            ,'lineFocus'
        <#elseif name == "SCATTER_LINE">
            ,'scatterLine'
        <#elseif name == "DONUT_CHART">
            ,'donutChart'
        <#elseif name == "SPARK_LINE">
            ,'sparkLine'
        <#elseif name == "PARALELL">
            ,'paralell'
        <#elseif name == "MULTI_CHART">
            ,'multiChart'
        <#elseif name == "CANDLESTICK">
            ,'candlestick'
        <#elseif name == "SUNBURST">
            ,'sunburst'
        <#elseif name == "OHCL">
            ,'ohcl'
        <#elseif name == "BOX_PLOT">
            ,'boxPlot'
        <#elseif name == "FORCE_DIRECTED">
            ,'forceDirected'
        <#elseif name == "MULTI_CHART">
            ,'multiChart'
        <#elseif name == "ALL">
            ,'lineChart','cumulativeLine','stackedArea','multiBar','bullet','discreteBar',
            'historicalBar','multiBarHorizontal','pieChart','scatter','lineFocus',
            'scatterLine','donutChart','sparkLine','paralell','multiChart',
            'candlestick','sunburst','ohcl','boxPlot','forceDirected'
        </#if>
    </#list>
      ];

    function ViewGraphController($stateParams, $scope, $state
    <#list listGraphs as name>
        <#if name == "LINE_CHART">
            ,lineChart
        <#elseif name == "CUMULATIVE_LINE">
            ,cumulativeLine
        <#elseif name == "STACKED_AREA">
            ,stackedArea
        <#elseif name == "MULTIBAR">
            ,multiBar
        <#elseif name == "BULLET">
            ,bullet
        <#elseif name == "DISCRETE_BAR">
            ,discreteBar
        <#elseif name == "HISTORICAL_BAR">
            ,historicalBar
        <#elseif name == "MULTIBAR_HORIZONTAL">
            ,multiBarHorizontal
        <#elseif name == "PIE_CHART">
            ,pieChart
        <#elseif name == "SCATTER">
            ,scatter
        <#elseif name == "LINE_FOCUS">
            ,lineFocus
        <#elseif name == "SCATTER_LINE">
            ,scatterLine
        <#elseif name == "DONUT_CHART">
            ,donutChart
        <#elseif name == "SPARK_LINE">
            ,sparkLine
        <#elseif name == "PARALELL">
            ,paralell
        <#elseif name == "MULTI_CHART">
            ,multiChart
        <#elseif name == "CANDLESTICK">
            ,candlestick
        <#elseif name == "SUNBURST">
            ,sunburst
        <#elseif name == "OHCL">
            ,ohcl
        <#elseif name == "BOX_PLOT">
            ,boxPlot
        <#elseif name == "FORCE_DIRECTED">
            ,forceDirected
        <#elseif name == "MULTI_CHART">
            ,multiChart
        <#elseif name == "ALL">
            ,lineChart,cumulativeLine, stackedArea, multiBar, bullet, discreteBar, historicalBar, multiBarHorizontal, pieChart,
            scatter, lineFocus, scatterLine, donutChart, sparkLine, paralell, multiChart, candlestick,
            sunburst, ohcl, boxPlot, forceDirected
         </#if>
     </#list>) {

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
