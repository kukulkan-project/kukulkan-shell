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
package mx.infotec.dads.kukulkan.shell.commands.docker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import mx.infotec.dads.kukulkan.shell.domain.NativeCommand;
import mx.infotec.dads.kukulkan.shell.domain.NativeCommandContext;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.services.CommandService;

/**
 * Docker Commands.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class DockerCommands {

    /** The Constant NULL. */
    private static final String NULL = "@NULL";

    /** The Constant DOCKER_COMMAND. */
    public static final String DOCKER_COMMAND = "docker";

    /** The project context. */
    @Autowired
    NativeCommandContext projectContext;

    /** The command service. */
    @Autowired
    CommandService commandService;

    /**
     * Docker ps.
     */
    @ShellMethod("Show the current docker process running")
    @ShellMethodAvailability({ "dockerCommandAvailability" })
    public void dockerPs() {
        commandService.exec(new ShellCommand(DOCKER_COMMAND, "ps"));
    }

    /**
     * Docker stop.
     *
     * @param containerId
     *            the container id
     */
    @ShellMethod("Show the current docker process running")
    @ShellMethodAvailability({ "dockerCommandAvailability" })
    public void dockerStop(
            @ShellOption(valueProvider = DockerStopValueProvider.class, defaultValue = NULL) String containerId) {
        commandService.exec(new ShellCommand(DOCKER_COMMAND, "stop", containerId));
    }

    /**
     * Docker start.
     *
     * @param containerId
     *            the container id
     */
    @ShellMethod("Show the current docker process running")
    @ShellMethodAvailability({ "dockerCommandAvailability" })
    public void dockerStart(
            @ShellOption(valueProvider = DockerStartValueProvider.class, defaultValue = NULL) String containerId) {
        commandService.exec(new ShellCommand(DOCKER_COMMAND, "start", containerId));
    }

    /**
     * Docker show running process availability.
     *
     * @return the availability
     */
    @ShellMethodAvailability({ "dockerCommandAvailability" })
    public Availability dockerCommandAvailability() {
        NativeCommand dockerCmd = projectContext.getAvailableCommands().get(DOCKER_COMMAND);
        if (dockerCmd != null && dockerCmd.isActive()) {
            return Availability.available();
        } else {
            return Availability.unavailable("you must install docker");
        }
    }
}
