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

import java.util.List;

import mx.infotec.dads.kukulkan.shell.commands.git.GitCommands;
import mx.infotec.dads.kukulkan.shell.commands.git.GitContext;
import mx.infotec.dads.kukulkan.shell.commands.git.GitHelper;
import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.ADD;
import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.CLONE;
import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.GIT_COMMAND;
import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.INIT;
import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.STATUS;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.event.message.EventType;
import mx.infotec.dads.kukulkan.shell.event.message.LocationUpdatedEvent;
import mx.infotec.dads.kukulkan.shell.services.CommandService;
import mx.infotec.dads.kukulkan.shell.services.PrintService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 *
 * @author erik.valdivieso
 */
@Service
public class GitCommandsServiceImpl implements GitCommandsService {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(GitCommandsServiceImpl.class);

    @Autowired
    protected CommandService commandService;
    
    @Autowired
    protected ApplicationEventPublisher publisher;    
    
    @Autowired
    protected GitContext gitContext;
    
    @Autowired
    private PrintService printService;

    @Autowired
    private GitCommands gitCommands;
    
    @Override
    public boolean status() {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, STATUS);
        return execGitCommand(new ShellCommand(GIT_COMMAND, STATUS));
    }

    @Override
    public boolean init(boolean quiet) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, INIT);
        ShellCommand shellCommand = new ShellCommand(GIT_COMMAND, INIT);

        if (quiet) {
            shellCommand.addArg("-q");
        }

        return execGitCommand(shellCommand);
    }

    @Override
    public boolean clone(String repositoryPath) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, CLONE);
        return execGitCommand(new ShellCommand(GIT_COMMAND, CLONE).addArg(repositoryPath));
    }

    @Override
    public boolean add(boolean force, String fileName) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, ADD);
        ShellCommand shellCommand = new ShellCommand(GIT_COMMAND, ADD);

        if (force) {
            shellCommand.addArg("-f");
        }

        return execGitCommand(shellCommand.addArg(fileName));
    }
    
    @Override
    public void addAll(String desc, String longDesc) {
        if (gitCommands.availabilityCheck().isAvailable()) {
            boolean res;
            res = init(true);

            if (res) {
                res = add(false, GitHelper.ADD_ALL_PARAM);
                if (res) {
                    res = commit(desc,
                            longDesc);
                    if (res) {
                        branchOrCheckout(GitHelper.DEVELOP_BRANCH);
                    }
                }
            }
        } else {
            printService.warning("Git not availability");
        }

    }

    @Override
    public boolean commit(String message, String author) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.COMMIT);
        ShellCommand shellCommand = new ShellCommand(GIT_COMMAND, GitHelper.COMMIT).addArg("-m").addArg(message);

        if (author != null && !author.isEmpty()) {
            shellCommand.addArg("--author").addArg(author);
        }

        return execGitCommand(shellCommand);
    }

    @Override
    public boolean push(boolean setUpstream) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.PUSH);
        ShellCommand shellCommand = new ShellCommand(GIT_COMMAND, GitHelper.PUSH);
        if (setUpstream) {
            shellCommand.addArg("--set-upstream").addArg("origin").addArg(gitContext.getCurrentBranchName());
        }
        return execGitCommand(shellCommand);
    }

    @Override
    public boolean pull() {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.PULL);
        return execGitCommand(new ShellCommand(GIT_COMMAND, GitHelper.PULL));
    }

    @Override
    public boolean checkout(String branchName) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.CHECKOUT);
        return execGitCommand(new ShellCommand(GIT_COMMAND, GitHelper.CHECKOUT).addArg(branchName));
    }

    @Override
    public boolean branch(String branchName) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.BRANCH);
        
        ShellCommand shellCommand = new ShellCommand(GIT_COMMAND, GitHelper.BRANCH);
        
        if (branchName != null && !branchName.isEmpty()) {
            shellCommand.addArg(branchName);
        }
        
        return execGitCommand(shellCommand);
    }

    @Override
    public boolean branchOrCheckout(String branchName) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.BRANCH);
        List<CharSequence> branches = commandService.exec(new ShellCommand(GIT_COMMAND, GitHelper.BRANCH));

        boolean create = true;

        for (CharSequence attr : branches) {
            if (attr.toString().replace("*", "").trim().equals(GitHelper.DEVELOP_BRANCH)) {
                create = false;
                break;
            }
        }

        boolean noError;
        
        if (create) {
            LOGGER.debug("Create develop branch");
            noError = branch(GitHelper.DEVELOP_BRANCH);
        } else {
            noError = true;
        }

        if (noError) {
            return checkout(GitHelper.DEVELOP_BRANCH);
        } else {
            return false;
        }
    }
    
    private boolean execGitCommand(ShellCommand shellCommand) {
        boolean res = commandService.execToConsole(shellCommand);
        publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
        return res;
    }
}
