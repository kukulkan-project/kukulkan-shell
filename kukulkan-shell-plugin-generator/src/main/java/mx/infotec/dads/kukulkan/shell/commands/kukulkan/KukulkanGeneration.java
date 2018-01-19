/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2016 Daniel Cortes Pichardo
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

import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.util.FileUtil;
import mx.infotec.dads.kukulkan.metamodel.util.PKGenerationStrategy;
import mx.infotec.dads.kukulkan.shell.commands.AbstractCommand;
import mx.infotec.dads.kukulkan.shell.commands.util.ProjectUtil;
import mx.infotec.dads.kukulkan.shell.commands.valueprovided.KukulkanFilesProvider;
import mx.infotec.dads.kukulkan.shell.util.TextFormatter;

/**
 * Generator Command.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class KukulkanGeneration extends AbstractCommand {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(KukulkanGeneration.class);

    /**
     * Command Shell for Generate all the entities that come from a file with .3
     * extension
     *
     * @param file
     *            the file
     */
    @ShellMethod("Generate all the entities that come from a file with .3k or .kukulkan extension")
    public void scaffoldingFromFile(@ShellOption(valueProvider = KukulkanFilesProvider.class) File file) {
        readProjectConfiguration(projectConfiguration, navigator.getCurrentPath());
        GeneratorContext genCtx = createGeneratorContext(projectConfiguration, file);
        generationService.findGeneratorByName("angularJs-spring").ifPresent(generator -> {
            generationService.process(genCtx, generator);
            FileUtil.saveToFile(genCtx);
        });
    }

    /**
     * Command Shell that Generate a Project from an Archetype Catalog.
     *
     * @param appName
     *            the app name
     * @param packaging
     *            the packaging
     */
    @ShellMethod("Generate a Project from an Archetype Catalog")
    public void generateProject(@NotNull String appName, @NotNull String packaging,
            @ShellOption(defaultValue = "NO_SQL_MONGODB") DatabaseType databaseType,
            @ShellOption(defaultValue = "NULL") PKGenerationStrategy pkGenerationType) {
        LOGGER.info("Generating Project...");
        validateProjectParams(appName, packaging);
        configProjectConfiguration(projectConfiguration, appName, packaging, navigator.getCurrentPath(), databaseType,
                pkGenerationType);
        GeneratorContext genCtx = new GeneratorContext(ProjectConfiguration.class, projectConfiguration);
        generationService.findGeneratorByName("angular-js-archetype-generator").ifPresent(generator -> {
            generationService.process(genCtx, generator);
            ProjectUtil.saveKukulkanFile(projectConfiguration);
            commandService.printf("Execute the command", "mvn-config-front-End");
            commandService.printf("\n\n\r");
        });
    }

    /**
     * Command Shell that show the current project configuration applied to the
     * current context.
     *
     * @return List<AttributedString>
     */
    @ShellMethod("Show the current project configuration applied to the current context")
    public List<AttributedString> generatorShowConfiguration() {
        List<AttributedString> attrList = new ArrayList<>();
        attrList.add(TextFormatter.formatLikeGlossary("AppName", projectConfiguration.getId()));
        attrList.add(TextFormatter.formatLikeGlossary("GroupId", projectConfiguration.getPackaging()));
        return attrList;
    }

}
