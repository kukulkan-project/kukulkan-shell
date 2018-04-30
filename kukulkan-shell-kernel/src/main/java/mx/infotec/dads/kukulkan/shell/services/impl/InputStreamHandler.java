package mx.infotec.dads.kukulkan.shell.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import mx.infotec.dads.kukulkan.shell.services.PrintService;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util class to handle command output stream.
 * 
 * @author erik.valdivieso
 */
public class InputStreamHandler extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(InputStreamHandler.class);

    private final InputStream inputStream;
    private final PrintService printService;

    private boolean errorStream = false;

    /**
     * Constructor.
     *
     * @param inputStream The command input stream
     * @param printService The printer service
     */
    InputStreamHandler(InputStream inputStream, PrintService printService) {
        this.inputStream = inputStream;
        this.printService = printService;
    }

    /**
     * Constructor.
     *
     * @param inputStream The command input stream
     * @param printService The printer service
     * @param errorStream If is a error input stream.
     */
    InputStreamHandler(InputStream inputStream, PrintService printService, boolean errorStream) {
        this.inputStream = inputStream;
        this.printService = printService;
        this.errorStream = errorStream;
    }

    /**
     * Stream the data.
     */
    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (errorStream) {
                    printService.print(new AttributedString(line, AttributedStyle.BOLD_OFF.foreground(AttributedStyle.RED)));
                } else {
                    printService.print(new AttributedString(line));
                }
            }
        } catch (IOException ioe) {
            LOGGER.error("Error at read stream", ioe);
        }
    }
}
