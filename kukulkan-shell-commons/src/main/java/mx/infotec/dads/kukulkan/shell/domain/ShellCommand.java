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
package mx.infotec.dads.kukulkan.shell.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Native Command show the main commands.
 *
 * @author Daniel Cortes Pichardo
 */
public class ShellCommand implements Command {

    /** The command. */
    private String command;

    /** The args. */
    private List<Args> args = new ArrayList<>();

    /**
     * Instantiates a new shell command.
     *
     * @param command the command
     */
    public ShellCommand(String command) {
        this.command = command;
    }

    /**
     * Instantiates a new shell command.
     *
     * @param command the command
     * @param args the args
     */
    public ShellCommand(String command, String... args) {
        this.command = command;
        for (String arg : args) {
            this.args.add(new Args(arg));
        }
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.shell.domain.Command#getCommand()
     */
    public String getCommand() {
        return this.command;
    }

    /**
     * Sets the command.
     *
     * @param command the new command
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * Adds the arg.
     *
     * @param arg the arg
     * @return the shell command
     */
    public ShellCommand addArg(String arg) {
        this.args.add(new Args(arg));
        return this;
    }

    /**
     * Gets the args.
     *
     * @return the args
     */
    public String getArgs() {
        StringBuilder sb = new StringBuilder();
        for (Args arg : args) {
            sb.append(" ").append(arg.getParam());
        }
        return sb.toString();

    }

    /**
     * Gets the executable command.
     *
     * @return the executable command
     */
    public String getExecutableCommand() {
        StringBuilder sb = new StringBuilder(command);
        for (Args arg : args) {
            sb.append(" ").append(arg.getParam());
        }
        return sb.toString();

    }
}
