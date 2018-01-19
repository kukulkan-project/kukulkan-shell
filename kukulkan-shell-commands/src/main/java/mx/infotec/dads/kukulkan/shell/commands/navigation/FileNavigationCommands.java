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
package mx.infotec.dads.kukulkan.shell.commands.navigation;

import static mx.infotec.dads.kukulkan.shell.commands.navigation.FileNavigationHelper.calculateNewPath;
import static mx.infotec.dads.kukulkan.shell.commands.navigation.FileNavigationHelper.processActions;
import static mx.infotec.dads.kukulkan.shell.util.FilesCommons.showFiles;
import static mx.infotec.dads.kukulkan.shell.util.TextFormatter.formatDirNotExistText;
import static mx.infotec.dads.kukulkan.shell.util.TextFormatter.formatNormalText;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.jline.utils.AttributedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.event.message.EventType;
import mx.infotec.dads.kukulkan.shell.event.message.LocationUpdatedEvent;
import mx.infotec.dads.kukulkan.shell.services.CommandService;

/**
 * Docker Commands.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class FileNavigationCommands {

    /** The nav. */
    @Autowired
    private Navigator nav;

    /** The command service. */
    @Autowired
    CommandService commandService;

    /** The publisher. */
    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Pwd.
     *
     * @return the attributed string
     */
    @ShellMethod("Show the current direction")
    public AttributedString pwd() {
        return formatNormalText(nav.getCurrentPath().toString());
    }

    /**
     * Ls.
     *
     * @return the list
     */
    @ShellMethod(value = "List the currents files", key = { "ls", "ll", "dir" })
    public List<AttributedString> ls() {
        return showFiles(nav.getCurrentPath());
    }

    /**
     * Cd.
     *
     * @param dir
     *            the dir
     * @return the attributed string
     */
    @ShellMethod("Change location")
    public AttributedString cd(
            @ShellOption(valueProvider = DirectoryValueProvider.class, defaultValue = "@home") String dir) {
        Path newPath = calculateNewPath(dir, nav);
        if (newPath.toFile().exists()) {
            nav.setCurrentPath(newPath);
//            processLocationUpdateActions();
            publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
            return formatNormalText(newPath.toString());
        } else {
            return formatDirNotExistText(newPath.toString());
        }
    }
}
