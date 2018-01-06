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
 * KukulkanCommand Class
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class CommandHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandHelper.class);

    private CommandHelper() {

    }

    /**
     * Read the project configuration from a file and set the properties into
     * the main ProjectConfiguration instance.
     * 
     * @param projectConfiguration
     * @param currentPath
     */
    public static void readProjectConfiguration(ProjectConfiguration projectConfiguration, Path currentPath) {
        ProjectConfiguration pConf = ProjectUtil.readKukulkanFile(currentPath);
        projectConfiguration.setId(pConf.getId());
        projectConfiguration.setGroupId(pConf.getGroupId());
        projectConfiguration.setOutputDir(currentPath);
    }

    /**
     * Create a Generator Context from a File. This file is the source code that
     * describe the domain model represented in the the kukulkan language.
     * 
     * @param projectConfiguration
     * @param file
     * @return
     */
    public static GeneratorContext createGeneratorContext(ProjectConfiguration projectConfiguration, File file) {
        DomainModel dataModel = new JavaDomainModel();
        KukulkanVisitor semanticAnalyzer = new KukulkanVisitor();
        List<DomainModelGroup> dmgList = GrammarMapping.createSingleDataModelGroupList(semanticAnalyzer, file);
        dataModel.setDomainModelGroup(dmgList);
        LOGGER.info("Processing File...");
        GeneratorContext genCtx = new GeneratorContext(dataModel, projectConfiguration);
        return genCtx;
    }

    /**
     * Configure the ProjectConfiguration Object with the proper appName,
     * groupId and currentPath
     * 
     * @param projectConfiguration
     * @param appName
     * @param package
     * @param currentPath
     */
    public static void configProjectConfiguration(ProjectConfiguration projectConfiguration, String appName,
            String packaging, Path currentPath) {
        projectConfiguration.setId(appName);
        projectConfiguration.setPackaging(packaging);
        projectConfiguration.setOutputDir(currentPath);
    }

}
