/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2018 Roberto Villarejo Martínez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package mx.infotec.dads.kukulkan.shell.kukulkanshell;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import mx.infotec.dads.kukulkan.engine.config.InflectorConf;
import mx.infotec.dads.kukulkan.engine.config.PrintProviderConfiguration;
import mx.infotec.dads.kukulkan.engine.config.XtextDSLConfiguration;
import mx.infotec.dads.kukulkan.engine.service.DefaultModelValidator;
import mx.infotec.dads.kukulkan.engine.service.FileUtil;
import mx.infotec.dads.kukulkan.engine.service.InflectorServiceImpl;
import mx.infotec.dads.kukulkan.engine.service.WriterService;
import mx.infotec.dads.kukulkan.engine.service.WriterServiceImpl;
import mx.infotec.dads.kukulkan.engine.service.pk.ComposedPrimaryKeyNameStrategy;
import mx.infotec.dads.kukulkan.engine.service.pk.DefaultPrimaryKeyNameStrategy;
import mx.infotec.dads.kukulkan.engine.templating.service.TemplateServiceImpl;
import mx.infotec.dads.kukulkan.engine.translator.dsl.FileSource;
import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarTranslatorService;
import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.Database;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.translator.Source;
import mx.infotec.dads.kukulkan.metamodel.translator.TranslatorService;
import mx.infotec.dads.kukulkan.metamodel.util.KukulkanConfigurationProperties;
import mx.infotec.dads.kukulkan.shell.commands.kukulkan.DomainModelDslWriter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TemplateServiceImpl.class, Configuration.class, FileUtil.class,
        GrammarTranslatorService.class, DefaultModelValidator.class, InflectorServiceImpl.class,
        DefaultPrimaryKeyNameStrategy.class, ComposedPrimaryKeyNameStrategy.class, WriterServiceImpl.class,
        DomainModelDslWriter.class })
@EnableConfigurationProperties({ KukulkanConfigurationProperties.class })
@Import({ PrintProviderConfiguration.class, InflectorConf.class, XtextDSLConfiguration.class })
public class DomainModelDslRenderer {

    @Autowired
    private WriterService writerService;

    @Autowired
    private DomainModelDslWriter dslWriter;

    @Autowired
    @Qualifier("grammarTranslatorService")
    private TranslatorService translatorService;
    
    @Autowired
    private Configuration conf;
    
    @Before
    public void config() throws IOException {
        conf.setTemplateLoader(new FileTemplateLoader(Paths.get("src/main/resources/templates").toFile()));
    }

    @Test
    public void renderDomainModelToKukulkanDsl() throws IOException {
        ProjectConfiguration pConf = createProjectConfiguration();
        GeneratorContext genCtx = new GeneratorContext();
        Source source = new FileSource("src/test/resources/Model.3k");
        genCtx.put(ProjectConfiguration.class, pConf);
        genCtx.put(DomainModel.class, translatorService.translate(pConf, source));

        Optional<DomainModel> maybeDomainModel = genCtx.get(DomainModel.class);
        if (!maybeDomainModel.isPresent()) {
            fail("No domain model obtained from Database");
        }
        DomainModel domainModel = maybeDomainModel.get();
        // Fill template with domain model
        String dslModel = dslWriter.toDslContent(domainModel);
        // String dslModel =
        // templateService.fillTemplate("src/main/resources/templates/kukulkanDsl.ftl",
        // domainModel);
        // Print kukulkan dsl result
        System.out.println(dslModel);
        Path file3kPath = Files.createTempFile("testKukulkan", ".3k");
        writerService.save(file3kPath, dslModel);
        // Validate parsing the generated model
        translatorService.translate(pConf, new FileSource(file3kPath.toFile()));
    }

    public static ProjectConfiguration createProjectConfiguration() {
        ProjectConfiguration pConf = new ProjectConfiguration();
        pConf.setTargetDatabase(new Database(DatabaseType.SQL_ORACLE));
        return pConf;
    }

}
