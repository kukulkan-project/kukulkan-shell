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
package mx.infotec.dads.kukulkan.shell.commands.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import mx.infotec.dads.kukulkan.shell.component.Navigator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import mx.infotec.dads.kukulkan.shell.services.PrintService;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellOption;

/**
 * Util Commands.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class LinuxCommands {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinuxCommands.class);

    /**
     * The navigator service.
     */
    @Autowired
    private Navigator nav;

    @Autowired
    private PrintService printService;

    /**
     * Ping.
     *
     * @param host
     *            the host
     */
    @ShellMethod("make a ping to a host")
    public void ping(String host) {
        ProcessBuilder ps = new ProcessBuilder("ping", "-c", "3", host);

        execCommnad(ps);
    }

    /**
     * Make df of dir; if dir is not present, make df in all partitions.
     *
     * @param dir
     */
    @ShellMethod("shows the used space of all partitions; if the directory parameter is specified, it shows the information of its partition")
    public void df(@ShellOption(defaultValue = "") String dir) {
        ProcessBuilder ps;

        if (dir == null || dir.isEmpty()) {
            ps = new ProcessBuilder("df", "-h");
        } else {
            ps = new ProcessBuilder("df", "-h", dir);
        }

        execCommnad(ps);
    }

    @ShellMethod("shows the used space of all files and directories; if the directory parameter is specified, it shows only this directory")
    public void du(@ShellOption(defaultValue = "") String dir) {
        ProcessBuilder ps;

        if (dir == null || dir.isEmpty()) {
            File currentDir = nav.getCurrentPath().toFile();
            ArrayList<String> list = new ArrayList<>();

            list.add("du");
            list.add("-shc");

            for (File f : currentDir.listFiles()) {
                list.add(f.getName());
            }

            ps = new ProcessBuilder(list);
        } else {
            ps = new ProcessBuilder("du", "-sh", dir);
        }

        execCommnad(ps);
    }

    private void execCommnad(ProcessBuilder ps) {
        ps.redirectErrorStream(true);

        try {
            Process pr = ps.start();

            try (BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    printService.print(new AttributedString(line));
                }

                pr.waitFor();
                LOGGER.debug("End ping command");
            }

        } catch (IOException | InterruptedException ex) {
            LOGGER.error("Error at excecute " + ps.toString() + " commad", ex);
        }
    }

    @ShellMethod("showColors")
    public void showColors() {
        printService.print(new AttributedString("blue", AttributedStyle.BOLD.foreground(AttributedStyle.BLUE)));
        printService.print(new AttributedString("black", AttributedStyle.BOLD.foreground(AttributedStyle.BLACK)));
        printService.print(new AttributedString("cyan", AttributedStyle.BOLD.foreground(AttributedStyle.CYAN)));
        printService.print(new AttributedString("green", AttributedStyle.BOLD_OFF.foreground(AttributedStyle.GREEN)));
        printService.print(new AttributedString("purple", AttributedStyle.BOLD.foreground(AttributedStyle.MAGENTA)));
        printService.print(new AttributedString("red", AttributedStyle.BOLD.foreground(AttributedStyle.RED)));
        printService.print(new AttributedString("white", AttributedStyle.BOLD.foreground(AttributedStyle.WHITE)));
        printService.print(new AttributedString("yellow", AttributedStyle.BOLD.foreground(AttributedStyle.YELLOW)));
    }

    @ShellMethod("showColors")
    public void logger() {
        printService.info("{}-{}", "hola", "mundo");
        printService.info("{}-{}-{}", "hola", "mundo");
        printService.info("{}", "hola", "mundo");

        
        
    }
}
