package mx.infotec.dads.kukulkan.shell.kukulkanshell;

import java.io.IOException;
import java.io.InputStream;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mx.infotec.dads.kukulkan.shell.config.ProjectContextConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectContextConfiguration.class)
public class KukulkanShellApplicationTests {

    private static final String RESOURCE = "/templates/error.html";
    @Autowired
    
    private Terminal terminal;

    @Test
    public void readFile() throws IOException {
        AttributedString fs = new AttributedString("[", AttributedStyle.BOLD.foreground(AttributedStyle.GREEN));
        System.out.println(fs.toAnsi(terminal));
        try (InputStream is = ProjectContextConfiguration.class.getResourceAsStream(RESOURCE)) {
            assert is != null : "Can't find " + RESOURCE;
        }
    }

}
