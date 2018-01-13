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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarMapping;
import mx.infotec.dads.kukulkan.engine.translator.dsl.KukulkanVisitor;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModelGroup;
import mx.infotec.dads.kukulkan.metamodel.foundation.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.JavaDomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.shell.commands.util.ProjectUtil;

/**
 * Command Helper, It is used for encapsulate common operation performed in the
 * KukulkanCommand Class.
 *
 * @author Daniel Cortes Pichardo
 */
public class CommandHelper {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandHelper.class);

    /**
     * Instantiates a new command helper.
     */
    private CommandHelper() {

    }

    /**
     * Read the project configuration from a file and set the properties into
     * the main ProjectConfiguration instance.
     *
     * @param projectConfiguration
     *            the project configuration
     * @param currentPath
     *            the current path
     */
    public static void readProjectConfiguration(ProjectConfiguration projectConfiguration, Path currentPath) {
        ProjectConfiguration pConf = ProjectUtil.readKukulkanFile(currentPath);
        projectConfiguration.setId(pConf.getId());
        projectConfiguration.setPackaging(pConf.getPackaging());
        projectConfiguration.setOutputDir(currentPath.getParent());
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
    public static GeneratorContext createGeneratorContext(ProjectConfiguration projectConfiguration, File file) {
        DomainModel domainModel = new JavaDomainModel();
        KukulkanVisitor semanticAnalyzer = new KukulkanVisitor();
        List<DomainModelGroup> dmgList = GrammarMapping.createSingleDataModelGroupList(semanticAnalyzer, file);
        domainModel.setDomainModelGroup(dmgList);
        LOGGER.info("Processing File...");
        GeneratorContext genCtx = new GeneratorContext();
        genCtx.put(ProjectConfiguration.class, projectConfiguration);
        genCtx.put(DomainModel.class, domainModel);
        return genCtx;
    }

    /**
     * Configure the ProjectConfiguration Object with the proper appName,
     * groupId and currentPath.
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
    public static void configProjectConfiguration(ProjectConfiguration projectConfiguration, String appName,
            String packaging, Path currentPath) {
        projectConfiguration.setId(appName);
        projectConfiguration.setPackaging(packaging);
        projectConfiguration.setOutputDir(currentPath);
    }

}
