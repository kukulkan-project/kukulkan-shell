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

import static mx.infotec.dads.kukulkan.metamodel.util.DefaultValues.getDefaulProjectConfiguration;
import static mx.infotec.dads.kukulkan.shell.commands.kukulkan.CommandHelper.createGeneratorContext;
import static mx.infotec.dads.kukulkan.shell.commands.maven.MavenCommands.MVN_COMMAND;
import static mx.infotec.dads.kukulkan.shell.commands.validation.UserInputValidation.validateParams;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.jline.utils.AttributedCharSequence;
import org.jline.utils.AttributedString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import mx.infotec.dads.kukulkan.engine.service.EngineGenerator;
import mx.infotec.dads.kukulkan.engine.service.InflectorService;
import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.util.FileUtil;
import mx.infotec.dads.kukulkan.shell.commands.AbstractCommand;
import mx.infotec.dads.kukulkan.shell.commands.valueprovided.KukulkanFilesProvider;
import mx.infotec.dads.kukulkan.shell.domain.Line;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.services.CommandService;
import mx.infotec.dads.kukulkan.shell.util.ProjectUtil;
import mx.infotec.dads.kukulkan.shell.util.TextFormatter;

/**
 * Generator Command.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class AppGeneration extends AbstractCommand {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AppGeneration.class);

    @Autowired
    ThreadPoolTaskExecutor executor;

    @Autowired
    private EngineGenerator engineGenerator;

    @Autowired
    private InflectorService inflectorService;

    @Autowired
    private CommandService commandService;

    /**
     * Command Shell for Generate all the entities that come from a file with .3
     * extension
     *
     * @param file
     *            the file
     */
    @ShellMethod("Generate all the entities that come from a file with .3k or .kukulkan extension")
    public void appGenerateCrud(@ShellOption(valueProvider = KukulkanFilesProvider.class) String fileName) {
        File file = Paths.get(navigator.getCurrentPath().toString(), fileName).toFile();
        GeneratorContext genCtx = createGeneratorContext(shellContext.getConfiguration(), file, inflectorService);
        engineGenerator.process(genCtx);
        FileUtil.saveToFile(genCtx);
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
    // @ShellMethodAvailability("availabilityAppGenerateProject")
    public void appGenerateProject(@NotNull String appName, @NotNull String packaging,
            @ShellOption(defaultValue = "NO_SQL_MONGODB") DatabaseType databaseType) {
        LOGGER.info("Generating Project from Archetype...");
        validateParams(appName, packaging);
        shellContext.setConfiguration(Optional.of(getDefaulProjectConfiguration()));
        GeneratorContext genCtx = createGeneratorContext(shellContext, appName, packaging, navigator.getCurrentPath(),
                databaseType);
        generationService.findGeneratorByName("angular-js-archetype-generator").ifPresent(generator -> {
            generationService.process(genCtx, generator);
            ProjectUtil.writeKukulkanFile(shellContext.getConfiguration().get());
            commandService.printf("Execute the command", "app-config --type FRONT_END");
        });
    }

    /**
     * Configurate a front end application, It execute the command "mvn package
     * -Pprod -DskiptTests".
     */
    @ShellMethod("Configurate the project")
    public void appConfig(@ShellOption(defaultValue = "FRONT_END") ConfigurationType type) {
        if (type.equals(ConfigurationType.FRONT_END)) {
            commandService.exec(new ShellCommand(MVN_COMMAND).addArg("package").addArg("-Pprod").addArg("-DskipTests"),
                    line -> {
                        commandService.printf(TextFormatter.formatLogText(line));
                        return Optional.ofNullable(new Line(line));
                    });
        } else {
            commandService.printf(new AttributedString("This configuration is not supported: " + type));
        }
    }

    @ShellMethod("Run a Spring-Boot App")
    public void appRun() {
        executor.execute(() -> commandService.exec(new ShellCommand(MVN_COMMAND).addArg("spring-boot:run"), line -> {
            commandService.printf(TextFormatter.formatLogText(line));
            return Optional.ofNullable(new Line(line));
        }));
    }

    /**
     * Command Shell that show the current project configuration applied to the
     * current context.
     *
     * @return List<AttributedString>
     * @throws JsonProcessingException
     */
    @ShellMethod("Show the current project configuration applied to the current context")
    public String appShowConfiguration() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        return objectMapper.writeValueAsString(shellContext.getConfiguration().orElse(new ProjectConfiguration()));
    }

    @ShellMethod("Show the current project configuration applied to the current context")
    public AttributedCharSequence testingCommand() {
        LOGGER.info("HOLA MUNDO");
        return new AttributedString("testing command");
    }

    // public Availability availabilityAppGenerateCrud() {
    // return Availability.unavailable("You must create a project before. try
    // using <app-generate-project> command");
    // }
    //
    // public Availability availabilityAppGenerateProject() {
    // return Availability.unavailable("You must create a project before. try
    // using <app-generate-project> command");
    // }
}
