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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import mx.infotec.dads.kukulkan.shell.commands.AbstractCommand;

/**
 * Util Commands.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class FileCommands extends AbstractCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileCommands.class);

    @ShellMethod("Move or rename a file to a target file.")
    public void mv(@ShellOption(valueProvider = FileLocationValueProvider.class) String from,
            @ShellOption(valueProvider = FileLocationValueProvider.class) String to) throws IOException {
        Files.move(Paths.get(from), Paths.get(to), StandardCopyOption.REPLACE_EXISTING);
    }

    @ShellMethod("make a ping to a host")
    public void rename(@ShellOption(valueProvider = FileNameProvider.class) String file) throws IOException {
        System.out.println("file Name: " + file.toString());
    }

    @ShellMethod("make a ping to a host")
    public void createCsv(@ShellOption(valueProvider = FileNameProvider.class) String file) throws IOException {
        ListFile listFiles = new ListFile();
        Files.walkFileTree(navigator.getCurrentPath(), listFiles);
        saveCsv(listFiles.getData());
    }

    private void saveCsv(List<String> data) {
        // TODO Auto-generated method stub
    }
}
