package mx.infotec.dads.kukulkan.shell.commands.antlr4;
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

import static mx.infotec.dads.kukulkan.metamodel.util.Validator.requiredNotEmpty;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import mx.infotec.dads.kukulkan.engine.templating.service.TemplateService;
import mx.infotec.dads.kukulkan.metamodel.template.Template;
import mx.infotec.dads.kukulkan.metamodel.template.TemplateType;
import mx.infotec.dads.kukulkan.metamodel.util.FileUtil;
import mx.infotec.dads.kukulkan.shell.commands.util.TemplateFactory;
import mx.infotec.dads.kukulkan.metamodel.annotation.GeneratorComponent;
import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.generator.Generator;
import mx.infotec.dads.kukulkan.metamodel.generator.Layer;

/**
 * Generator for Angular 1.5.8, Spring boot and Spring Data
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@GeneratorComponent
public class Antlr4Generator implements Generator {

    /** The template service. */
    @Autowired
    private TemplateService templateService;

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.generator.Generator#getName()
     */
    @Override
    public String getName() {
        return "antlr4";
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.generator.Generator#getVersion()
     */
    @Override
    public String getVersion() {
        return "1.0.0";
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.metamodel.generator.Generator#getDescription()
     */
    @Override
    public String getDescription() {
        return "Angular 1.5.8 and Spring boot application";
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.generator.Generator#getKeywords()
     */
    @Override
    public String getKeywords() {
        return null;
    }

    @Override
    public void process(GeneratorContext context) {
        Antlr4Params antlrContext = requiredNotEmpty(context.get(Antlr4Params.class));
        Map<String, Object> model = new HashMap<>();
        model.put("grammar", requiredNotEmpty(context.get(Antlr4Params.class)));
        // templateService.fillTemplate(templateRelativePath, model);
        for (Template template : getTemplatesToProcess()) {
            Path toSave = createToSavePath(context, template, antlrContext.getOutputDir());
            processTemplate(context, model, template, toSave);
        }
    }

    private void processTemplate(GeneratorContext context, Map<String, Object> propertiesMap, Template template,
            Path toSave) {
        String content = templateService.fillTemplate(template.getName(), propertiesMap);
        FileUtil.saveToFile(toSave, content);
    }

    private Path createToSavePath(GeneratorContext context, Template template, Path outputPath) {
        Antlr4Params pConf = requiredNotEmpty(context.get(Antlr4Params.class));
        return createPath(template, pConf.getPackaging(), pConf.getId(), outputPath);
    }

    private Path createPath(Template template, String packaging, String projectid, Path outputPath) {
        String newPackaging = packaging.replaceAll("\\.", "/");
        Path temp = Paths.get(template.getName());
        Path parent = temp.getParent();
        String newTemplate = createTemplatePath(projectid, newPackaging, parent, outputPath, template);
        Path targetPath = Paths.get(newTemplate, temp.getFileName().toString().replaceAll(".ftl", ""));
        return createOutputPath(projectid, targetPath);
    }

    private String createTemplatePath(String projectid, String newPackaging, Path parent, Path outputPath,
            Template template) {
        return parent.toString().replaceAll(template.getType().getTemplatePath(), outputPath + "/" + projectid)
                .replaceAll("package", newPackaging);
    }

    private Path createOutputPath(String projectid, Path targetPath) {
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

    private List<Template> getTemplatesToProcess() {
        List<Template> templateList = new ArrayList<>();
        templateList.addAll(convertTemplate(TemplateType.ANTLR4, TemplateFactory.ANTLR4_TEMPLATE_LIST));
        return templateList;
    }

    private static List<Template> convertTemplate(TemplateType type, List<String> from) {
        List<Template> to = new ArrayList<>();
        for (String template : from) {
            to.add(new Template(type, template));
        }
        return to;
    }

    @Override
    public Collection<? extends Layer> getLayers() {
        // TODO Auto-generated method stub
        return null;
    }

}
