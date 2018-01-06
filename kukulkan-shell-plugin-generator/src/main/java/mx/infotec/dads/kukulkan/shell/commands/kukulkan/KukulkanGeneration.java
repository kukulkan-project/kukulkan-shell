package mx.infotec.dads.kukulkan.shell.commands.kukulkan;

import static mx.infotec.dads.kukulkan.shell.commands.kukulkan.CommandHelper.configProjectConfiguration;
import static mx.infotec.dads.kukulkan.shell.commands.kukulkan.CommandHelper.createGeneratorContext;
import static mx.infotec.dads.kukulkan.shell.commands.kukulkan.CommandHelper.readProjectConfiguration;
import static mx.infotec.dads.kukulkan.shell.commands.validation.UserInputValidation.validateProjectParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.jline.utils.AttributedString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import mx.infotec.dads.kukulkan.metamodel.foundation.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.util.FileUtil;
import mx.infotec.dads.kukulkan.shell.commands.AbstractCommand;
import mx.infotec.dads.kukulkan.shell.commands.util.ProjectUtil;
import mx.infotec.dads.kukulkan.shell.commands.valueprovided.KukulkanFilesProvider;
import mx.infotec.dads.kukulkan.shell.util.TextFormatter;

/**
 * Generator Command
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@ShellComponent
public class KukulkanGeneration extends AbstractCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(KukulkanGeneration.class);

    @ShellMethod("Show the current output dir")
    public String outputdir() {
        return configurationProperties.getConfig().getOutputdir();
    }

    /**
     * Command Shell for Generate all the entities that come from a file with .3
     * extension
     * 
     * @param file
     */
    @ShellMethod("Generate all the entities that come from a file with .3k extension")
    public void generateEntitiesFromFile(@ShellOption(valueProvider = KukulkanFilesProvider.class) File file) {
        readProjectConfiguration(projectConfiguration, navigator.getCurrentPath());
        GeneratorContext genCtx = createGeneratorContext(projectConfiguration, file);
        generationService.findGeneratorByName("angularJs-spring").ifPresent(generator -> {
            generationService.process(genCtx, generator);
            FileUtil.saveToFile(genCtx);
        });
    }

    /**
     * Command Shell that Generate a Project from an Archetype Catalog
     * 
     * @param appName
     * @param groupId
     */
    @ShellMethod("Generate a Project from an Archetype Catalog")
    public void generateProject(@NotNull String appName, @NotNull String packaging) {
        LOGGER.info("Generating Project...");
        validateProjectParams(appName, packaging);
        configProjectConfiguration(projectConfiguration, appName, packaging, navigator.getCurrentPath());
        GeneratorContext genCtx = new GeneratorContext(projectConfiguration);
        generationService.findGeneratorByName("angular-js-archetype-generator").ifPresent(generator -> {
            generationService.process(genCtx, generator);
        });
        ProjectUtil.saveKukulkanFile(projectConfiguration);
        commandService.printf("Execute the command", "mvn-config-front-End");
        commandService.printf("\n\n\r");
    }

    /**
     * Command Shell that show the current project configuration applied to the
     * current context
     * 
     * @return List<AttributedString>
     */
    @ShellMethod("Show the current project configuration applied to the current context")
    public List<AttributedString> generatorShowConfiguration() {
        List<AttributedString> attrList = new ArrayList<>();
        attrList.add(TextFormatter.formatLikeGlossary("AppName", projectConfiguration.getId()));
        attrList.add(TextFormatter.formatLikeGlossary("GroupId", projectConfiguration.getGroupId()));
        return attrList;
    }
}
