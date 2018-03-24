package mx.infotec.dads.kukulkan.shell.kukulkanshell;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mx.infotec.dads.kukulkan.metamodel.util.FileUtil;
import mx.infotec.dads.kukulkan.kukulkan.shell.test.config.ProjectContextConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectContextConfiguration.class)
public class KukulkanShellApplicationTests {

    @Test
    public void contextLoads() {
        try {
            File tmp = File.createTempFile("favicon", ".ico");

            FileUtil.copyFromJar("templates/archetypes/angularjs/src/main/webapp/favicon.ico", tmp.toPath());

        } catch (IOException ex) {

        }
    }

}
