package mx.infotec.dads.kukulkan.shell.kukulkanshell;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class NativeCommandTests {

    @Test
    public void contextLoads() {
        String s = null;
        List<CharSequence> lines = new ArrayList<>();
        try {
            Process p = Runtime.getRuntime().exec("ls");
            p.waitFor();
            lines = readBufferProcess(p);
        } catch (Exception e) {
            lines.add(e.getMessage());
        }
    }

    public static List<CharSequence> readBufferProcess(Process p) throws IOException {
        List<CharSequence> lines = new ArrayList<>();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String s;
        while ((s = stdInput.readLine()) != null) {
            lines.add(s);
        }
        while ((s = stdError.readLine()) != null) {
            lines.add(s);
        }
        return lines;
    }
}
