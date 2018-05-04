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
package mx.infotec.dads.kukulkan.shell.commands.git;

import javax.validation.constraints.NotNull;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import mx.infotec.dads.kukulkan.shell.commands.git.valueprovided.GitValueProvider;

/**
 * Docker Commands.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class GitCommands extends GitBaseCommands {

    /**
     * Git status.
     */
    @ShellMethod("Displays paths that have differences between the index file and the current HEAD commit")
    public void gitStatus() {
        gitCommandsService.status();
    }

    @ShellMethod("Init an git repositoty")
    public void gitInit(@ShellOption(value = {"-q", "--quiet"}) boolean quiet) {
        gitCommandsService.init(quiet);
    }

    @ShellMethod("Clones a repository into a newly created directory, creates remote-tracking branches for each branch in the cloned repository")
    public void gitClone(@NotNull String repositoryPath) {
        gitCommandsService.clone(repositoryPath);
    }

    @ShellMethod("Add the fileName to the stage, to prepare the content staged for the next commit")
    public void gitAdd(@ShellOption(value = {"-f", "--force"}) boolean force, @ShellOption(value = {"--fileName"}, valueProvider = GitValueProvider.GitAddValueProvider.class) @NotNull String fileName) {
        gitCommandsService.add(force, fileName);
    }

    @ShellMethod("Add all files to the stage, to prepare the content staged for the next commit")
    public void gitAddAll(@ShellOption(value = {"-f", "--force"}) boolean force) {
        gitCommandsService.add(force, GitHelper.ADD_ALL_PARAM);
    }

    @ShellMethod("Stores the current contents of the index in a new commit along with a log message from the user describing the changes")
    public void gitCommit(@ShellOption(value = {"--message", "-m"}) @NotNull String message, @ShellOption(value = {"--author"}, defaultValue = "") String author) {
        gitCommandsService.commit(message, author);
    }

    @ShellMethod("Updates remote refs using local refs, while sending objects necessary to complete the given refs")
    public void gitPush(boolean setUpstream) {
        gitCommandsService.push(setUpstream);
    }

    @ShellMethod("Fetch from and integrate with another repository or a local branch")
    public void gitPull() {
        gitCommandsService.pull();
    }

    @ShellMethod("Updates files in the working tree to match the version in the index or the specified tree")
    public void gitCheckout(
            @ShellOption(valueProvider = GitValueProvider.GitBranchValueProvider.class) @NotNull String branchName) {
        gitCommandsService.checkout(branchName);
    }

    @ShellMethod("List all the branches in the local repository")
    public void gitBranches() {
        gitCommandsService.branch(null);
    }

    @ShellMethod("Checkout to develop branch, create develop branch if not exits")
    public void gitDevelop() {
        gitCommandsService.branchOrCheckout(GitHelper.DEVELOP_BRANCH);
    }

    @ShellMethod("List all the branches in the local repository")
    public void gitMaster() {
        gitCommandsService.checkout(GitHelper.MASTER_BRANCH);
    }

}
