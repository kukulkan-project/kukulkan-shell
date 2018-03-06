package mx.infotec.dads.kukulkan.shell.kukulkanshell;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import mx.infotec.dads.kukulkan.kukulkan.shell.test.config.ProjectContextConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectContextConfiguration.class)
public class KukulkanShellApplicationTests {

    private final static String RES = "classpath:templates/error.html";
    private final static Logger LOGGER = LoggerFactory.getLogger(KukulkanShellApplicationTests.class);
    
    @Test
    public void readFile() throws IOException {
        URL url = ResourceUtils.getURL(RES);

        assert url != null : "Can't find " + RES;
        
        if (LOGGER.isDebugEnabled()) {
            try (InputStream is = url.openStream(); BufferedInputStream bis = new BufferedInputStream(is)) {
                StringBuilder sb = new StringBuilder();
                int c;
                
                while((c = bis.read()) != -1) {
                    sb.append((char) c);
                }
                
                LOGGER.debug("Content:\n{}", sb.toString());
            } catch (IOException ex) {
                LOGGER.error("Can't read file", ex);
            }
        }        
    }

}
