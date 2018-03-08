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

import static mx.infotec.dads.kukulkan.shell.util.AnsiConstants.ANSI_GREEN;
import static mx.infotec.dads.kukulkan.shell.util.AnsiConstants.ANSI_RESET;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.domain.Line;
import mx.infotec.dads.kukulkan.shell.domain.NativeCommand;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.services.CommandService;
import mx.infotec.dads.kukulkan.shell.util.LineProcessor;
import mx.infotec.dads.kukulkan.shell.util.LineValuedProcessor;
import mx.infotec.dads.kukulkan.shell.util.TextFormatter;

/**
 * Useful methods to handle the main Console.
 *
 * @author Daniel Cortes Pichardo
 */
@Service
public class CommandServiceImpl implements CommandService {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandServiceImpl.class);

    private static final String GENERIC_ERROR_MSG = "Errot at exec command";
    
    /**
     * The terminal.
     */
    @Autowired
    @Lazy
    private Terminal terminal;

    /**
     * The nav.
     */
    @Autowired
    private Navigator nav;

    /**
     * Printf.
     *
     * @param text the text
     * @deprecated prefered use TextFormatter class
     * @see TextFormatter
     */
    @Override
    @Deprecated
    public void printf(String text) {
        terminal.writer().append(text).append("\n");
        terminal.flush();
    }

    /**
     * Printf.
     *
     * @param key the key
     * @param message the message
     * @deprecated prefered use TextFormatter class
     * @see TextFormatter
     */
    @Override
    @Deprecated
    public void printf(String key, String message) {
        terminal.writer().append(String.format("%s[%-15s] %s-%-30s%n\t", ANSI_GREEN, key, ANSI_RESET, message));
        terminal.flush();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.shell.services.CommandService#exec(mx.infotec.
     * dads.kukulkan.shell.domain.ShellCommand,
     * mx.infotec.dads.kukulkan.shell.util.LineValuedProcessor)
     */
    @Override
    public List<Line> exec(final ShellCommand command, LineValuedProcessor processor) {
        List<Line> lines = new ArrayList<>();
        try {
            Process p = Runtime.getRuntime().exec(command.getExecutableCommand(), null, nav.getCurrentPath().toFile());
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                processor.process(line).ifPresent(lines::add);
            }
        } catch (Exception e) {
            LOGGER.debug(GENERIC_ERROR_MSG, e);
            printf(String.format("The command [%s] could not be executed", command));
        }
        return lines;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.shell.services.CommandService#exec(mx.infotec.
     * dads.kukulkan.shell.domain.ShellCommand)
     */
    @Override
    public List<CharSequence> exec(final ShellCommand command) {
        return exec(nav.getCurrentPath(), command);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.shell.services.CommandService#exec(java.nio.file
     * .Path, mx.infotec.dads.kukulkan.shell.domain.ShellCommand)
     */
    @Override
    public List<CharSequence> exec(final Path workingDirectory, final ShellCommand command) {
        return exec(workingDirectory, command, line -> Optional.ofNullable(new AttributedString(line)));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.shell.services.CommandService#exec(java.nio.file
     * .Path, mx.infotec.dads.kukulkan.shell.domain.ShellCommand,
     * mx.infotec.dads.kukulkan.shell.util.LineProcessor)
     */
    @Override
    public List<CharSequence> exec(final Path workingDirectory, final ShellCommand command, LineProcessor processor) {
        List<CharSequence> lines = new ArrayList<>();
        try {
            Process p = Runtime.getRuntime().exec(command.getExecutableCommand(), null, workingDirectory.toFile());
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                processor.process(line).ifPresent(lines::add);
            }
        } catch (Exception e) {
            LOGGER.debug(GENERIC_ERROR_MSG, e);
            lines.add(e.getMessage());
        }
        return lines;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.shell.services.CommandService#testNativeCommand(
     * mx.infotec.dads.kukulkan.shell.domain.NativeCommand)
     */
    @Override
    public void testNativeCommand(NativeCommand nc) {
        StringBuilder output = new StringBuilder();
        try {
            Process p = Runtime.getRuntime().exec(nc.getTestCommand());
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            LOGGER.info("[{}] is installed", nc.getCommand());
            nc.setInfoMessage(output.toString());
            nc.setActive(true);
        } catch (Exception e) {
            LOGGER.debug(GENERIC_ERROR_MSG, e);
            LOGGER.warn("[{}] is not installed", nc.getCommand());
            nc.setErrorMessage(String.format("You must install the command [%s]", nc.getCommand()));
        }
    }
}
