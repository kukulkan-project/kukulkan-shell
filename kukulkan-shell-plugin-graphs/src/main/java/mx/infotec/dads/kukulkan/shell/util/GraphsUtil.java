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
package mx.infotec.dads.kukulkan.shell.util;

import com.google.gson.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * GraphsUtil
 */
public class GraphsUtil {

    private GraphsUtil() { }

    public static void editFiles(Path projectPathPath, List<String> graphs) {
        Map<String, String> dependencies = new HashMap<>();
        dependencies.put("d3", "^3.4.4");
        dependencies.put("nvd3", "^1.7.1");
        dependencies.put("angular-nvd3", "1.0.9");
        if (!GraphsUtil.addBowerDependencies(dependencies, projectPathPath)) {
            System.out.println("The bower.json file was not found");
        }

        Map<String, String> overrides = new HashMap<>();
        overrides.put("angular-nvd3", "{\n" +
                "      \"main\": [\n" +
                "        \"dist/angular-nvd3.js\"\n" +
                "      ]\n" +
                "    }");
        if (!GraphsUtil.addBowerOverrides(overrides, projectPathPath)) {
            System.out.println("The bower.json file was not found");
        }

        if (!addModule("nvd3", projectPathPath)) {
            System.out.println("The /src/main/webapp/app/app.module.js file was not found or it does not have the correct format");
        }

        if (!GraphsUtil.addGraphsMenu(projectPathPath)) {
            System.out.println("The /src/main/webapp/app/layouts/navbar/navbar.html file was not found or it does not have the correct format");
        }
        addJsonGraphs(projectPathPath, graphs);
    }

