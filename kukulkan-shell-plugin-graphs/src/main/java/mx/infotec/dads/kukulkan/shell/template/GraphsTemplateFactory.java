/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2018
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package mx.infotec.dads.kukulkan.shell.template;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import mx.infotec.dads.kukulkan.metamodel.template.TemplateInfo;
import mx.infotec.dads.kukulkan.shell.generator.GraphType;

/**
 * TemplateFactory for Immutable template list.
 *
 */
public class GraphsTemplateFactory {


    public static final String GRAPHS_TEMPLATE = "archetypes" + File.separator + "graphs";

    /**
     * Instantiates a new template factory.
     */
    private GraphsTemplateFactory() {
    }

    public static List<TemplateInfo> getGraphsTemplates(GraphType graphType) {
        List<TemplateInfo> templates = new ArrayList<>();

        templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/d3.controller.js.ftl"));
        templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/d3.service.js.ftl"));
        templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/d3.state.js.ftl"));
        templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/graph.state.js.ftl"));
        templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/graph.controller.js.ftl"));

        switch (graphType.name()) {
            case "BOX_PLOT": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/boxPlotService.js.ftl"));
                break;
            case "BULLET": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/bulletService.js.ftl"));
                break;
            case "CANDLESTICK": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/candlestickService.js.ftl"));
                break;
            case "CUMULATIVE_LINE": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/cumulativeLineService.js.ftl"));
                break;
            case "DISCRETE_BAR": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/discreteBarService.js.ftl"));
                break;
            case "DONUT_CHART": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/donutChartService.js.ftl"));
                break;
            case "FORCE_DIRECTED": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/forceDirectedService.js.ftl"));
                break;
            case "HISTORICAL_BAR": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/historicalBarService.js.ftl"));
                break;
            case "LINE_BAR": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/lineBarService.js.ftl"));
                break;
            case "LINE_CHART": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/lineChartService.js.ftl"));
                break;
            case "LINE_FOCUS": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/lineFocusService.js.ftl"));
                break;
            case "MULTIBAR_HORIZONTAL": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/multiBarHorizontalService.js.ftl"));
                break;
            case "MULTIBAR": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/multiBarService.js.ftl"));
                break;
            case "MULTI_CHART": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/multiChartService.js.ftl"));
                break;
            case "OHCL": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/ohclService.js.ftl"));
                break;
            case "PARALELL": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/paralellService.js.ftl"));
                break;
            case "PIE_CHART": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/pieChartService.js.ftl"));
                break;
            case "SCATTER_LINE": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/scatterLineService.js.ftl"));
                break;
            case "SCATTER": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/scatterService.js.ftl"));
                break;
            case "SPARK_LINE": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/sparkLineService.js.ftl"));
                break;
            case "STACKED_AREA": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/stackedAreaService.js.ftl"));
                break;
            case "SUNBURST": templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/sunburstService.js.ftl"));
                break;
            case "ALL":  templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/boxPlotService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/bulletService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/candlestickService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/cumulativeLineService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/discreteBarService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/donutChartService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/forceDirectedService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/historicalBarService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/lineBarService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/lineChartService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/lineFocusService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/multiBarHorizontalService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/multiBarService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/multiChartService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/ohclService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/paralellService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/pieChartService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/scatterLineService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/scatterService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/sparkLineService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/stackedAreaService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/sunburstService.js.ftl"));
                break;
            default:
                break;
        }
        return templates;
    }

}
