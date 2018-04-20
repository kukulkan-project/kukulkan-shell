package mx.infotec.dads.kukulkan.shell.kukulkanshell;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.shell.commands.GraphsArgs;
import mx.infotec.dads.kukulkan.shell.generator.GraphsContext;
import mx.infotec.dads.kukulkan.shell.generator.GraphsGenerator;
import mx.infotec.dads.kukulkan.shell.util.MapperG;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@Import(value = { KukulkanEngineApp.class })
@ComponentScan(basePackages = { "mx.infotec.dads.kukulkan.shell", "mx.infotec.dads.kukulkan.engine",
        "mx.infotec.dads.kukulkan.metamodel" }, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CommandLineRunner.class))
public class KukulkanShellApplicationTests {
    @Autowired
    private GraphsGenerator generator;

    @Test
    public void generate() throws IOException {
        GraphsArgs params = new GraphsArgs();

        GraphsContext graphsContext = MapperG.toContext(params);
        Path tmpFolder = Files.createTempDirectory("kukulkan-output-test");
        tmpFolder.toFile().deleteOnExit();
        graphsContext.setOutputDir(tmpFolder);
        GeneratorContext genCtx = new GeneratorContext();
        genCtx.put(GraphsContext.class, graphsContext);
        generator.process(genCtx);
    }
}
