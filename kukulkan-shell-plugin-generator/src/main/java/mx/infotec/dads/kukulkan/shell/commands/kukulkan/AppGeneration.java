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
import static mx.infotec.dads.kukulkan.shell.commands.kukulkan.CommandHelper.LAYERS_OPTION_DEFAULT_VALUE;
import static mx.infotec.dads.kukulkan.shell.commands.kukulkan.CommandHelper.computeExcludedLayers;
import static mx.infotec.dads.kukulkan.shell.commands.kukulkan.CommandHelper.createGeneratorContext;
import static mx.infotec.dads.kukulkan.shell.commands.validation.UserInputValidation.validateParams;
import static mx.infotec.dads.kukulkan.shell.services.impl.CommandServiceImpl.isWindowsOS;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.jline.utils.AttributedCharSequence;
import org.jline.utils.AttributedString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import mx.infotec.dads.kukulkan.engine.service.EngineGenerator;
import mx.infotec.dads.kukulkan.engine.service.FileUtil;
import mx.infotec.dads.kukulkan.engine.translator.database.DataBaseTranslatorService;
import mx.infotec.dads.kukulkan.engine.translator.database.SchemaAnalyzerException;
import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.conventions.CodeStandard;
import mx.infotec.dads.kukulkan.metamodel.foundation.Database;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.translator.TranslatorService;
import mx.infotec.dads.kukulkan.shell.commands.AbstractCommand;
import mx.infotec.dads.kukulkan.shell.commands.git.service.GitCommandsService;
import mx.infotec.dads.kukulkan.shell.commands.navigation.FileNavigationCommands;
import mx.infotec.dads.kukulkan.shell.commands.valueprovided.KukulkanFilesProvider;
import mx.infotec.dads.kukulkan.shell.domain.Line;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.services.PrintService;
import mx.infotec.dads.kukulkan.shell.services.impl.CommandServiceImpl;
import mx.infotec.dads.kukulkan.shell.util.GeneratorException;
import mx.infotec.dads.kukulkan.shell.util.ProjectUtil;
import mx.infotec.dads.kukulkan.shell.util.TextFormatter;

