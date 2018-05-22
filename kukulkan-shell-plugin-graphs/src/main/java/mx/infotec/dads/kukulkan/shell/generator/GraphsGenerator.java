package mx.infotec.dads.kukulkan.shell.generator;

import static mx.infotec.dads.kukulkan.metamodel.util.Validator.requiredNotEmpty;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import mx.infotec.dads.kukulkan.engine.service.FileUtil;
import mx.infotec.dads.kukulkan.engine.templating.service.TemplateService;
import mx.infotec.dads.kukulkan.metamodel.annotation.GeneratorComponent;
import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.Plugin;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.generator.Generator;
import mx.infotec.dads.kukulkan.metamodel.template.TemplateInfo;
import mx.infotec.dads.kukulkan.metamodel.util.PathProcessor;
import mx.infotec.dads.kukulkan.shell.commands.GraphsCommand;
import mx.infotec.dads.kukulkan.shell.services.WriterHelper;
import mx.infotec.dads.kukulkan.shell.template.GraphsTemplateFactory;
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

    public static final String GRAPHS_ARCHETYPE = "archetypes/graphs/src/main/webapp/";
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

        Map<String, Object> model = new HashMap<>();
        Optional<ProjectConfiguration> project = ProjectUtil.readKukulkanFile(graphsContext.getOutputDir());
        String id = project.get().getId();
        graphsContext.setId(id);
        graphsContext.setGrammarName(id);

        String graphType = graphsContext.getGraphType().name();

        Plugin plugin = project.get().getPlugin("GraphsD3");
        if( plugin == null )
        {
            plugin = new Plugin();
            plugin.setName("GraphsD3");
            project.get().addPlugin(plugin);
        }
        ObjectNode data = plugin.getData();
        if (data == null)
        {
            ObjectMapper mapper = new ObjectMapper();
            data = mapper.createObjectNode();
            data.putArray("Graphs");
            plugin.setData(data);
        }
        ArrayNode installedGraphs = (ArrayNode) data.get("Graphs");
        List<String> lista = new ArrayList<>();

        for (int i = 0; i< installedGraphs.size(); i ++)
        {
            if (installedGraphs.get(i).textValue().equals(graphType) ||
                    installedGraphs.get(i).textValue().equals("ALL"))
            {
                LOGGER.info(graphType + " is already installed");
                System.out.println(graphType + " is already installed");
                return;
            }
            lista.add(installedGraphs.get(i).textValue());
        }

        if(graphType.equals("ALL")){
            installedGraphs.removeAll();
            lista.clear();
            writer.copyDir(GraphsGenerator.class, GRAPHS_ARCHETYPE + "content/images/graficasD3","src/main/webapp/content/images/graficasD3");
            writer.copy(GRAPHS_ARCHETYPE + "app/entities/d3/defaultCharts.json","src/main/webapp/app/entities/d3/defaultCharts.json");
        }
        else
        {
            writer.copy(GRAPHS_ARCHETYPE + "content/images/graficasD3/"+graphType+".png",
                    "src/main/webapp/content/images/graficasD3/"+graphType+".png");
        }
        lista.add(graphType);
        installedGraphs.add(graphType);

        GraphsUtil.editFiles(graphsContext.getOutputDir(), graphType);
        model.put("project", requiredNotEmpty(context.get(GraphsContext.class)));
        model.put("listGraphs", lista);
        for (TemplateInfo template : GraphsTemplateFactory.getGraphsTemplates( graphsContext.getGraphType())) {
            String content = templateService.fillTemplate(template.getTemplatePath(), model);
            FileUtil.saveToFile(createOutputPath(graphsContext, template), content);
        }

        writer.copy(GRAPHS_ARCHETYPE + "app/entities/d3/d3.html","src/main/webapp/app/entities/d3/d3.html");
        writer.copy(GRAPHS_ARCHETYPE + "app/entities/d3/charts/graph.html", "src/main/webapp/app/entities/d3/charts/graph.html");
        ProjectUtil.writeKukulkanFile(project.get());
    }

    private Path createOutputPath(GraphsContext graphsContext, TemplateInfo template) {
        return PathProcessor.forPath(template.getFilePath()).joinBefore(graphsContext.getId())
                .replaceRegExp(".ftl", "").getAbsolutePath(graphsContext.getOutputDir().getParent());
    }
}
