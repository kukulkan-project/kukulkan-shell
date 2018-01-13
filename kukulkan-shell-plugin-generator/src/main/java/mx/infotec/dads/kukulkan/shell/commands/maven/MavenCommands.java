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
package mx.infotec.dads.kukulkan.shell.commands.maven;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.jline.utils.AttributedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.domain.Line;
import mx.infotec.dads.kukulkan.shell.domain.NativeCommandContext;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.services.CommandService;
import mx.infotec.dads.kukulkan.shell.util.TextFormatter;

/**
 * Docker Commands.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class MavenCommands {

    /** The Constant MVN_COMMAND. */
    public static final String MVN_COMMAND = "mvn";

    /** The project context. */
    @Autowired
    NativeCommandContext projectContext;

    /** The command service. */
    @Autowired
    CommandService commandService;

    @Autowired
    Navigator navigator;

    @Autowired
    ThreadPoolTaskExecutor executor;

    /**
     * Configurate a front end application, It execute the command "mvn package
     * -Pprod -DskiptTests".
     */
    @ShellMethod("Configurate a initial Archetype")
    public void configurateFrontEnd() {
        commandService.exec(new ShellCommand(MVN_COMMAND).addArg("package").addArg("-Pprod").addArg("-DskipTests"),
                line -> {
                    commandService.printf(TextFormatter.formatLogText(line).toAnsi());
                    return Optional.ofNullable(new Line(line));
                });
    }

    @ShellMethod("Run a Spring-Boot App")
    public void runApp() {
        executor.execute(() -> commandService.exec(new ShellCommand(MVN_COMMAND).addArg("spring-boot:run"), line -> {
            commandService.printf(TextFormatter.formatLogText(line).toAnsi());
            return Optional.ofNullable(new Line(line));
        }));
    }

    @ShellMethod("Stop a Process")
    public void stopProcess(@ShellOption(valueProvider = JavaProcessValueProvider.class) @NotNull String id) {
        commandService.exec(new ShellCommand("kill", "-9", id));
    }

    @ShellMethod("Stop an App")
    public void stopApp() {
        executor.destroy();
    }

    /**
     * Show the runnin apps.
     *
     * @return the list
     */
    @ShellMethod("Show running apps")
    public List<AttributedString> showProcess() {
        List<CharSequence> exec = commandService.exec(new ShellCommand("jps"));
        return TextFormatter.formatToGitOutput(exec);
    }

}
