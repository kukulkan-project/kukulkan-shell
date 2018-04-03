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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import mx.infotec.dads.kukulkan.shell.domain.NativeCommand;
import mx.infotec.dads.kukulkan.shell.domain.NativeCommandContext;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.event.message.EventType;
import mx.infotec.dads.kukulkan.shell.event.message.LocationUpdatedEvent;
import mx.infotec.dads.kukulkan.shell.services.CommandService;
import mx.infotec.dads.kukulkan.shell.util.TextFormatter;

/**
 * Docker Commands.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class GitCommands {

    private static final String LOGGER_EXEC = "exec [{} {}]";

    private static final Logger LOGGER = LoggerFactory.getLogger(GitCommands.class);

    /** The command service. */
    @Autowired
    CommandService commandService;

    /** The project context. */
    @Autowired
    NativeCommandContext projectContext;

    /** The publisher. */
    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Git status.
     *
     * @return the list
     */
    @ShellMethod("Show the status of the current git project")
    public List<AttributedString> gitStatus() {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, STATUS);
        return execGitCommand(new ShellCommand(GIT_COMMAND, STATUS));
    }

    @ShellMethod("Show the status of the current git project")
    public List<AttributedString> gitClone(@NotNull String repositoryPath) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, CLONE);
        return execGitCommand(new ShellCommand(GIT_COMMAND, CLONE).addArg(repositoryPath));
    }

    @ShellMethod("Show the status of the current git project")
    public List<AttributedString> gitAdd(@NotNull String fileName) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, ADD);
        return execGitCommand(new ShellCommand(GIT_COMMAND, ADD).addArg(fileName));
    }

    @ShellMethod("Change to other branch")
    public List<AttributedString> gitCheckout(
            @ShellOption(valueProvider = GitValueProvider.class) @NotNull String fileName) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.CHECKOUT);
        return execGitCommand(new ShellCommand(GIT_COMMAND, GitHelper.CHECKOUT).addArg(fileName));
    }

    @ShellMethod("Show the status of the current git project")
    public List<AttributedString> gitBranchList(@NotNull String fileName) {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, GitHelper.BRANCH);
        return execGitCommand(new ShellCommand(GIT_COMMAND, GitHelper.BRANCH));
    }

    private List<AttributedString> execGitCommand(ShellCommand shellCommand) {
        List<CharSequence> exec = commandService.exec(shellCommand);
        publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
        return TextFormatter.formatToGitOutput(exec);
    }

    @ShellMethodAvailability("gitStatus")
    public Availability gitStatusAvailability() {
        NativeCommand gitCmd = projectContext.getAvailableCommands().get(GIT_COMMAND);
        if (gitCmd != null && gitCmd.isActive()) {
            return Availability.available();
        } else {
            return Availability.unavailable("you must install git");
        }
    }
}
