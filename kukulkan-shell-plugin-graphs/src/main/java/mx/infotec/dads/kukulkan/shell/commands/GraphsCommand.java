/*
 *
 * The MIT License (MIT)
 * Copyright (c) 2018
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
package mx.infotec.dads.kukulkan.shell.commands;

import javax.validation.Valid;

import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.shell.util.MapperG;
import mx.infotec.dads.kukulkan.shell.util.ProjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.shell.generator.GraphsContext;
import mx.infotec.dads.kukulkan.shell.generator.GraphsGenerator;
import mx.infotec.dads.kukulkan.shell.generator.GraphType;

import java.util.Optional;

/**
 * Graphs Command
 */
@ShellComponent
public class GraphsCommand extends AbstractCommand {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GraphsCommand.class);

    @Autowired
    private GraphsGenerator generator;
    private GraphType graphTypes;

    /**
     * Command Shell that show the current project configuration applied to the
     * current context.
     */
    @ShellMethod("Create Graphs plugin ")
    public void graphsPlugin(@ShellOption(optOut = true) @Valid GraphsArgs params,
                             @ShellOption(valueProvider = GraphsValueProvider.class) String graphs) {
        LOGGER.info("Creating Graphs...");

        GraphsContext graphsContext = MapperG.toContext(params);
        graphsContext.setOutputDir(navigator.getCurrentPath());
        graphsContext.setGraphs(graphs);

        Optional<ProjectConfiguration> project = ProjectUtil.readKukulkanFile(graphsContext.getOutputDir());
        if (!project.isPresent())
        {
            LOGGER.error("This folder does not contain a kukulkan project");
            System.out.println("This folder does not contain a kukulkan project");
            return;
        }
        GeneratorContext genCtx = new GeneratorContext();
        genCtx.put(GraphsContext.class, graphsContext);
        generator.process(genCtx);


    }
}
