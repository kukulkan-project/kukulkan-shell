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
package mx.infotec.dads.kukulkan.shell.commands.git.service;

import java.io.Serializable;

/**
 *
 * @author erik.valdivieso
 */
public interface GitCommandsService extends Serializable {

    String LOGGER_EXEC = "exec [{} {}]";
    
    /**
     * Exec git status command.
     *
     * @return true if no error.
     */
    boolean status();

    /**
     * Exec git init command.
     *
     * @param quiet true if no output in excecution.
     * @return true if no error.
     */
    boolean init(boolean quiet);

    /**
     * Exec git clone command.
     *
     * @param repositoryPath Repository to clone.
     * @return true if no error.
     */
    boolean clone(String repositoryPath);

    /**
     * Exec git add command.
     *
     * @param force Force add operation.
     * @param fileName The filename.
     * @return true if no error.
     */
    boolean add(boolean force, String fileName);

    /**
     * Exec git commit command.
     *
     * @param message Message in the command.
     * @param author Author of commit.
     * @return true if no error.
     */
    boolean commit(String message, String author);

    /**
     * Exec git push command.
     *
     * @param setUpstream True if set upstream.
     * @return true if no error.
     */
    boolean push(boolean setUpstream);

    /**
     * Exec git pull command.
     *
     * @return true if no error.
     */
    boolean pull();
    
    /**
     * Exec git checkout command.
     * 
     * @param branchName The branch name.
     * @return true if no error.
     */
    boolean checkout(String branchName);
    
    /**
     * Exec git branch command.
     * 
     * @param branchName The branch name.
     * @return true if no error.
     */
    boolean branch(String branchName);
    
    /**
     * If branch exists, perform git checkout command. Otherwise perform git branch command.
     * 
     * @param branchName The branch name.
     * @return true if no error.
     */
    boolean branchOrCheckout(String branchName);
    
}
