package mx.infotec.dads.kukulkan.shell.kukulkanshell;

import javax.annotation.PostConstruct;

//
//import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
//import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import mx.infotec.dads.kukulkan.metamodel.util.KukulkanConfigurationProperties;

@ComponentScan
// @EnableAutoConfiguration(exclude = { MetricFilterAutoConfiguration.class,
// MetricRepositoryAutoConfiguration.class })
@EnableAutoConfiguration
@EnableConfigurationProperties({ KukulkanConfigurationProperties.class })
public class KukulkanEngineApp {

    /**
     * Initializes kukulkancraftsman.
     * <p>
     * Spring profiles can be configured with a program arguments
     * --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on
     * <a href=
     * "http://jhipster.github.io/profiles/">http://jhipster.github.io/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {

    }
}
