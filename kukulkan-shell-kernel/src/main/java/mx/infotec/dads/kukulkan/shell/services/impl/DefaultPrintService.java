package mx.infotec.dads.kukulkan.shell.services.impl;

import java.util.regex.Pattern;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.shell.services.PrintService;
import mx.infotec.dads.kukulkan.shell.util.TextFormatter;

/**
 * DefaultPrintService
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Service
public class DefaultPrintService implements PrintService {

    /**
     * The terminal.
     */
    @Autowired
    @Lazy
    private Terminal terminal;

    /**
     * Printf.
     *
     * @param text
     *            the text
     * @see TextFormatter
     */
    @Override
    public void print(AttributedString text) {
        terminal.writer().append(text.toAnsi(terminal)).append("\n");
        terminal.flush();
    }

    /**
     * Printf.
     *
     * @param key
     *            the key
     * @param message
     *            the message
     * @see TextFormatter
     */
    @Override
    public void print(String key, String message) {
        terminal.writer().append(String.format("[%-15s] -%-30s%n\t", key, message)).append("\n");
        terminal.flush();
    }

    @Override
    public void info(String message) {
        AttributedString text = new AttributedString(message,
                AttributedStyle.BOLD.italic().foreground(AttributedStyle.BRIGHT));
        print(text);
    }

    @Override
    public void warning(String message) {
        AttributedString text = new AttributedString(message,
                AttributedStyle.BOLD.italic().foreground(AttributedStyle.YELLOW));
        print(text);
    }

    @Override
    public void error(String message) {
        AttributedString text = new AttributedString(message,
                AttributedStyle.BOLD.italic().foreground(AttributedStyle.RED));
        print(text);
    }

    @Override
    public void info(String message, Object... objects) {
        String temp = message;
        for (Object object : objects) {
            temp = temp.replaceFirst(Pattern.quote("{}"), object.toString());
        }
        AttributedString text = new AttributedString(temp,
                AttributedStyle.BOLD.italic().foreground(AttributedStyle.BLUE));
        print(text);
    }

    @Override
    public void warning(String message, Object... objects) {
        AttributedString text = new AttributedString(message,
                AttributedStyle.BOLD.italic().foreground(AttributedStyle.YELLOW));
        print(text);
    }

    @Override
    public void error(String message, Throwable throwable) {
        AttributedString text = new AttributedString(message,
                AttributedStyle.BOLD.italic().foreground(AttributedStyle.RED));
        print(text);
    }

    @Override
    public void error(String message, Object[] objects) {
        AttributedString text = new AttributedString(message,
                AttributedStyle.BOLD.italic().foreground(AttributedStyle.RED));
        print(text);
    }
}
