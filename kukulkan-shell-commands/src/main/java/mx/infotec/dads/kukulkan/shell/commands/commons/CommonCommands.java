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
package mx.infotec.dads.kukulkan.shell.commands.commons;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import mx.infotec.dads.kukulkan.shell.domain.AboutInfo;
import mx.infotec.dads.kukulkan.shell.domain.NativeCommandContext;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.services.CommandService;

/**
 * Docker Commands.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class CommonCommands {

    /** The context. */
    @Autowired
    private NativeCommandContext context;
    
    /** The terminal. */
    @Autowired
    @Lazy
    private Terminal terminal;

    /** The command service. */
    @Autowired
    CommandService commandService;

    /**
     * Native commands.
     */
    @ShellMethod("Show the status of the common commands")
    public void nativeCommands() {
        context.getAvailableCommands()
                .forEach((key, cmd) -> commandService.printf(cmd.getCommand(), Boolean.toString(cmd.isActive())));
    }

    /**
     * Test.
     *
     * @return the about info
     */
    @ShellMethod("Show the status of the common commands")
    public AboutInfo test() {
        return new AboutInfo("server", "102");
    }

    /**
     * Native cmd.
     *
     * @param cmd the cmd
     * @return the list
     */
    @ShellMethod("Run a native Command")
    public List<CharSequence> nativeCmd(@NotNull String cmd) {
        return commandService.exec(new ShellCommand(cmd));
    }

}