/**
 * Generator Command.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class AppGeneration extends AbstractCommand {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AppGeneration.class);

    public static final String MVN_WRAPPER_COMMAND = "mvnw";

    @Autowired
    private DomainModelDslWriter dslWriter;

    @Autowired
    ThreadPoolTaskExecutor executor;

    @Autowired
    private AppInput appInput;

    @Autowired
    private EngineGenerator engineGenerator;

    @Autowired
    private DataBaseTranslatorService dataBaseTranslatorService;

    @Autowired
    private PrintService printService;

    @Autowired
    private GitCommandsService gitCommandsService;

    @Autowired
    private FileNavigationCommands fileNavigationCommands;

    @Autowired
    @Qualifier("grammarTranslatorService")
    private TranslatorService translatorService;

    /**
     * Command Shell for Generate all the entities that come from a file with .3
     * extension
     *
     * @param fileName
     *            the file
     */
    @ShellMethod("Generate all the entities that come from a file with .3k or .kukulkan extension")
    public void addEntitiesFromLanguage(@ShellOption(valueProvider = KukulkanFilesProvider.class) String fileName,
            @ShellOption(valueProvider = LayersValueProvider.class, defaultValue = LAYERS_OPTION_DEFAULT_VALUE) String excludeLayers) {
        File file = Paths.get(navigator.getCurrentPath().toString(), fileName).toFile();
        computeExcludedLayers(shellContext, excludeLayers);
        ProjectConfiguration pConf = shellContext.getConfiguration()
                .orElseThrow(() -> new GeneratorException("No ProjectConfiguration Found"));
        GeneratorContext genCtx = createGeneratorContext(pConf, file, translatorService);
        FileUtil.deleteFiles(navigator.getCurrentPath().getParent().toString(), pConf.getEntities());
        engineGenerator.process(genCtx);
        ProjectUtil.addEntities(genCtx, pConf);
        FileUtil.saveToFile(genCtx);
        ProjectUtil.writeKukulkanFile(pConf);
        config(ConfigurationType.FRONT_END);
    }

    @ShellMethod("Create a 3k file with the entities definition extracted from database")
    public void createDomainModelFromDatabase(@ShellOption(defaultValue = "SQL_MYSQL") DatabaseType source,
            @ShellOption(defaultValue = "") String fileName) {
        ProjectConfiguration pConf = new ProjectConfiguration();
        pConf.setTargetDatabase(new Database(source));
        GeneratorContext genCtx = createGeneratorContext(Optional.of(pConf), appInput.readDataStore(source),
                dataBaseTranslatorService);
        Optional<DomainModel> maybeDomainModel = genCtx.get(DomainModel.class);
        if (maybeDomainModel.isPresent()) {
            if (!StringUtils.isEmpty(fileName)) {
                dslWriter.toDslFile(maybeDomainModel.get(), navigator.getCurrentPath().resolve(fileName));
            } else {
                printService.info(dslWriter.toDslContent(maybeDomainModel.get()));
            }
        } else {
            printService.error("No domain model obtained from database");
        }
    }

    @ShellMethod("Generate all the entities that come from a file with .3k or .kukulkan extension")
    public void rollBack() {
        ProjectConfiguration pConf = shellContext.getConfiguration()
                .orElseThrow(() -> new GeneratorException("No ProjectConfiguration Found"));
        FileUtil.deleteFiles(navigator.getCurrentPath().getParent().toString(), pConf.getEntities());
    }

    /**
     * Command Shell that Generate a Project from an Archetype Catalog.
     *
     * @param appName
     *            the app name
     * @param packaging
     *            the packaging
     * @param databaseType
     *            the database type DatabaseType.NO_SQL_MONGODB /
     *            DatabaseType.SQL_MYSQL
     * @see DatabaseType
     */
    @ShellMethod("Generate a Project from an Archetype Catalog")
    public void createProject(@NotNull String appName, @NotNull String packaging,
            @ShellOption(defaultValue = "NO_SQL_MONGODB") DatabaseType databaseType,
            @ShellOption(defaultValue = "ANGULARJS") FrontEndArchetype frontEndArchetype,
            @ShellOption(defaultValue = "DEFAULT") CodeStandard codeStandard) {
        LOGGER.info("Generating Project from Archetype...");
        validateParams(appName, packaging);
        ProjectConfiguration pConf = getDefaulProjectConfiguration();
        pConf.setCodeStandard(codeStandard);
        shellContext.setConfiguration(Optional.of(pConf));
        GeneratorContext genCtx = createGeneratorContext(shellContext, appName, packaging, navigator.getCurrentPath(),
                databaseType, frontEndArchetype);
        generationService.findGeneratorByName("angular-js-archetype-generator").ifPresent(generator -> {
            generationService.process(genCtx, generator);
            ProjectUtil.writeKukulkanFile(shellContext.getConfiguration().get());
            fileNavigationCommands.cd(appName);
            gitCommandsService.addAll("First version of project", "Kukulkan Team <suport@kukulkan.org.mx>");
            printService.print("Execute the command", "config --type FRONT_END");
        });

    }

    /**
     * Configurate a front end application, It execute the command "mvn package
     * -Pprod -DskiptTests".
     * 
     * @param type
     *            Config type
     */
    @ShellMethod("Configurate the project")
    public void config(@ShellOption(defaultValue = "FRONT_END") ConfigurationType type) {
        if (type.equals(ConfigurationType.FRONT_END)) {
            commandService.exec(new ShellCommand(getMavenWrapperCommand(), "package", "-Pprod", "-DskipTests"),
                    line -> {
                        printService.print(TextFormatter.formatLogText(line));
                        return Optional.ofNullable(new Line(line));
                    });
        } else {
            printService.print(new AttributedString("This configuration is not supported: " + type));
        }
    }

    /**
     * Configurate a front end application, It execute the command "mvn package
     * -Pprod -DskiptTests".
     * 
     * @param type
     *            Config type
     */
    @ShellMethod("Configurate the project")
    public void sonar() {
        commandService.exec(new ShellCommand(getMavenWrapperCommand(), "sonar:sonar"), line -> {
            printService.print(TextFormatter.formatLogText(line));
            return Optional.ofNullable(new Line(line));
        });
    }

    /* YARN */
    @ShellMethod("Execute local installed yarn")
    @ShellMethodAvailability("isLocalYarnInstalled")
    public void yarn(@ShellOption(defaultValue = "") String arguments) {
        executor.execute(() -> commandService.exec(
                navigator.getCurrentPath().resolve(YarnCommandHelper.YARN_BIN_LOCATION).toFile(),
                getYarnCommand(arguments), line -> {
                    printService.print(TextFormatter.formatLogText(line));
                    return Optional.ofNullable(new Line(line));
                }));
    }

    public ShellCommand getYarnCommand(String args) {
        List<String> yarnArgs = new ArrayList<>();
        yarnArgs.addAll(Arrays.asList(args.split(" ")));
        yarnArgs.add(YarnCommandHelper.YARN_CWD_OPTION);
        yarnArgs.add(navigator.getCurrentPath().toString());
        return new ShellCommand(isWindowsOS() ? YarnCommandHelper.YARN_COMMAND : "./" + YarnCommandHelper.YARN_COMMAND,
                yarnArgs.toArray(new String[yarnArgs.size()]));
    }

    public Availability isLocalYarnInstalled() {
        return navigator.getCurrentPath().resolve(YarnCommandHelper.YARN_BIN_LOCATION).toFile().exists()
                ? Availability.available()
                : Availability.unavailable("Yarn local installation was not found in this directory");
    }

    /* GULP */

    @ShellMethod("Execute local installed gulp")
    @ShellMethodAvailability("isLocalGulpInstalled")
    public void gulp(@ShellOption(defaultValue = "") String arguments) {
        executor.execute(() -> commandService.exec(navigator.getCurrentPath().resolve("node").toFile(),
                getGulpCommand(arguments), line -> {
                    printService.print(TextFormatter.formatLogText(line));
                    return Optional.ofNullable(new Line(line));
                }));
    }

    public ShellCommand getGulpCommand(String args) {
        List<String> gulpArgs = new ArrayList<>();
        gulpArgs.add(navigator.getCurrentPath().resolve(GulpCommandHelper.GULP_BIN_LOCATION)
                .resolve(GulpCommandHelper.GULP_COMMAND).toString());
        gulpArgs.addAll(Arrays.asList(args.split(" ")));
        gulpArgs.add(GulpCommandHelper.GULP_CWD_OPTION);
        gulpArgs.add(navigator.getCurrentPath().toString());
        return new ShellCommand(isWindowsOS() ? GulpCommandHelper.NODE_COMMAND : "./" + GulpCommandHelper.NODE_COMMAND,
                gulpArgs.toArray(new String[gulpArgs.size()]));
    }

    public Availability isLocalGulpInstalled() {
        return navigator.getCurrentPath().resolve(GulpCommandHelper.GULP_BIN_LOCATION).toFile().exists()
                ? Availability.available()
                : Availability.unavailable("Yarn local installation was not found in this directory");
    }

    /* BOWER */

    @ShellMethod("Execute local installed bower")
    @ShellMethodAvailability("isLocalBowerInstalled")
    public void bower(@ShellOption(defaultValue = "") String arguments) {
        executor.execute(() -> commandService.exec(
                navigator.getCurrentPath().resolve(BowerCommandHelper.BOWER_BIN_LOCATION).toFile(),
                getBowerCommand(arguments), line -> {
                    printService.print(TextFormatter.formatLogText(line));
                    return Optional.ofNullable(new Line(line));
                }));
    }

    public ShellCommand getBowerCommand(String args) {
        List<String> bowerArgs = new ArrayList<>();
        bowerArgs.addAll(Arrays.asList(args.split(" ")));
        bowerArgs.add(BowerCommandHelper.BOWER_CWD_OPTION + "=" + navigator.getCurrentPath());
        return new ShellCommand(
                isWindowsOS() ? BowerCommandHelper.BOWER_COMMAND : "./" + BowerCommandHelper.BOWER_COMMAND,
                bowerArgs.toArray(new String[bowerArgs.size()]));
    }

    public Availability isLocalBowerInstalled() {
        return navigator.getCurrentPath().resolve(BowerCommandHelper.BOWER_BIN_LOCATION).toFile().exists()
                ? Availability.available()
                : Availability.unavailable("Bower local installation was not found in this directory");
    }

    @ShellMethod("Run a Spring-Boot App")
    public void run(@ShellOption(defaultValue = "DEV") Profile profile) {
        executor.execute(() -> commandService.exec(
                new ShellCommand(getMavenWrapperCommand(), "spring-boot:run", "-P", profile.getProfileName()), line -> {
                    printService.print(TextFormatter.formatLogText(line));
                    return Optional.ofNullable(new Line(line));
                }));
    }

    /**
     * Command Shell that show the current project configuration applied to the
     * current context.
     *
     * @return Configuration
     * @throws JsonProcessingException
     */
    @ShellMethod("Show the current project configuration applied to the current context")
    public String showConfiguration() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        return objectMapper.writeValueAsString(shellContext.getConfiguration().orElse(new ProjectConfiguration()));
    }

    @ShellMethod("Show the current project configuration applied to the current context")
    public AttributedCharSequence testingCommand() {
        return new AttributedString("testing command");
    }

    public static String getMavenWrapperCommand() {
        if (CommandServiceImpl.isWindowsOS()) {
            return MVN_WRAPPER_COMMAND;
        }
        return "./" + MVN_WRAPPER_COMMAND;
    }
}
