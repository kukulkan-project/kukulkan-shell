package mx.infotec.dads.kukulkan.shell.kukulkanshell;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mx.infotec.dads.kukulkan.shell.config.ProjectContextConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectContextConfiguration.class)
public class KukulkanShellApplicationTests {

    private static final String RESOURCE = "/templates/error.html";
    
    @Test
    public void readFile() throws IOException {
        try (InputStream is = ProjectContextConfiguration.class.getResourceAsStream(RESOURCE)) {
            assert is != null : "Can't find " + RESOURCE;
        }
    }

}
