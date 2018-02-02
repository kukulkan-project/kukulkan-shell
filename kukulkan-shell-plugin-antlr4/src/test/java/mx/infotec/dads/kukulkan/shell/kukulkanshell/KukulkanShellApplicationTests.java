package mx.infotec.dads.kukulkan.shell.kukulkanshell;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.shell.commands.Antlr4Args;
import mx.infotec.dads.kukulkan.shell.generator.Antlr4Context;
import mx.infotec.dads.kukulkan.shell.generator.Antlr4Generator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KukulkanEngineApp.class)
public class KukulkanShellApplicationTests {

    @Autowired
    private Antlr4Generator generator;
    
    @Test
    public void generate() throws IOException {
        Antlr4Context pConf = new Antlr4Context();
        pConf.setId("antlr4");
        pConf.setPackaging("mx.infotec.dads.antlr4");
        pConf.setOutputDir(Paths.get("/home/daniel/refactoring"));
        GeneratorContext genCtx = new GeneratorContext();
        genCtx.put(Antlr4Args.class, pConf);
        generator.process(genCtx);
    }
}
