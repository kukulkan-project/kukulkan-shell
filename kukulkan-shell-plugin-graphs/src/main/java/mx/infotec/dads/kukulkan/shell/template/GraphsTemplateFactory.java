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

/**
 * TemplateFactory for Immutable template list.
 */
public class GraphsTemplateFactory {

    private static final String GRAPHS_TEMPLATE = "archetypes" + File.separator + "graphs";

    /**
     * Instantiates a new template factory.
     */
    private GraphsTemplateFactory() { }

    public static List<TemplateInfo> getGraphsTemplates(List<String> graphs) {
        List<TemplateInfo> templates = new ArrayList<>();

        for (String graph : graphs) {
            if (graph.equals("ALL")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/boxPlotService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/bulletService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/candlestickService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/cumulativeLineService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/discreteBarService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/donutChartService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/forceDirectedService.js.ftl"));
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/historicalBarService.js.ftl"));
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
            }
            if (graph.equals("BOX_PLOT")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/boxPlotService.js.ftl"));
            } else if (graph.equals("BULLET")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/bulletService.js.ftl"));
            } else if (graph.equals("CANDLESTICK")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/candlestickService.js.ftl"));
            } else if (graph.equals("CUMULATIVE_LINE")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/cumulativeLineService.js.ftl"));
            } else if (graph.equals("DISCRETE_BAR")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/discreteBarService.js.ftl"));
            } else if (graph.equals("DONUT_CHART")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/donutChartService.js.ftl"));
            } else if (graph.equals("FORCE_DIRECTED")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/forceDirectedService.js.ftl"));
            } else if (graph.equals("HISTORICAL_BAR")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/historicalBarService.js.ftl"));
            } else if (graph.equals("LINE_CHART")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/lineChartService.js.ftl"));
            } else if (graph.equals("LINE_FOCUS")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/lineFocusService.js.ftl"));
            } else if (graph.equals("MULTIBAR_HORIZONTAL")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/multiBarHorizontalService.js.ftl"));
            } else if (graph.equals("MULTIBAR")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/multiBarService.js.ftl"));
            } else if (graph.equals("MULTI_CHART")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/multiChartService.js.ftl"));
            } else if (graph.equals("OHCL")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/ohclService.js.ftl"));
            } else if (graph.equals("PARALELL")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/paralellService.js.ftl"));
            } else if (graph.equals("PIE_CHART")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/pieChartService.js.ftl"));
            } else if (graph.equals("SCATTER_LINE")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/scatterLineService.js.ftl"));
            } else if (graph.equals("SCATTER")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/scatterService.js.ftl"));
            } else if (graph.equals("SPARK_LINE")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/sparkLineService.js.ftl"));
            } else if (graph.equals("STACKED_AREA")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/stackedAreaService.js.ftl"));
            } else if (graph.equals("SUNBURST")) {
                templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/js/sunburstService.js.ftl"));
            }
        }
        templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/d3.controller.js.ftl"));
        templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/d3.service.js.ftl"));
        templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/d3.state.js.ftl"));
        templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/graph.state.js.ftl"));
        templates.add(new TemplateInfo(GRAPHS_TEMPLATE, "src/main/webapp/app/entities/d3/charts/graph.controller.js.ftl"));

        return templates;
    }

}
