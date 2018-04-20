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

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * GraphsUtil
 *
 *
 */
public class GraphsUtil {

    private GraphsUtil() {

    }

    public static boolean editFiles (Path projectPathPath)
    {
        Map<String, String> dependencies = new HashMap<>();
        dependencies.put("d3", "^3.4.4");
        dependencies.put("nvd3", "^1.7.1");
        dependencies.put("angular-nvd3", "1.0.9");
        if (!GraphsUtil.addBowerDependencies(dependencies, projectPathPath))
        {
            System.out.println("The bower.json file was not found");
            return false;
        }

        Map<String, String> overrides = new HashMap<>();
        overrides.put("angular-nvd3", "{\n" +
                "      \"main\": [\n" +
                "        \"dist/angular-nvd3.js\"\n" +
                "      ]\n" +
                "    }");
        if (!GraphsUtil.addBowerOverrides(overrides, projectPathPath))
        {
            System.out.println("The bower.json file was not found");
            return false;
        }

        if (!GraphsUtil.addGraphsMenu(projectPathPath))
        {
            System.out.println("The /src/main/webapp/app/layouts/navbar/navbar.html file was not found or it does not have the correct format");
            return false;
        }
        if (!addModule("nvd3",  projectPathPath))
        {
            System.out.println("The /src/main/webapp/app/app.module.js file was not found or it does not have the correct format");
        }
        return  true;
    }

    public static boolean addBowerDependencies(Map<String,String> dependencies, Path projectPathPath)
    {
        boolean success = false;
        JsonParser parser = new JsonParser();
        try {
            JsonElement element = parser.parse(new FileReader(projectPathPath.toString() + "/bower.json" ));
            JsonObject bowerFile = element.getAsJsonObject();
            JsonObject depArr = bowerFile.getAsJsonObject("dependencies");
            for (Map.Entry<String, String> entry : dependencies.entrySet()) {
                if (depArr.get(entry.getKey()) == null)
                {
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
        }catch (IOException ex)
        {

        }

        return success;
    }

    public static boolean addBowerOverrides(Map<String,String> overrides, Path projectPathPath)
    {
        boolean success = false;
        JsonParser parser = new JsonParser();
        try {
            JsonElement element = parser.parse(new FileReader(projectPathPath.toString() + "/bower.json" ));
            JsonObject bowerFile = element.getAsJsonObject();
            JsonObject overArr = bowerFile.getAsJsonObject("overrides");
            for (Map.Entry<String, String> entry : overrides.entrySet()) {
                if (overArr.get(entry.getKey()) == null)
                {
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
        }catch (IOException ex)
        {

        }

        return success;
    }

    public static boolean addGraphsMenu(Path projectPathPath)
    {
        boolean success = false;
        String menu = "<li id=\"graphs\" ng-class=\"{active: vm.$state.includes('admin')}\" uib-dropdown\n" +
                "                    dropdown ng-switch-when=\"true\">\n" +
                "                    <a class=\"dropdown-toggle\" uib-dropdown-toggle href=\"\">\n" +
                "                        <span>\n" +
                "                            <span class=\"glyphicon glyphicon-stats\"></span> <span class=\"hidden-sm\">Graphs</span> <b class=\"caret\"></b>\n" +
                "                        </span>\n" +
                "                    </a>\n" +
                "                    <ul class=\"dropdown-menu\" uib-dropdown-menu>\n" +
                "                        <li ui-sref-active=\"active\">\n" +
                "                            <a ui-sref=\"graphs\"\n" +
                "                               ng-click=\"vm.collapseNavbar()\">\n" +
                "                                <span class=\"glyphicon glyphicon-stats\"></span>&nbsp; <span >\n" +
                "                                    Graphs\n" +
                "                                </span>\n" +
                "                            </a>\n" +
                "                        </li>\n" +
                "                    </ul>\n" +
                "                </li>";

        try {
            File input = new File(projectPathPath.toString() + "/src/main/webapp/app/layouts/navbar/navbar.html");
            Document doc = Jsoup.parse(input, "UTF-8", "");

            Elements idGraphs = doc.select("li#graphs");

            if(idGraphs.isEmpty())
            {
                Elements ul = doc.select("div.navbar-collapse>ul");
                ul.first().append(menu);

                FileWriter file = new FileWriter(projectPathPath.toString() + "/src/main/webapp/app/layouts/navbar/navbar.html");
                file.write(doc.toString());
                file.flush();
                file.close();
            }
            success = true;

        }catch (IOException ex)
        {

        }
        return success;
    }

    public static boolean addModule(String module, Path projectPathPath)
    {
        boolean success = false;
        try
        {
            File file = new File( projectPathPath.toString() + "/src/main/webapp/app/app.module.js");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line, oldText = "";
            while((line = reader.readLine()) != null)
            {
                oldText += line + "\r\n";
            }
            reader.close();

            if(!oldText.contains(module))
            {
                String replacedText  = oldText.replaceAll("'angular-loading-bar'", "'angular-loading-bar',\r\n'" + module +"'");

                FileWriter writer = new FileWriter(projectPathPath.toString() + "/src/main/webapp/app/app.module.js");
                writer.write(replacedText);
                writer.flush();
                writer.close();
            }
            success = true;
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return success;
    }
}
