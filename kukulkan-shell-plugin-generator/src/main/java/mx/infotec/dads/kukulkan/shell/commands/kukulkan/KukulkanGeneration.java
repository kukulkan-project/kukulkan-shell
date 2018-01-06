package mx.infotec.dads.kukulkan.shell.commands.kukulkan;

import static mx.infotec.dads.kukulkan.shell.commands.validation.UserInputValidation.createProjectValidation;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.jline.utils.AttributedString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import mx.infotec.dads.kukulkan.engine.service.GenerationService;
import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarMapping;
import mx.infotec.dads.kukulkan.engine.translator.dsl.KukulkanVisitor;
import mx.infotec.dads.kukulkan.generator.angularjs.domain.Rule;
import mx.infotec.dads.kukulkan.generator.angularjs.domain.RuleType;
import mx.infotec.dads.kukulkan.generator.angularjs.repository.RuleRepository;
import mx.infotec.dads.kukulkan.generator.angularjs.repository.RuleTypeRepository;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModelGroup;
import mx.infotec.dads.kukulkan.metamodel.foundation.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.JavaDomainModel;
import mx.infotec.dads.kukulkan.metamodel.util.FileUtil;
import mx.infotec.dads.kukulkan.metamodel.util.InflectorProcessor;
import mx.infotec.dads.kukulkan.metamodel.util.KukulkanConfigurationProperties;
import mx.infotec.dads.kukulkan.shell.commands.util.ProjectUtil;
import mx.infotec.dads.kukulkan.shell.commands.valueprovided.KukulkanFilesProvider;
import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.domain.ProjectContext;
import mx.infotec.dads.kukulkan.shell.services.CommandService;
import mx.infotec.dads.kukulkan.shell.util.TextFormatter;

/**
 * Util Commands
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@ShellComponent
public class KukulkanGeneration {

    private static final Logger LOGGER = LoggerFactory.getLogger(KukulkanGeneration.class);

    @Autowired
    private GenerationService generationService;
    @Autowired
    private RuleRepository ruleRepository;
    @Autowired
    private RuleTypeRepository ruleTypeRepository;

    @Autowired
    private CommandService commandService;

    @Autowired
    private Navigator navigator;

    private ProjectContext context;

    @Autowired
    private KukulkanConfigurationProperties prop;

    public KukulkanGeneration(ProjectContext context) {
        this.context = context;
    }

    @ShellMethod("Show the current output dir")
    public String outputdir() {
        return prop.getConfig().getOutputdir();

    }

    @ShellMethod("Create entities from file with .3k extension")
    public void generatorCreateEntitiesFromFile(@ShellOption(valueProvider = KukulkanFilesProvider.class) File file) {
        context.getProject().setOutputDir(navigator.getCurrentPath());
        // Create ProjectConfiguration
        configInflectorProcessor();
        // Create DataModel
        DomainModel dataModel = new JavaDomainModel();
        KukulkanVisitor semanticAnalyzer = new KukulkanVisitor();
        // Mapping DataContext into DataModel
        List<DomainModelGroup> dmgList = GrammarMapping.createSingleDataModelGroupList(semanticAnalyzer, file);
        dataModel.setDomainModelGroup(dmgList);
        // Create GeneratorContext
        LOGGER.info("Processing File...");
        GeneratorContext genCtx = new GeneratorContext(dataModel, context.getProject());
        // Process Activities
        generationService.findGeneratorByName("angularJs-spring").ifPresent(generator -> {
            generationService.process(genCtx, generator);
            FileUtil.saveToFile(genCtx);
        });
    }

    @ShellMethod("Create a project from archetype")
    public void generatorCreateProject(@NotNull String appName, @NotNull String groupId) {
        createProjectValidation(appName, groupId);
        context.getProject().setGroupId(groupId);
        context.getProject().setId(appName);
        context.getProject().setOutputDir(navigator.getCurrentPath());
        LOGGER.info("Processing Archetype...");
        GeneratorContext genCtx = new GeneratorContext(context.getProject());
        generationService.findGeneratorByName("angular-js-archetype-generator").ifPresent(generator -> {
            generationService.process(genCtx, generator);
        });
        commandService.printf("Execute the command", "mvn-config-front-End");
        commandService.printf("\n\n\r");
        ProjectUtil.saveKukulkanFile(context.getProject());
    }

    @ShellMethod("Create a project from archetype")
    public List<AttributedString> generatorShowConfiguration() {
        List<AttributedString> attrList = new ArrayList<>();
        attrList.add(TextFormatter.formatLikeGlossary("AppName", context.getProject().getId()));
        attrList.add(TextFormatter.formatLikeGlossary("GroupId", context.getProject().getGroupId()));
        return attrList;
    }

    public void configInflectorProcessor() {
        Rule rule = new Rule();
        RuleType ruleType = ruleTypeRepository.findAll().get(0);
        ruleType.setName("singular");
        rule.setRuleType(ruleType);
        Example<Rule> ruleExample = Example.of(rule);
        List<Rule> rulesList = ruleRepository.findAll(ruleExample);
        for (Rule item : rulesList) {
            InflectorProcessor.getInstance().addSingularize(item.getExpression(), item.getReplacement());
        }
    }
}
