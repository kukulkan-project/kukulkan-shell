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
            pb.redirectErrorStream(true);
            Process p = pb.start();
            try (InputStream is = p.getInputStream()) {
                List<Line> readBufferProcess = readBufferProcess(is, new LineCollector(processor));
                p.waitFor();
                return readBufferProcess;
            }
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
            pb.redirectErrorStream(true);
            Process p = pb.start();
            try (InputStream is = p.getInputStream()) {
                p.waitFor();
                lines = readBufferProcess(is, new CharSequenceCollector());
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
        String output;
        try {
            Process p = Runtime.getRuntime().exec(nc.getTestCommand());
            try (InputStream is = p.getInputStream()) {
                p.waitFor();
                printService.info(nc.getCommand() + " is installed");
                output = readBufferProcess(is, new StringBuilderCollector());
                nc.setInfoMessage(output);
                nc.setActive(true);
            }
        } catch (Exception e) {
            nc.setErrorMessage(String.format("You must install the command [%s]", nc.getCommand()));
            printService.warning(nc.getErrorMessage());
        }
    }

    private static <L> L readBufferProcess(InputStream is, BufferCollector<L> bufferCollector) throws IOException {
        String stringHolder;
        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(is))) {
            while ((stringHolder = stdInput.readLine()) != null) {
                bufferCollector.collect(stringHolder);
            }
        }
        return bufferCollector.getCollection();
    }

    @Override
    public boolean execToConsole(ShellCommand command) {
        return execToConsole(nav.getCurrentPath(), command);
    }

    @Override
    public boolean execToConsole(Path workingDirectory, ShellCommand command) {
        ProcessBuilder pb = new ProcessBuilder(fixShellCommand(command).getExecutableCommand());
        pb.directory(nav.getCurrentPath().toFile());

        try {
            Process p = pb.start();

            try (InputStream inputStream = p.getInputStream(); InputStream errorStream = p.getErrorStream()) {
                InputStreamHandler inputStreamHandler = new InputStreamHandler(inputStream, printService);
                InputStreamHandler errorStreamHandler = new InputStreamHandler(errorStream, printService, true);

                inputStreamHandler.start();
                errorStreamHandler.start();

                return p.waitFor() == 0;
            }
        } catch (IOException | InterruptedException ex) {
            LOGGER.error("Error at execute command", ex);
        }

        return false;
    }

    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException ex) {
                LOGGER.warn("Error al close stream", ex);
            }
        }
    }

    public static boolean isWindowsOS() {
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
