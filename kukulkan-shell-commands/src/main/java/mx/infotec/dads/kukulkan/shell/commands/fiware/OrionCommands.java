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
package mx.infotec.dads.kukulkan.shell.commands.fiware;

import static mx.infotec.dads.kukulkan.shell.commands.docker.DockerCommands.DOCKER_COMMAND;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import mx.infotec.dads.kukulkan.shell.commands.fiware.dto.OrionEntityResponse;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.services.CommandService;

/**
 * Orion Context Broker Commands.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class OrionCommands {

    /** The Constant NULL. */
    public static final String NULL = "@NULL";

    /** The command service. */
    @Autowired
    private CommandService commandService;

    @Autowired
    private OrionService orionService;

    /** The is running. */
    private boolean isRunning;

    /**
     * Orion start.
     *
     * @param port
     *            the port
     */
    @ShellMethod("Create a instance of the Orion Context Broker")
    public void orionStart(@ShellOption(defaultValue = "1026") String port) {
        commandService.exec(new ShellCommand(DOCKER_COMMAND).addArg("run").addArg("-d").addArg("--name")
                .addArg("-orion").addArg("-p").addArg(port + ":1026").addArg("fiware/orion"));
    }

    /**
     * Orion start.
     *
     * @param port
     *            the port
     */
    @ShellMethod("Create a instance of the Orion Context Broker")
    public List<AttributedString> getAllEntities(@NotNull String url) {
        Optional<List<OrionEntityResponse>> entities = orionService.getAllEntities(url);
        if (entities.isPresent()) {
            return formatToNGSI(entities.get());
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * format a List<CharSequence> in order to highlight some git command
     * results.
     *
     * @param input
     *            the input
     * @return List<AttributedString>
     */
    @Deprecated
    public static List<AttributedString> formatToNGSI(List<OrionEntityResponse> orionReponse) {
        return orionReponse.stream().map(result -> {
            AttributedStringBuilder asb = new AttributedStringBuilder();
            asb.append(new AttributedString(result.getId(), AttributedStyle.BOLD.foreground(AttributedStyle.BLUE)));
            asb.append("\n");
            asb.append(new AttributedString(result.getType(), AttributedStyle.BOLD.foreground(AttributedStyle.BLUE)));
            asb.append("\n");
            processMap(result.getAdditionalProperties(), asb);
            asb.append("\n");
            return asb.toAttributedString();
        }).collect(Collectors.toList());
    }

    private static void processMap(Map<String, Object> additionalProperties, AttributedStringBuilder asb) {
        for (Entry<String, Object> property : additionalProperties.entrySet()) {
            asb.append(new AttributedString(property.getKey(), AttributedStyle.BOLD.foreground(AttributedStyle.BLUE)));
            asb.append(":");
            asb.append(new AttributedString(property.getValue().toString(),
                    AttributedStyle.BOLD.foreground(AttributedStyle.WHITE)));
            asb.append("\n");
        }
    }

    /**
     * Orion stop.
     *
     * @param containerId
     *            the container id
     */
    @ShellMethod("Stop a instance of the Orion Context Broker")
    public void orionStop(@ShellOption(defaultValue = NULL) String containerId) {
        commandService.exec(new ShellCommand(DOCKER_COMMAND).addArg("start").addArg(containerId));
    }

    /**
     * Orion start availability.
     *
     * @return the availability
     */
    @ShellMethodAvailability({ "dockerShowRunningProcess", "dockerStop" })
    public Availability orionStartAvailability() {
        if (!isRunning) {
            return Availability.available();
        } else {
            return Availability.unavailable("you have a Orion Context Broker instance running");
        }
    }
}
