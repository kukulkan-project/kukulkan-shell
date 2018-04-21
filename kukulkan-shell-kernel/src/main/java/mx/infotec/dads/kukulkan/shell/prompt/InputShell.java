package mx.infotec.dads.kukulkan.shell.prompt;

import java.util.Arrays;
import java.util.List;

import org.jline.reader.LineReader;
import org.springframework.context.ApplicationContextException;

public class InputShell {
    private final LineReader lineReader;

    public InputShell(LineReader lineReader) {
        this.lineReader = lineReader;
    }

    public String readFromOptionList(String prompt, String defaultValue, String... options) {
        List<String> optionsAsList = Arrays.asList(options);
        if (!optionsAsList.contains(defaultValue)) {
            throw new ApplicationContextException("defaultValue must match a element in option List");
        }
        String answer;
        do {
            answer = lineReader.readLine(String.format("%s %s: ", prompt, optionsAsList));
        } while (!optionsAsList.contains(answer) && !"".equals(answer));
        return "".equals(answer) && !optionsAsList.contains("") ? defaultValue : answer;
    }

    public String readOption(String prompt, String defaultValue, boolean echo) {
        String answer = lineReader.readLine(prompt + ": ", '*');
        return "".equals(answer) ? defaultValue : answer;
    }
}
