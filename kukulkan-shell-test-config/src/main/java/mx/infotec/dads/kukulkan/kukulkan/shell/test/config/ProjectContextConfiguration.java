package mx.infotec.dads.kukulkan.kukulkan.shell.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.infotec.dads.kukulkan.metamodel.foundation.Database;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.util.PKGenerationStrategy;

/**
 * The Class ProjectContextConfiguration.
 * 
 * TODO: Build global test configuration class
 */
@Configuration
public class ProjectContextConfiguration {

    /**
     * Config project context.
     *
     * @return the project configuration
     */
    @Bean
    public ProjectConfiguration configProjectContext() {
        ProjectConfiguration pConf = new ProjectConfiguration();
        pConf.setId("default");
        pConf.setVersion("1.0.0");
        pConf.setPackaging("mx.infotec.dads.default");
        pConf.setYear("2018");
        pConf.setAuthor("KUKULKAN");
        pConf.setTargetDatabase(new Database(DatabaseType.SQL_MYSQL, PKGenerationStrategy.AUTO));
        return pConf;
    }
}
