package mx.infotec.dads.kukulkan.shell;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Init of the Shell
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@ComponentScan(basePackages = { "mx.infotec.dads.kukulkan", "mx.infotec.dads.kukulkan.engine" })
// @EnableAutoConfiguration(exclude = { MetricFilterAutoConfiguration.class,
// MetricRepositoryAutoConfiguration.class })
// @EnableConfigurationProperties({ KukulkanConfigurationProperties.class })
public class KukulkanShellApplication {
    public static void main(String[] args) {
        SpringApplication.run(KukulkanShellApplication.class, args);
    }
}
