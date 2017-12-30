package mx.infotec.dads.kukulkan.shell.kukulkanshell;

import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mx.infotec.dads.kukulkan.metamodel.util.FileUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KukulkanShellApplicationTests {

    @Test
    public void contextLoads() {
    }

    public static void main(String[] args) {
        FileUtil.copyFromJar("templates/archetypes/angularjs-spring-mongo/src/main/webapp/favicon.ico",
                Paths.get("/home/daniel/bot/favicon.ico"));
        System.out.println("located");
    }

}
