package mx.infotec.dads.kukulkan.shell.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.util.PKGenerationStrategy;
import mx.infotec.dads.kukulkan.shell.domain.ProjectContext;

@Configuration
public class ProjectContextConfiguration {
    @Bean
    public ProjectContext configProjectContext() {
        ProjectContext context = new ProjectContext();
        return createDefaultProjectConfiguration(context);
    }

    private ProjectContext createDefaultProjectConfiguration(ProjectContext context) {
        ProjectConfiguration pConf = new ProjectConfiguration();
        pConf.setId("paz");
        pConf.setGroupId("mx.infotec.dads.mongo");
        pConf.setVersion("1.0.0");
        pConf.setPackaging("mx.infotec.dads.mongo");
        pConf.setYear("2017");
        pConf.setAuthor("KUKULKAN");
        pConf.setWebLayerName("web.rest");
        pConf.setServiceLayerName("service");
        pConf.setDaoLayerName("repository");
        pConf.setDtoLayerName("dto");
        pConf.setExceptionLayerName("exception");
        pConf.setDomainLayerName("domain");
        pConf.setMongoDb(true);
        pConf.setGlobalGenerationType(PKGenerationStrategy.SEQUENCE);
        context.setProject(pConf);
        return context;
    }
}