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

import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.CHECKOUT;
import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.DEVELOP_BRANCH;
import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.FEATURE_PREFIX;
import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.GIT_COMMAND;


import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.event.message.EventType;
import mx.infotec.dads.kukulkan.shell.event.message.LocationUpdatedEvent;
import mx.infotec.dads.kukulkan.shell.services.CommandService;

/**
 * Docker Commands.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class GitHotfixesCommands {

    /** The command service. */
    @Autowired
    private CommandService commandService;

    /** The publisher. */
    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Git create feature.
     *
     * @param name
     *            the name
     */
    @ShellMethod("Create a new Feature")
    public void gitHotfixList(@NotNull String name) {
        commandService.exec(new ShellCommand(GIT_COMMAND, CHECKOUT, "-b", FEATURE_PREFIX + name, DEVELOP_BRANCH));
        publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
    }

    /**
     * Git create feature.
     *
     * @param name
     *            the name
     */
    @ShellMethod("Create a new Feature")
    public void gitHotfixStart(@NotNull String name) {
        commandService.exec(new ShellCommand(GIT_COMMAND, CHECKOUT, "-b", FEATURE_PREFIX + name, DEVELOP_BRANCH));
        publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
    }

    /**
     * Git terminate feature.
     *
     * @param name
     *            the name
     */
    @ShellMethod("Terminate a Feature")
    public void gitHotfixFinish(@NotNull String name) {
        commandService.exec(new ShellCommand(GIT_COMMAND, CHECKOUT, DEVELOP_BRANCH));
        commandService.exec(new ShellCommand(GIT_COMMAND, "merge", "--no-ff", name));
        commandService.exec(new ShellCommand(GIT_COMMAND, "branch", "-d", FEATURE_PREFIX + name));
        publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
    }
}
