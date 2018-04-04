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

import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.ADD;
import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.CLONE;
import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.GIT_COMMAND;
import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.STATUS;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.jline.utils.AttributedString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import mx.infotec.dads.kukulkan.shell.commands.git.valueprovided.GitValueProvider;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.event.message.EventType;
import mx.infotec.dads.kukulkan.shell.event.message.LocationUpdatedEvent;
import mx.infotec.dads.kukulkan.shell.util.TextFormatter;

/**
 * Docker Commands.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class GitCommands extends GitBaseCommands {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitCommands.class);

    /**
     * Git status.
     *
     * @return the list
     */
    @ShellMethod("Displays paths that have differences between the index file and the current HEAD commit")
    public List<AttributedString> gitStatus() {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, STATUS);
        return execGitCommand(new ShellCommand(GIT_COMMAND, STATUS));
    }

    @ShellMethod("Clones a repository into a newly created directory, creates remote-tracking branches for each branch in the cloned repository")
    public List<AttributedString> gitClone(@NotNull String repositoryPath) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, CLONE);
        return execGitCommand(new ShellCommand(GIT_COMMAND, CLONE).addArg(repositoryPath));
    }

    @ShellMethod("Updates the index using the current content found in the working tree, to prepare the content staged for the next commit")
    public List<AttributedString> gitAdd(@NotNull String fileName) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, ADD);
        return execGitCommand(new ShellCommand(GIT_COMMAND, ADD).addArg(fileName));
    }

    @ShellMethod("Stores the current contents of the index in a new commit along with a log message from the user describing the changes")
    public List<AttributedString> gitCommit(@NotNull String message) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.COMMIT);
        return execGitCommand(new ShellCommand(GIT_COMMAND, GitHelper.COMMIT).addArg("-m").addArg(message));
    }

    @ShellMethod("Updates remote refs using local refs, while sending objects necessary to complete the given refs")
    public List<AttributedString> gitPush(boolean setUpstream) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.PUSH);
        ShellCommand shellCommand = new ShellCommand(GIT_COMMAND, GitHelper.PUSH);
        if (setUpstream) {
            shellCommand.addArg("--set-upstream").addArg("origin").addArg(gitContext.getCurrentBranchName());
        }
        return execGitCommand(shellCommand);
    }

    @ShellMethod("Fetch from and integrate with another repository or a local branch")
    public List<AttributedString> gitPull() {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.PULL);
        return execGitCommand(new ShellCommand(GIT_COMMAND, GitHelper.PULL));
    }

    @ShellMethod("Updates files in the working tree to match the version in the index or the specified tree")
    public List<AttributedString> gitCheckout(
            @ShellOption(valueProvider = GitValueProvider.GitBranchValueProvider.class) @NotNull String branchName) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.CHECKOUT);
        return execGitCommand(new ShellCommand(GIT_COMMAND, GitHelper.CHECKOUT).addArg(branchName));
    }

    @ShellMethod("List all the branches in the local repository")
    public List<AttributedString> gitBranches() {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.BRANCH);
        return execGitCommand(new ShellCommand(GIT_COMMAND, GitHelper.BRANCH));
    }

    private List<AttributedString> execGitCommand(ShellCommand shellCommand) {
        List<CharSequence> exec = commandService.exec(shellCommand);
        publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
        return TextFormatter.formatToGitOutput(exec);
    }
}
