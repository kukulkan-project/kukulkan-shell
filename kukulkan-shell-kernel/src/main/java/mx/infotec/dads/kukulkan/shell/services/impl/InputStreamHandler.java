/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2016 Daniel Cortes Pichardo
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
