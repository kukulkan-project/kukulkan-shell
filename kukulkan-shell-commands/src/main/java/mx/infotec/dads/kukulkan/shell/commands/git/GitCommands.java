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
import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.INIT;
import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.STATUS;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import mx.infotec.dads.kukulkan.shell.commands.git.valueprovided.GitValueProvider;
import mx.infotec.dads.kukulkan.shell.domain.Line;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.event.message.EventType;
import mx.infotec.dads.kukulkan.shell.event.message.LocationUpdatedEvent;
import mx.infotec.dads.kukulkan.shell.services.PrintService;
import mx.infotec.dads.kukulkan.shell.util.TextFormatter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Docker Commands.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class GitCommands extends GitBaseCommands {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitCommands.class);

    @Autowired
    private PrintService printService;

    /**
     * Git status.
     */
    @ShellMethod("Displays paths that have differences between the index file and the current HEAD commit")
    public void gitStatus() {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, STATUS);
        execGitCommand(new ShellCommand(GIT_COMMAND, STATUS));
    }

    @ShellMethod("Init an git repositoty")
    public void gitInit(@ShellOption(value = {"-q", "--quiet"}) boolean quiet) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, INIT);
        ShellCommand shellCommand = new ShellCommand(GIT_COMMAND, INIT);
        
        if (quiet) {
            shellCommand.addArg("-q");
        }
        
        execGitCommand(shellCommand);
    }

    @ShellMethod("Clones a repository into a newly created directory, creates remote-tracking branches for each branch in the cloned repository")
    public void gitClone(@NotNull String repositoryPath) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, CLONE);
        execGitCommand(new ShellCommand(GIT_COMMAND, CLONE).addArg(repositoryPath));
    }

    @ShellMethod("Add the fileName to the stage, to prepare the content staged for the next commit")
    public void gitAdd(@ShellOption(value = {"-f", "--force"}) boolean force, @ShellOption(value = {"--fileName"}) @NotNull String fileName) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, ADD);
        ShellCommand shellCommand = new ShellCommand(GIT_COMMAND, ADD);
        
        if (force) {
            shellCommand.addArg("-f");
        }

        execGitCommand(shellCommand.addArg(fileName));
    }

    @ShellMethod("Add all files to the stage, to prepare the content staged for the next commit")
    public void gitAddAll(@ShellOption(value = {"-f", "--force"}) boolean force) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, ADD);
        ShellCommand shellCommand = new ShellCommand(GIT_COMMAND, ADD).addArg("-A");
        
        if (force) {
            shellCommand.addArg("-f");
        }
        
        execGitCommand(shellCommand);
    }


    @ShellMethod("Stores the current contents of the index in a new commit along with a log message from the user describing the changes")
    public void gitCommit(@NotNull String message, @ShellOption(value = {"--author"}) String author) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.COMMIT);
        ShellCommand shellCommand = new ShellCommand(GIT_COMMAND, GitHelper.COMMIT).addArg("-m").addArg(message);
        
        if (author != null && !author.isEmpty()) {
            shellCommand.addArg("--author").addArg(author);
        }
        
        execGitCommand(shellCommand);
    }

    @ShellMethod("Updates remote refs using local refs, while sending objects necessary to complete the given refs")
    public void gitPush(boolean setUpstream) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.PUSH);
        ShellCommand shellCommand = new ShellCommand(GIT_COMMAND, GitHelper.PUSH);
        if (setUpstream) {
            shellCommand.addArg("--set-upstream").addArg("origin").addArg(gitContext.getCurrentBranchName());
        }
        execGitCommand(shellCommand);
    }

    @ShellMethod("Fetch from and integrate with another repository or a local branch")
    public void gitPull() {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.PULL);
        execGitCommand(new ShellCommand(GIT_COMMAND, GitHelper.PULL));
    }

    @ShellMethod("Updates files in the working tree to match the version in the index or the specified tree")
    public void gitCheckout(
            @ShellOption(valueProvider = GitValueProvider.GitBranchValueProvider.class) @NotNull String branchName) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.CHECKOUT);
        execGitCommand(new ShellCommand(GIT_COMMAND, GitHelper.CHECKOUT).addArg(branchName));
    }

    @ShellMethod("List all the branches in the local repository")
    public void gitBranches() {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.BRANCH);
        execGitCommand(new ShellCommand(GIT_COMMAND, GitHelper.BRANCH));
    }

    @ShellMethod("Checkout to develop branch, create develop branch if not exits")
    public void gitDevelop() {
        List<CharSequence> branches = commandService.exec(new ShellCommand(GIT_COMMAND, GitHelper.BRANCH));

        boolean create = true;

        for (CharSequence attr : branches) {
            if (attr.toString().replace("*", "").trim().equals(GitHelper.DEVELOP_BRANCH)) {
                create = false;
                break;
            }
        }

        if (create) {
            LOGGER.debug("Create develop branch");
            LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.BRANCH);
            execGitCommand(new ShellCommand(GIT_COMMAND, GitHelper.BRANCH, GitHelper.DEVELOP_BRANCH));
        }

        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.CHECKOUT);
        execGitCommand(new ShellCommand(GIT_COMMAND, GitHelper.CHECKOUT, GitHelper.DEVELOP_BRANCH));
    }

    @ShellMethod("List all the branches in the local repository")
    public void gitMaster() {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.CHECKOUT);
        execGitCommand(new ShellCommand(GIT_COMMAND, GitHelper.CHECKOUT, GitHelper.MASTER_BRANCH));
    }

    private void execGitCommand(ShellCommand shellCommand) {
        commandService.exec(shellCommand, line -> {
            printService.print(TextFormatter.formatLogText(line));
            return Optional.ofNullable(new Line(line));
        });

        publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
    }

}
