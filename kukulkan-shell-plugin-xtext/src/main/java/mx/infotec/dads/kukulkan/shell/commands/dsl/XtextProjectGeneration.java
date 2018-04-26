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

package mx.infotec.dads.kukulkan.shell.commands.dsl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.shell.commands.AbstractCommand;
import mx.infotec.dads.kukulkan.shell.commands.util.XtextContextMapper;
import mx.infotec.dads.kukulkan.shell.generator.XtextProjectContext;
import mx.infotec.dads.kukulkan.shell.generator.XtextProjectGenerator;

/**
 * 
 * @author Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
 *
 */
@ShellComponent
public class XtextProjectGeneration extends AbstractCommand {

    @Autowired
    private XtextProjectGenerator generator;

    private static final Logger LOGGER = LoggerFactory.getLogger(XtextProjectGenerator.class);

    @ShellMethod("Generates a Xtext project")
    public void xtextProject(String name, String packaging, String extension) {
        XtextProjectContext context = XtextContextMapper.toXtextContext(name, packaging, extension);
        context.setOutputDir(navigator.getCurrentPath());
        GeneratorContext genContext = new GeneratorContext();
        genContext.put(XtextProjectContext.class, context);
        genContext.put("name", "EXTERNO");
        generator.process(genContext);
    }

}
