package mx.infotec.dads.kukulkan.shell.kukulkanshell;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KukulkanShellApplicationTests {

    @Test
    public void readFile() throws IOException {
        File file = ResourceUtils.getFile("classpath:templates/error.html");

        // File is found
        System.out.println("File Found : " + file.exists());

        // Read File Content
        String content = new String(Files.readAllBytes(file.toPath()));
        System.out.println(content);
    }
    
 

}
