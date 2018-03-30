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
package mx.infotec.dads.kukulkan.shell.commands.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import mx.infotec.dads.kukulkan.shell.commands.AbstractCommand;
import mx.infotec.dads.kukulkan.shell.commands.navigation.DirectoryValueProvider;
import mx.infotec.dads.kukulkan.shell.component.Navigator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import mx.infotec.dads.kukulkan.shell.services.CommandService;
import mx.infotec.dads.kukulkan.shell.util.AnsiConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellOption;
import org.unix4j.Unix4j;
import org.unix4j.builder.Unix4jCommandBuilder;
import org.unix4j.io.Output;
import org.unix4j.io.StreamOutput;
import org.unix4j.line.Line;
import org.unix4j.unix.Cut;
import org.unix4j.unix.Ls;
import org.unix4j.unix.Sort;
import org.unix4j.unix.grep.GrepOption;
import org.unix4j.util.Range;
import org.unix4j.variable.Arg;

/**
 * Util Commands.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class FileCommands extends AbstractCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileCommands.class);

    /**
     * The command service.
     */
    @Autowired
    private CommandService commandService;

    /**
     * The navigator service.
     */
    @Autowired
    private Navigator nav;

    /**
     * Ping.
     *
     * @param host
     *            the host
     */
    @ShellMethod("make a ping to a host")
    public List<Line> lsDump(String param) {
        List<Line> lineList = Unix4j.ls(Ls.Options.l.a, nav.getCurrentPath().toString()).toLineList();
        for (Line line : lineList) {

        }
        return null;
    }

    @ShellMethod("make a ping to a host")
    public void mv(@ShellOption(valueProvider = FileLocationValueProvider.class) String from,
            @ShellOption(valueProvider = FileLocationValueProvider.class) String to) throws IOException {
        Files.move(Paths.get(from), Paths.get(to), StandardCopyOption.REPLACE_EXISTING);
    }
}
