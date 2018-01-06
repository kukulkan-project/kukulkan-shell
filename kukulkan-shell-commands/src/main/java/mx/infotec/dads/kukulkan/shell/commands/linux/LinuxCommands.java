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
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import mx.infotec.dads.kukulkan.shell.services.CommandService;
import mx.infotec.dads.kukulkan.shell.util.AnsiConstants;

/**
 * Util Commands.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class LinuxCommands {

    /** The command service. */
    @Autowired
    CommandService commandService;

    /**
     * Ping.
     *
     * @param host the host
     * @return the string
     */
    @ShellMethod("make a ping to a host")
    public String ping(String host) {
        commandService.printf("todo bien");
        commandService.printf(AnsiConstants.ANSI_BLUE, "blue");
        commandService.printf(AnsiConstants.ANSI_BLACK, "black");
        commandService.printf(AnsiConstants.ANSI_CYAN, "cyan");
        commandService.printf(AnsiConstants.ANSI_GRAY, "gray");
        commandService.printf(AnsiConstants.ANSI_GREEN, "green");
        commandService.printf(AnsiConstants.ANSI_PURPLE, "purple");
        commandService.printf(AnsiConstants.ANSI_RED, "red");
        commandService.printf(AnsiConstants.ANSI_WHITE, "white");
        commandService.printf(AnsiConstants.ANSI_YELLOW, "yellow");

        String command = "ping -c 3 " + host;
        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }
}
