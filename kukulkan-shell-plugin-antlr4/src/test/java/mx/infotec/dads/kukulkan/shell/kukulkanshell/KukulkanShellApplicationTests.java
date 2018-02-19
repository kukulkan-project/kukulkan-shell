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
import mx.infotec.dads.kukulkan.shell.commands.Antlr4Args;
import mx.infotec.dads.kukulkan.shell.generator.Antlr4Context;
import mx.infotec.dads.kukulkan.shell.generator.Antlr4Generator;
import mx.infotec.dads.kukulkan.shell.util.Mapper;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@Import(value = { KukulkanEngineApp.class })
@ComponentScan(basePackages = { "mx.infotec.dads.kukulkan.shell", "mx.infotec.dads.kukulkan.engine",
        "mx.infotec.dads.kukulkan.metamodel" }, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CommandLineRunner.class))
public class KukulkanShellApplicationTests {
    @Autowired
    private Antlr4Generator generator;

    @Test
    public void generate() throws IOException {
        Antlr4Args params = new Antlr4Args();
        params.setGrammarExtension("3k");
        params.setGrammarName("kukulkan");
        params.setId("kukulkan-grammar-testing");
        params.setPackaging("mx.dads.infotec");
        Antlr4Context antlr4Context = Mapper.toContext(params);
        Path tmpFolder = Files.createTempDirectory("kukulkan-output-test");
        tmpFolder.toFile().deleteOnExit();
        antlr4Context.setOutputDir(tmpFolder);
        GeneratorContext genCtx = new GeneratorContext();
        genCtx.put(Antlr4Context.class, antlr4Context);
        generator.process(genCtx);
    }
}
