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
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jline.utils.AttributedString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.domain.Line;
import mx.infotec.dads.kukulkan.shell.domain.NativeCommand;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.services.CommandService;
import mx.infotec.dads.kukulkan.shell.services.PrintService;
import mx.infotec.dads.kukulkan.shell.util.BufferCollector;
import mx.infotec.dads.kukulkan.shell.util.CharSequenceCollector;
import mx.infotec.dads.kukulkan.shell.util.LineCollector;
import mx.infotec.dads.kukulkan.shell.util.LineProcessor;
import mx.infotec.dads.kukulkan.shell.util.LineValuedProcessor;
import mx.infotec.dads.kukulkan.shell.util.StringBuilderCollector;

/**
 * Useful methods to handle the main Console.
 *
 * @author Daniel Cortes Pichardo
 * @param <A>
 * @param <R>
 * @param <T>
 */
@Service
public class CommandServiceImpl implements CommandService {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandServiceImpl.class);

    private static final String GENERIC_ERROR_MSG = "Errot at exec command";

    /**
     * The nav.
     */
    @Autowired
    private Navigator nav;

    @Autowired
    private PrintService printService;

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
            ProcessBuilder pb = new ProcessBuilder(fixShellCommand(command).getExecutableCommand());
            pb.directory(nav.getCurrentPath().toFile());
            Process p = pb.start();
            List<Line> readBufferProcess = readBufferProcess(p, new LineCollector(processor));
            p.waitFor();
            return readBufferProcess;
        } catch (Exception e) {
            LOGGER.debug(GENERIC_ERROR_MSG, e);
            printService.print(new AttributedString(String.format("The command [%s] could not be executed", command)));
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
            ProcessBuilder pb = new ProcessBuilder(fixShellCommand(command).getExecutableCommand());
            pb.directory(workingDirectory.toFile());
            Process p = pb.start();
            p.waitFor();
            lines = readBufferProcess(p, new CharSequenceCollector());
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
        String output;
        try {
            Process p = Runtime.getRuntime().exec(nc.getTestCommand());
            p.waitFor();
            printService.info(nc.getCommand() + " is installed");
            output = readBufferProcess(p, new StringBuilderCollector());
            nc.setInfoMessage(output);
            nc.setActive(true);
        } catch (Exception e) {
            nc.setErrorMessage(String.format("You must install the command [%s]", nc.getCommand()));
            printService.warning(nc.getErrorMessage());
        }
    }

    public static <L> L readBufferProcess(Process p, BufferCollector<L> bufferCollector) throws IOException {
        String stringHolder;
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        while ((stringHolder = stdInput.readLine()) != null) {
            bufferCollector.collect(stringHolder);
        }
        while ((stringHolder = stdError.readLine()) != null) {
            bufferCollector.collect(stringHolder);
        }
        return bufferCollector.getCollection();
    }

    private static boolean isWindowsOS() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    private static ShellCommand fixShellCommand(final ShellCommand command) {
        if (isWindowsOS()) {
            ShellCommand windowsCommand = getWindowsBaseShellCommand();
            for (String arg : command.getExecutableCommand()) {
                windowsCommand.addArg(arg);
            }
            return windowsCommand;
        }
        return command;
    }

    private static ShellCommand getWindowsBaseShellCommand() {
        return new ShellCommand("cmd.exe").addArg("/C");
    }
}