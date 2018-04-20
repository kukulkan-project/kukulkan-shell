package mx.infotec.dads.kukulkan.shell.generator;

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

import static mx.infotec.dads.kukulkan.metamodel.util.StringFormater.replaceDotByFileSeparator;
import static mx.infotec.dads.kukulkan.metamodel.util.Validator.requiredNotEmpty;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.shell.commands.GraphsCommand;
import mx.infotec.dads.kukulkan.shell.util.GraphsUtil;
import mx.infotec.dads.kukulkan.shell.util.ProjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.infotec.dads.kukulkan.engine.templating.service.TemplateService;
import mx.infotec.dads.kukulkan.engine.util.TemplateUtil;
import mx.infotec.dads.kukulkan.metamodel.annotation.GeneratorComponent;
import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.generator.Generator;
import mx.infotec.dads.kukulkan.metamodel.template.TemplateInfo;
import mx.infotec.dads.kukulkan.metamodel.template.TemplateType;
import mx.infotec.dads.kukulkan.metamodel.util.FileUtil;
import mx.infotec.dads.kukulkan.metamodel.util.PathProcessor;
import mx.infotec.dads.kukulkan.shell.template.GraphsTemplateFactory;
import mx.infotec.dads.kukulkan.shell.services.WriterService;
import org.springframework.shell.standard.ShellOption;

/**
 * Generator for Angular 1.5.8, Spring boot and Spring Data
 *
 *
 */
@GeneratorComponent
public class GraphsGenerator implements Generator {

    private WriterService writer;

    public static final String GRAPHS_ARCHETYPE = "archetypes/graphs/";
    public GraphsGenerator(WriterService writer) {
        this.writer = writer;
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(GraphsCommand.class);
    /** The template service. */
    @Autowired
    private TemplateService templateService;

    @Override
    public String getName() {
        return "graphs";
    }


    @Override
    public void process(GeneratorContext context) {
        GraphsContext graphsContext = requiredNotEmpty(context.get(GraphsContext.class));

        Map<String, Object> model = new HashMap<>();

        Optional<ProjectConfiguration> project = ProjectUtil.readKukulkanFile(graphsContext.getOutputDir());
        if(!project.isPresent())
        {
            LOGGER.error("This folder does not contain a kukulkan project");
             System.out.println("This folder does not contain a kukulkan project");
            return;
        }
        String id = project.get().getId();
        graphsContext.setId(id);
        graphsContext.setGrammarName(id);

        Map<String, String> dependencies = new HashMap<>();
        GraphsUtil.editFiles(graphsContext.getOutputDir());
        model.put("project", requiredNotEmpty(context.get(GraphsContext.class)));
        for (TemplateInfo template : GraphsTemplateFactory.GRAPHS_TEMPLATE_LIST) {
            String content = templateService.fillTemplate(template.getTemplatePath(), model);
            FileUtil.saveToFile(createOutputPath(graphsContext, template), content);
        }
        writer.copyDir(GraphsGenerator.class, GRAPHS_ARCHETYPE + "src/main/webapp/content/images/graficasD3","src/main/webapp/content/images/graficasD3");
        writer.copy(GRAPHS_ARCHETYPE + "src/main/webapp/app/entities/d3/d3.html","src/main/webapp/app/entities/d3/d3.html");
        writer.copy(GRAPHS_ARCHETYPE + "src/main/webapp/app/entities/d3/defaultCharts.json","src/main/webapp/app/entities/d3/defaultCharts.json");
        writer.copy(GRAPHS_ARCHETYPE + "src/main/webapp/app/entities/d3/charts/graph.html", "src/main/webapp/app/entities/d3/charts/graph.html");
    }

    /**
     * @param graphsContext
     * @param template
     */
    private Path createOutputPath(GraphsContext graphsContext, TemplateInfo template) {
        return PathProcessor.forPath(template.getFilePath()).joinBefore(graphsContext.getId())
                .replaceRegExp(".ftl", "").getAbsolutePath(graphsContext.getOutputDir().getParent());
    }
}
