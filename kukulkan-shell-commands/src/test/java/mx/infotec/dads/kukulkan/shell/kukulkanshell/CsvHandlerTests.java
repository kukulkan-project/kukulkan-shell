package mx.infotec.dads.kukulkan.shell.kukulkanshell;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mx.infotec.dads.kukulkan.kukulkan.shell.test.config.ProjectContextConfiguration;
import mx.infotec.dads.kukulkan.shell.commands.files.CsvUtil;
import mx.infotec.dads.kukulkan.shell.commands.files.FileNamePair;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectContextConfiguration.class)
public class CsvHandlerTests {

    @Test
    public void whenLoadingUsersFromCsvFile_thenLoaded() {
        String path = "csv/file.csv";
        List<FileNamePair> users = CsvUtil.loadCsvAsList(FileNamePair.class, path);
        for (FileNamePair fileNamePair : users) {
        }
    }
}
