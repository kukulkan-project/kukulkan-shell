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
public interface NativeCommand extends Command, Comparable<NativeCommand> {

    /**
     * Return the command to be executed.
     *
     * @return the command
     */
    public String getCommand();

    /**
     * Return the command used for test if the command is presented, In the test
     * process when the testCommand is used, it must return true if the command
     * is installed in the current machine, false otherwise.
     *
     * @return the test command
     */
    public String getTestCommand();

    /**
     * Get a useful info message to show in the console.
     *
     * @return the info message
     */
    public String getInfoMessage();

    /**
     * Get a useful info message to show in the console.
     *
     * @param message the new info message
     */
    public void setInfoMessage(String message);

    /**
     * Return a message if the command is not installed.
     *
     * @return the error message
     */
    public String getErrorMessage();

    /**
     * Return a message if the command is not installed.
     *
     * @param message the new error message
     */
    public void setErrorMessage(String message);

    /**
     * Return true if the command is installed, false otherwise.
     *
     * @return boolean
     */
    public boolean isActive();

    /**
     * Set true if the command is installed in the machine, false otherwise.
     *
     * @param active the new active
     */
    public void setActive(boolean active);
}
