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

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import mx.infotec.dads.kukulkan.engine.translator.dsl.FileSource;
import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.Database;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.translator.Source;
import mx.infotec.dads.kukulkan.metamodel.translator.TranslatorService;
import mx.infotec.dads.kukulkan.shell.domain.KukulkanShellContext;
import mx.infotec.dads.kukulkan.shell.util.GeneratorException;

/**
 * Command Helper, It is used for encapsulate common operation performed in the
 * KukulkanCommand Class.
 *
 * @author Daniel Cortes Pichardo
 */
public class CommandHelper {

    public static final String LAYERS_OPTION_DEFAULT_VALUE = "@all";

    /**
     * Instantiates a new command helper.
     */
    private CommandHelper() {

    }

    /**
     * Create a Generator Context from a File. This file is the source code that
     * describe the domain model represented in the the kukulkan language.
     *
     * @param projectConfiguration
     *            the project configuration
     * @param file
     *            the file
     * @return the generator context
     */
    public static GeneratorContext createGeneratorContext(ProjectConfiguration projectConfiguration, File file,
            TranslatorService translatorService) {
        GeneratorContext genCtx = new GeneratorContext();
        DomainModel domainModel = translatorService.translate(projectConfiguration, new FileSource(file));
        genCtx.put(ProjectConfiguration.class, projectConfiguration);
        genCtx.put(DomainModel.class, domainModel);

        return genCtx;
    }

    public static GeneratorContext createGeneratorContext(Optional<ProjectConfiguration> projectConfiguration,
            Source dataBaseSource, TranslatorService translatorService) {
        GeneratorContext genCtx = new GeneratorContext();
        if (!projectConfiguration.isPresent()) {
            throw new GeneratorException("projectConfiguration is not present");
        }
        projectConfiguration.ifPresent(config -> {
            DomainModel domainModel = translatorService.translate(projectConfiguration.get(), dataBaseSource);
            genCtx.put(ProjectConfiguration.class, config);
            genCtx.put(DomainModel.class, domainModel);
        });
        return genCtx;
    }

    /**
     * Configure the ProjectConfiguration Object with the proper appName, groupId
     * and currentPath.
     *
     * @param projectConfiguration
     *            the project configuration
     * @param appName
     *            the app name
     * @param packaging
     *            the packaging
     * @param currentPath
     *            the current path
     */
    public static void configProjectConfiguration(KukulkanShellContext shellContext, String appName, String packaging,
            Path currentPath, DatabaseType databaseType) {
        shellContext.getConfiguration().ifPresent(config -> {
            config.setId(appName);
            config.setPackaging(packaging);
            config.setOutputDir(currentPath.resolve(appName));
            config.setTargetDatabase(new Database(databaseType));
        });
    }

    public static void configLayers(KukulkanShellContext shellContext, FrontEndArchetype frontEndArchetype) {
        Objects.requireNonNull(shellContext.getConfiguration(), "The configuration Object can not be null");
        shellContext.getConfiguration().ifPresent(config -> {
            config.addLayers("spring-rest", "spring-service", "spring-repository", "domain-core");

            if (FrontEndArchetype.GOB_MX_ANGULARJS.equals(frontEndArchetype)) {
                config.addLayers("gob-mx-angular-js", "gob-mx-angular-js-archetype-layer");
            } else if (FrontEndArchetype.ANGULARJS.equals(frontEndArchetype)) {
                config.addLayers("angular-js", "angular-js-archetype-layer");
            }

            DatabaseType dbType = config.getTargetDatabase().getDatabaseType();
            if (dbType.equals(DatabaseType.SQL_MYSQL) || dbType.equals(DatabaseType.SQL_ORACLE)) {
                config.addLayer("liquibase");
            }
        });
    }

    public static GeneratorContext createGeneratorContext(KukulkanShellContext shellContext, String appName,
            String packaging, Path currentPath, DatabaseType databaseType, FrontEndArchetype frontEndArchetype) {
        configProjectConfiguration(shellContext, appName, packaging, currentPath, databaseType);
        configLayers(shellContext, frontEndArchetype);
        if (shellContext.getConfiguration().isPresent()) {
            return new GeneratorContext(ProjectConfiguration.class, shellContext.getConfiguration().get());
        } else {
            throw new GeneratorException("The project configuration is not present");
        }
    }

    public static void computeExcludedLayers(KukulkanShellContext shellContext, String fileName) {
        final List<String> toExcludeList = getExcludedList(fileName);
        shellContext.getConfiguration().ifPresent(config -> {
            List<String> currentLayers = config.getLayers();
            List<String> toProcessList = new ArrayList<>();
            for (String layer : currentLayers) {
                if (!toExcludeList.contains(layer)) {
                    toProcessList.add(layer);
                }
            }
            config.setLayersToProcess(toProcessList);
        });
    }

    private static List<String> getExcludedList(String fileName) {
        if (!LAYERS_OPTION_DEFAULT_VALUE.equals(fileName)) {
            return Arrays.asList(fileName.split(","));
        } else {
            return new ArrayList<>();
        }
    }
}
