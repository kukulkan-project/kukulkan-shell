package mx.infotec.dads.kukulkan.shell.kukulkanshell;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import mx.infotec.dads.kukulkan.engine.config.PrintProviderConfiguration;
import mx.infotec.dads.kukulkan.engine.service.FileUtil;
import mx.infotec.dads.kukulkan.kukulkan.shell.test.config.ProjectContextConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ProjectContextConfiguration.class, FileUtil.class })
@Import(value = { PrintProviderConfiguration.class })
public class KukulkanShellApplicationTests {

    @Autowired
    FileUtil fileUtil;

    @Test
    public void contextLoads() {
        try {
            File tmp = File.createTempFile("favicon", ".ico");

            FileUtil.copyFromJar("templates/archetypes/angularjs/src/main/webapp/favicon.ico", tmp.toPath());

        } catch (IOException ex) {

        }
    }

}
