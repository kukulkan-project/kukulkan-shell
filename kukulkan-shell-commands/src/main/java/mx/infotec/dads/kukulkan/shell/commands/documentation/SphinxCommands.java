/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2018 Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
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

package mx.infotec.dads.kukulkan.shell.commands.documentation;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import mx.infotec.dads.kukulkan.engine.service.FileUtil;
import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.domain.NativeCommand;
import mx.infotec.dads.kukulkan.shell.domain.NativeCommandContext;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.services.CommandService;

/**
 * SphinxCommands
 * 
 * @author Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
 *
 */
@ShellComponent
public class SphinxCommands {

    private static final Logger LOGGER = LoggerFactory.getLogger(SphinxCommands.class);

    /** The command service. */
    @Autowired
    CommandService commandService;

    /** The Constant SPHINX_COMMAND. */
    public static final String SPHINX_COMMAND = "sphinx-quickstart";

    /** The project context. */
    @Autowired
    NativeCommandContext projectContext;

    /** The navigator */
    @Autowired
    Navigator navigator;

    @ShellMethod("Generate a documentation static site")
    @ShellMethodAvailability({ "sphinxAvailability" })
    public void initDocs(@NotNull @ShellOption(defaultValue = "KukulkanProject") String project,
            @ShellOption(defaultValue = "Kukulkan") String author, @ShellOption(defaultValue = "1.0") String version,
            @ShellOption(defaultValue = "1.0") String release, @ShellOption(defaultValue = "es") String lang) {
        try {
            copyResources();
            ShellCommand command = getSphinxCommand(project, author, version, release, lang);
            commandService.exec(command);
            commandService.exec(navigator.getCurrentPath().resolve("docs"), new ShellCommand("make", "html"));
            LOGGER.info("Your documentation site has been generated in docs/build/html");
            LOGGER.info("Use your favorite browser to open the docs/build/html/index.html file");
            LOGGER.info("Edit the Markdown files (.md) placed in /docs/source ");
            LOGGER.info("Run `make html` in docs folder every time you edit the content");
        } catch (IOException ex) {
            LOGGER.error("Error while copying resources");
        }

    }

    public Availability sphinxAvailability() {
        NativeCommand sphinxCmd = projectContext.getAvailableCommands().get(SPHINX_COMMAND);
        if (sphinxCmd != null && sphinxCmd.isActive()) {
            return Availability.available();
        } else {
            return Availability.unavailable("you must install " + SPHINX_COMMAND);
        }
    }

    private ShellCommand getSphinxCommand(String project, String author, String version, String release,
            String lang) {
        return new ShellCommand(SPHINX_COMMAND, "--quiet", "--sep", "--project", project, "--author", author, "-v",
                version, "--release", release, "--language", lang, "--makefile", "--batchfile", "-t",
                navigator.getCurrentPath().toString() + "/docs/template-DADS",
                navigator.getCurrentPath().toString() + "/docs");
    }

    private void copyResources() throws IOException {
        for (String template : TemplateDocs.DOCS_TEMPLATE_LIST) {
            LOGGER.info("Created: {}", template);
            InputStream in = getClass().getClassLoader().getResourceAsStream(template);
            Path target = Paths.get(navigator.getCurrentPath().toString(), template);
            FileUtil.createDirectories(target);
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

}