    private static boolean addBowerDependencies(Map<String, String> dependencies, Path projectPathPath) {
        boolean success = false;
        JsonParser parser = new JsonParser();
        try {
            JsonElement element = parser.parse(new FileReader(projectPathPath.toString() + "/bower.json"));
            JsonObject bowerFile = element.getAsJsonObject();
            JsonObject depArr = bowerFile.getAsJsonObject("dependencies");
            for (Map.Entry<String, String> entry : dependencies.entrySet()) {
                if (depArr.get(entry.getKey()) == null) {
                    depArr.addProperty(entry.getKey(), entry.getValue());
                }
            }
            FileWriter file = new FileWriter(projectPathPath.toString() + "/bower.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = gson.toJson(bowerFile);
            file.write(prettyJson);
            file.flush();
            file.close();
            success = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return success;
    }

    private static boolean addBowerOverrides(Map<String, String> overrides, Path projectPathPath) {
        boolean success = false;
        JsonParser parser = new JsonParser();
        try {
            JsonElement element = parser.parse(new FileReader(projectPathPath.toString() + "/bower.json"));
            JsonObject bowerFile = element.getAsJsonObject();
            JsonObject overArr = bowerFile.getAsJsonObject("overrides");
            for (Map.Entry<String, String> entry : overrides.entrySet()) {
                if (overArr.get(entry.getKey()) == null) {
                    JsonObject value = (new JsonParser()).parse(entry.getValue()).getAsJsonObject();
                    overArr.add(entry.getKey(), value);
                }
            }
            FileWriter file = new FileWriter(projectPathPath.toString() + "/bower.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = gson.toJson(bowerFile);
            file.write(prettyJson);
            file.flush();
            file.close();
            success = true;
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("The bower.json file was not found");
        }

        return success;
    }

    private static boolean addGraphsMenu(Path projectPathPath) {
        boolean success = false;
        String menu = "<li id=\"graphs\"  ui-sref-active=\"active\" class=\"sidebar-list\">\n" +
                "\t\t\t\t<a ui-sref=\"graphs\" ng-click=\"vm.collapseNavbar();\" >\n" +
                "\t\t\t\t\t<span ng-click=\"vm.toggleNavbar()\" class=\"menu-icon glyphicon glyphicon-stats\"></span>\n" +
                "                    <span>Graphs</span>\n" +
                "\t\t\t\t\t <b class=\"caret\"></b>\n" +
                "\t\t\t\t</a>\n" +
                "\t\t\t</li>";

        try {
            File input = new File(projectPathPath.toString() + "/src/main/webapp/app/layouts/navbar/navbar.html");
            Document doc = Jsoup.parse(input, "UTF-8", "");

            Elements idGraphs = doc.select("li#graphs");

            if (idGraphs.isEmpty()) {
                Elements ul = doc.select("div#sidebar-wrapper>ul");
                if(ul.isEmpty()){
                    success = false;
                }else {
                    ul.first().append(menu);
                    FileWriter file = new FileWriter(projectPathPath.toString() + "/src/main/webapp/app/layouts/navbar/navbar.html");
                    file.write(doc.toString());
                    file.flush();
                    file.close();
                }
            }
            success = true;

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("The navbar.html file was not found");

        }
        return success;
    }

    private static boolean addModule(String module, Path projectPathPath) {
        boolean success = false;
        try {
            File file = new File(projectPathPath.toString() + "/src/main/webapp/app/app.module.js");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line, oldText = "";
            while ((line = reader.readLine()) != null) {
                oldText += line + "\r\n";
            }
            reader.close();

            if (!oldText.contains(module)) {
                String replacedText = oldText.replaceAll("'angular-loading-bar'", "'angular-loading-bar',\r\n'" + module + "'");

                FileWriter writer = new FileWriter(projectPathPath.toString() + "/src/main/webapp/app/app.module.js");
                writer.write(replacedText);
                writer.flush();
                writer.close();
            }
            success = true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return success;
    }

    private static void addJsonGraphs(Path projectPathPath, List<String> graphsList) {
        String pathCharts = projectPathPath.toString() + "/src/main/webapp/app/entities/d3/";
        String pathFile = pathCharts + "defaultCharts.json";

        if (!graphsList.contains("ALL")) {
            JsonParser parser = new JsonParser();
            JsonArray charArr;
            Gson gson = new Gson();
            try {
                File f = new File(pathFile);
                JsonObject chartsFile;

                if (f.exists()) {
                    JsonElement element = parser.parse(new FileReader(pathFile));
                    chartsFile = element.getAsJsonObject();
                    charArr = chartsFile.getAsJsonArray("charts");
                    for (String graph : graphsList) {
                        String json = gson.toJson(jsonGraph(graph));
                        JsonObject obj = parser.parse(json).getAsJsonObject();
                        charArr.add(obj);
                    }
                } else {
                    String jsonCharts = "{\n" +
                            "    \"charts\": [\n" +
                            "        \n" +
                            "    ]\n" +
                            "}\n";
                    chartsFile = parser.parse(jsonCharts).getAsJsonObject();
                    charArr = chartsFile.getAsJsonArray("charts");
                    for (String graph : graphsList) {
                        String json = gson.toJson(jsonGraph(graph));
                        JsonObject obj = parser.parse(json).getAsJsonObject();
                        charArr.add(obj);
                    }

                    File directory = new File(pathCharts);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                }
                FileWriter file = new FileWriter(pathFile);
                Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
                String prettyJson = gsonPretty.toJson(chartsFile);
                file.write(prettyJson);
                file.flush();
                file.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static Map<String, Object> jsonGraph(String graph) {
        Map<String, Object> graphs = new HashMap<>();

        switch (graph) {
            case "LINE_CHART":
                graphs.put("id", "LINE_CHART");
                graphs.put("title", "Line Chart");
                graphs.put("href", "lineChart");
                graphs.put("src", "content/images/graficasD3/LINE_CHART.png");
                break;
            case "BOX_PLOT":
                graphs.put("id", "BOX_PLOT");
                graphs.put("title", "Box Plot Chart");
                graphs.put("href", "boxPlot");
                graphs.put("src", "content/images/graficasD3/BOX_PLOT.png");
                break;
            case "BULLET":
                graphs.put("id", "BULLET");
                graphs.put("title", "Bullet Chart");
                graphs.put("href", "bullet");
                graphs.put("src", "content/images/graficasD3/BULLET.png");
                break;
            case "CANDLESTICK":
                graphs.put("id", "CANDLESTICK");
                graphs.put("title", "Candlestick Chart");
                graphs.put("href", "candlestick");
                graphs.put("src", "content/images/graficasD3/CANDLESTICK.png");
                break;
            case "CUMULATIVE_LINE":
                graphs.put("id", "CUMULATIVE_LINE");
                graphs.put("title", "Cumulative Line Chart");
                graphs.put("href", "cumulativeLine");
                graphs.put("src", "content/images/graficasD3/CUMULATIVE_LINE.png");
                break;
            case "DISCRETE_BAR":
                graphs.put("id", "DISCRETE_BAR");
                graphs.put("title", "DiscreteBar Chart");
                graphs.put("href", "discreteBar");
                graphs.put("src", "content/images/graficasD3/DISCRETE_BAR.png");
                break;
            case "DONUT_CHART":
                graphs.put("id", "DONUT_CHART");
                graphs.put("title", "Donut Chart");
                graphs.put("href", "donutChart");
                graphs.put("src", "content/images/graficasD3/DONUT_CHART.png");
                break;
            case "FORCE_DIRECTED":
                graphs.put("id", "FORCE_DIRECTED");
                graphs.put("title", "Force Directed Graph");
                graphs.put("href", "forceDirected");
                graphs.put("src", "content/images/graficasD3/FORCE_DIRECTED.png");
                break;
            case "HISTORICAL_BAR":
                graphs.put("id", "HISTORICAL_BAR");
                graphs.put("title", "HistoricalBar Chart");
                graphs.put("href", "historicalBar");
                graphs.put("src", "content/images/graficasD3/HISTORICAL_BAR.png");
                break;
            case "LINE_FOCUS":
                graphs.put("id", "LINE_FOCUS");
                graphs.put("title", "Line with Focus Chart");
                graphs.put("href", "lineFocus");
                graphs.put("src", "content/images/graficasD3/LINE_FOCUS.png");
                break;
            case "MULTIBAR_HORIZONTAL":
                graphs.put("id", "MULTIBAR_HORIZONTAL");
                graphs.put("title", "MultiBar Horizontal Chart");
                graphs.put("href", "multiBarHorizontal");
                graphs.put("src", "content/images/graficasD3/MULTIBAR_HORIZONTAL.png");
                break;
            case "MULTIBAR":
                graphs.put("id", "MULTIBAR");
                graphs.put("title", "MultiBar Chart");
                graphs.put("href", "multiBar");
                graphs.put("src", "content/images/graficasD3/MULTIBAR.png");
                break;
            case "MULTI_CHART":
                graphs.put("id", "MULTI_CHART");
                graphs.put("title", "Multi Chart");
                graphs.put("href", "multiChart");
                graphs.put("src", "content/images/graficasD3/MULTI_CHART.png");
                break;
            case "OHCL":
                graphs.put("id", "OHCL");
                graphs.put("title", "OHLC Chart");
                graphs.put("href", "ohcl");
                graphs.put("src", "content/images/graficasD3/OHCL.png");
                break;
            case "PARALELL":
                graphs.put("id", "PARALELL");
                graphs.put("title", "Paralell Cordinates");
                graphs.put("href", "paralell");
                graphs.put("src", "content/images/graficasD3/PARALELL.png");
                break;
            case "PIE_CHART":
                graphs.put("id", "PIE_CHART");
                graphs.put("title", "Pie Chart");
                graphs.put("href", "pieChart");
                graphs.put("src", "content/images/graficasD3/PIE_CHART.png");
                break;
            case "SCATTER_LINE":
                graphs.put("id", "SCATTER_LINE");
                graphs.put("title", "Scatter + Line Chart");
                graphs.put("href", "scatterLine");
                graphs.put("src", "content/images/graficasD3/SCATTER_LINE.png");
                break;
            case "SCATTER":
                graphs.put("id", "SCATTER");
                graphs.put("title", "Scatter Chart");
                graphs.put("href", "scatter");
                graphs.put("src", "content/images/graficasD3/SCATTER.png");
                break;
            case "SPARK_LINE":
                graphs.put("id", "SPARK_LINE");
                graphs.put("title", "SparkLine Chart");
                graphs.put("href", "sparkLine");
                graphs.put("src", "content/images/graficasD3/SPARK_LINE.png");
                break;
            case "STACKED_AREA":
                graphs.put("id", "STACKED_AREA");
                graphs.put("title", "Stacked Area Chart");
                graphs.put("href", "stackedArea");
                graphs.put("src", "content/images/graficasD3/STACKED_AREA.png");
                break;
            case "SUNBURST":
                graphs.put("id", "SUNBURST");
                graphs.put("title", "Sunburst Chart");
                graphs.put("href", "sunburst");
                graphs.put("src", "content/images/graficasD3/SUNBURST.png");
                break;
        }
        return graphs;
    }
}