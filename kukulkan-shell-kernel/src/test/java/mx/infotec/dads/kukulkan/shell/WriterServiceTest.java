package mx.infotec.dads.kukulkan.shell;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import freemarker.template.Configuration;
import mx.infotec.dads.kukulkan.engine.config.PrintProviderConfiguration;
import mx.infotec.dads.kukulkan.engine.service.FileUtil;
import mx.infotec.dads.kukulkan.engine.service.WriterServiceImpl;
import mx.infotec.dads.kukulkan.engine.templating.service.TemplateServiceImpl;
import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.services.WriterHelper;
import mx.infotec.dads.kukulkan.shell.services.impl.WriterHelperImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Navigator.class, WriterHelperImpl.class, Configuration.class, TemplateServiceImpl.class,
        WriterServiceImpl.class, FileUtil.class })
@Import(PrintProviderConfiguration.class)
public class WriterServiceTest {

    @Autowired
    WriterHelper writer;

    @Autowired
    Navigator navigator;

    Path workingDir;

    Map<Object, Object> model;

    @Before
    public void prepare() {
        try {
            workingDir = Files.createTempDirectory("kukulkanTest");
            navigator.setCurrentPath(workingDir);
            model = getDefaultModel();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testCopy() {
        writer.copy("testFile.txt", "tested.txt");
        File file = workingDir.resolve("tested.txt").toFile();
        assert (file.exists());
    }

    @Test
    public void testCopyWithModel() {
        writer.copy("testFile.txt", "${name}/${packaging?replace(\".\", \"/\")}/tested.txt", model);
        File file = workingDir.resolve("testProject/mx/infotec/dads/tested.txt").toFile();
        assert (file.exists());
    }

    private Map<Object, Object> getDefaultModel() {
        Map<Object, Object> model = new HashMap<Object, Object>();
        model.put("name", "testProject");
        model.put("version", "0.0.1");
        model.put("author", "Kukulkán");
        model.put("packaging", "mx.infotec.dads");
        return model;
    }

}
