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

import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.GIT_COMMAND;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

import mx.infotec.dads.kukulkan.shell.domain.NativeCommand;
import mx.infotec.dads.kukulkan.shell.domain.NativeCommandContext;
import mx.infotec.dads.kukulkan.shell.services.CommandService;

/**
 * GitBaseCommands.
 *
 * @author Daniel Cortes Pichardo
 */
public abstract class GitBaseCommands {

    protected static final String LOGGER_EXEC = "exec [{} {}]";

    /** The command service. */
    @Autowired
    protected CommandService commandService;

    /** The project context. */
    @Autowired
    @Lazy
    protected NativeCommandContext projectContext;

    /** The publisher. */
    @Autowired
    protected ApplicationEventPublisher publisher;

    @Autowired
    protected GitContext gitContext;

    /**
     * Check for Availability in all git commands
     * 
     * @return Availability
     */
    @ShellMethodAvailability
    public Availability availabilityCheck() {
        NativeCommand gitCmd = projectContext.getAvailableCommands().get(GIT_COMMAND);
        if (gitCmd != null && gitCmd.isActive()) {
            return Availability.available();
        } else {
            return Availability.unavailable("you must install git");
        }
    }
}
