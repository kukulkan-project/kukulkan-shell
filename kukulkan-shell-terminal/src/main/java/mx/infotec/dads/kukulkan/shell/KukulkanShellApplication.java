package mx.infotec.dads.kukulkan.shell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import mx.infotec.dads.kukulkan.metamodel.util.KukulkanConfigurationProperties;

/**
 * Init of the Shell
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@SpringBootApplication(scanBasePackages = { "mx.infotec.dads.kukulkan", "mx.infotec.dads.kukulkan.engine",
        "mx.infotec.dads.kukulkan.shell.config" })
@EnableConfigurationProperties({ KukulkanConfigurationProperties.class })
public class KukulkanShellApplication {
    public static void main(String[] args) {
        SpringApplication.run(KukulkanShellApplication.class, args);
    }
}
