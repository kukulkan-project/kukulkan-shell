package mx.infotec.dads.kukulkan.shell.generator;

import static mx.infotec.dads.kukulkan.metamodel.util.Validator.requiredNotEmpty;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.infotec.dads.kukulkan.engine.templating.service.TemplateService;
import mx.infotec.dads.kukulkan.metamodel.annotation.GeneratorComponent;
import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.util.FileUtil;
import mx.infotec.dads.kukulkan.metamodel.generator.Generator;
import mx.infotec.dads.kukulkan.metamodel.template.TemplateInfo;
import mx.infotec.dads.kukulkan.metamodel.util.PathProcessor;
import mx.infotec.dads.kukulkan.shell.commands.GraphsCommand;
import mx.infotec.dads.kukulkan.shell.services.WriterHelper;
import mx.infotec.dads.kukulkan.shell.util.GraphsUtil;
import mx.infotec.dads.kukulkan.shell.util.ProjectUtil;

/**
 * Generator for Angular 1.5.8, Spring boot and Spring Data
 *
 *
 */
@GeneratorComponent
public class GraphsGenerator implements Generator {

    private WriterHelper writer;

    public static final String GRAPHS_ARCHETYPE = "archetypes/graphs/";
    public GraphsGenerator(WriterHelper writer) {
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

        boolean pluginExist = false;

        Map<String, Object> model = new HashMap<>();
        Optional<ProjectConfiguration> project = ProjectUtil.readKukulkanFile(graphsContext.getOutputDir());
        String id = project.get().getId();
        graphsContext.setId(id);
        graphsContext.setGrammarName(id);

        String graphType = graphsContext.getGraphType().name();

        if(project.get().getPlugins().isEmpty())
            //if(project.get().containsPlugin("Graphs"))
        {
            PluginGraphs plugin = new PluginGraphs();
            plugin.addGraphs(graphsContext.getGraphType());
            for(GraphType graphName : plugin.getGraphs()){
                System.out.print(graphName.name());
            }
            project.get().addPlugin(plugin);
            ProjectUtil.writeKukulkanFile(project.get());
        }

        Map<String, String> dependencies = new HashMap<>();
        GraphsUtil.editFiles(graphsContext.getOutputDir());
        model.put("project", requiredNotEmpty(context.get(GraphsContext.class)));
//        for (TemplateInfo template : GraphsTemplateFactory.getGraphsTemplates()) {
//            String content = templateService.fillTemplate(template.getTemplatePath(), model);
//            FileUtil.saveToFile(createOutputPath(graphsContext, template), content);
//        }
        writer.copyDir(GraphsGenerator.class, GRAPHS_ARCHETYPE + "src/main/webapp/content/images/graficasD3","src/main/webapp/content/images/graficasD3");
        writer.copy(GRAPHS_ARCHETYPE + "src/main/webapp/app/entities/d3/d3.html","src/main/webapp/app/entities/d3/d3.html");
        //writer.copy(GRAPHS_ARCHETYPE + "src/main/webapp/app/entities/d3/defaultCharts.json","src/main/webapp/app/entities/d3/defaultCharts.json");
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
