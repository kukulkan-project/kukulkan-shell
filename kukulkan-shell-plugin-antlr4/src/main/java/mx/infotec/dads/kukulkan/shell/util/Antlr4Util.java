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
package mx.infotec.dads.kukulkan.shell.util;

import static mx.infotec.dads.kukulkan.metamodel.util.Validator.requiredNotEmpty;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.template.Template;
import mx.infotec.dads.kukulkan.metamodel.template.TemplateType;
import mx.infotec.dads.kukulkan.shell.generator.Antlr4Context;

/**
 * Antlr4Context
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class Antlr4Util {

    private Antlr4Util() {

    }

    public static Path createToSavePath(GeneratorContext context, Template template, Path outputPath) {
        Antlr4Context pConf = requiredNotEmpty(context.get(Antlr4Context.class));
        return createPath(template, pConf.getPackaging(), pConf.getId(), outputPath);
    }

    public static Path createPath(Template template, String packaging, String projectid, Path outputPath) {
        String newPackaging = packaging.replaceAll("\\.", "/");
        Path temp = Paths.get(template.getName());
        Path parent = temp.getParent();
        String newTemplate = createTemplatePath(projectid, newPackaging, parent, outputPath, template);
        Path targetPath = Paths.get(newTemplate, temp.getFileName().toString().replaceAll(".ftl", ""));
        return createOutputPath(projectid, targetPath);
    }

    public static String createTemplatePath(String projectid, String newPackaging, Path parent, Path outputPath,
            Template template) {
        return parent.toString().replaceAll(template.getType().getTemplatePath(), outputPath + "/" + projectid)
                .replaceAll("package", newPackaging);
    }

    public static Path createOutputPath(String projectid, Path targetPath) {
        Objects.requireNonNull(projectid, "project id cannot be null");
        Objects.requireNonNull(targetPath, "targetPath cannot be null");
        if (targetPath.getFileName().toString().contains("MyGrammar.g4")) {
            String output = projectid.substring(0, 1).toUpperCase() + projectid.substring(1);
            return Paths.get(targetPath.getParent().toString(), output + ".g4");
        } else if (targetPath.getFileName().toString().contains("MyGrammarCustomVisitor")) {
            String output = projectid.substring(0, 1).toUpperCase() + projectid.substring(1);
            return Paths.get(targetPath.getParent().toString(), output + "CustomVisitor.java");
        } else {
            return targetPath;
        }
    }

    public static List<Template> convertTemplate(TemplateType type, List<String> from) {
        List<Template> to = new ArrayList<>();
        for (String template : from) {
            to.add(new Template(type, template));
        }
        return to;
    }

}
