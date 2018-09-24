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
package mx.infotec.dads.kukulkan.shell.services;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.domain.Line;
import mx.infotec.dads.kukulkan.shell.domain.NativeCommand;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.util.LineProcessor;
import mx.infotec.dads.kukulkan.shell.util.LineValuedProcessor;

/**
 * Useful methods to handle the main Console.
 *
 * @author Daniel Cortes Pichardo
 */

public interface CommandService {

    /**
     * Exec.
     *
     * @param workingDirectory
     *            the working directory
     * @param command
     *            the command
     * @param processor
     *            the processor
     * @return the list
     */
    List<Line> exec(final File workingDirectory, final ShellCommand command, LineValuedProcessor processor);

    /**
     * Exec.
     *
     * @param command
     *            the command
     * @return the list
     */
    List<CharSequence> exec(final ShellCommand command);

    /**
     * Exec.
     *
     * @param command
     *            the command
     * @param processor
     *            the processor
     * @return the list
     */
    List<Line> exec(final ShellCommand command, LineValuedProcessor processor);

    /**
     * Exec.
     *
     * @param workingDirectory
     *            the working directory
     * @param command
     *            the command
     * @return the list
     */
    List<CharSequence> exec(final Path workingDirectory, final ShellCommand command);

    /**
     * Exec.
     *
     * @param workingDirectory
     *            the working directory
     * @param command
     *            the command
     * @param processor
     *            the processor
     * @return the list
     */
    List<CharSequence> exec(final Path workingDirectory, final ShellCommand command, LineProcessor processor);

    /**
     * Exec a command in the current directory (from Navigator class), and write the
     * output to console with PrintService.
     * 
     * @param command
     *            The Command to excecute.
     * @return True if command end with no error.
     * @see PrintService
     * @see Navigator
     */
    boolean execToConsole(final ShellCommand command);

    /**
     * Exec a command in the workingDirectory, and write the output to console with
     * PrintService.
     * 
     * @param workingDirectory
     *            The working directory.
     * @param command
     *            The Command to excecute.
     * @return True if command end with no error.
     * @see PrintService
     */
    boolean execToConsole(final Path workingDirectory, final ShellCommand command);

    /**
     * Test native command.
     *
     * @param nc
     *            the nc
     */
    void testNativeCommand(NativeCommand nc);
}
