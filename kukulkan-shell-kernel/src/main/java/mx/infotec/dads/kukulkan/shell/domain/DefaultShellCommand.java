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

/**
 * Native Command show the main commands.
 *
 * @author Daniel Cortes Pichardo
 */
public class DefaultShellCommand implements NativeCommand {

    /** The command. */
    private String command;
    
    /** The test command. */
    private String testCommand;
    
    /** The info message. */
    private String infoMessage;
    
    /** The error message. */
    private String errorMessage;
    
    /** The is active. */
    private boolean isActive;

    /**
     * Instantiates a new default shell command.
     *
     * @param command the command
     * @param testCommand the test command
     */
    public DefaultShellCommand(String command, String testCommand) {
        this.command = command;
        this.testCommand = testCommand;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.shell.domain.NativeCommand#getCommand()
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

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.shell.domain.NativeCommand#getTestCommand()
     */
    @Override
    public String getTestCommand() {
        return this.testCommand;
    }

    /**
     * Sets the test command.
     *
     * @param testCommand the new test command
     */
    public void setTestCommand(String testCommand) {
        this.testCommand = testCommand;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.shell.domain.NativeCommand#getInfoMessage()
     */
    @Override
    public String getInfoMessage() {
        return infoMessage;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.shell.domain.NativeCommand#setInfoMessage(java.lang.String)
     */
    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.shell.domain.NativeCommand#getErrorMessage()
     */
    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.shell.domain.NativeCommand#setErrorMessage(java.lang.String)
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.shell.domain.NativeCommand#isActive()
     */
    @Override
    public boolean isActive() {
        return isActive;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.shell.domain.NativeCommand#setActive(boolean)
     */
    @Override
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((command == null) ? 0 : command.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DefaultShellCommand other = (DefaultShellCommand) obj;
        if (command == null) {
            if (other.command != null)
                return false;
        } else if (!command.equals(other.command))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(NativeCommand o) {
        return this.command.compareTo(o.getCommand());
    }
}
