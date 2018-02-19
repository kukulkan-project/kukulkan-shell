package mx.infotec.dads.kukulkan.shell.generator;
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

import static mx.infotec.dads.kukulkan.metamodel.util.StringFormater.replaceDotByFileSeparator;
import static mx.infotec.dads.kukulkan.metamodel.util.Validator.requiredNotEmpty;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import mx.infotec.dads.kukulkan.engine.templating.service.TemplateService;
import mx.infotec.dads.kukulkan.engine.util.TemplateUtil;
import mx.infotec.dads.kukulkan.metamodel.annotation.GeneratorComponent;
import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.generator.Generator;
import mx.infotec.dads.kukulkan.metamodel.template.TemplateInfo;
import mx.infotec.dads.kukulkan.metamodel.template.TemplateType;
import mx.infotec.dads.kukulkan.metamodel.util.FileUtil;
import mx.infotec.dads.kukulkan.metamodel.util.PathProcessor;
import mx.infotec.dads.kukulkan.shell.template.TemplateFactory;

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

    @Override
    public String getName() {
        return "antlr4";
    }

    @Override
    public void process(GeneratorContext context) {
        Antlr4Context antlrContext = requiredNotEmpty(context.get(Antlr4Context.class));
        Map<String, Object> model = new HashMap<>();
        model.put("project", requiredNotEmpty(context.get(Antlr4Context.class)));
        for (TemplateInfo template : TemplateUtil.convertToTemplateInfoList(TemplateType.ANTLR4,
                TemplateFactory.ANTLR4_TEMPLATE_LIST)) {
            String content = templateService.fillTemplate(template.getStringPath(), model);
            FileUtil.saveToFile(createOutputPath(antlrContext, template), content);
        }
    }

    /**
     * @param antlrContext
     * @param template
     */
    private Path createOutputPath(Antlr4Context antlrContext, TemplateInfo template) {
        return PathProcessor.forPath(template.getStringPath()).replaceRegExp("archetypes[\\/]antlr4", "")
                .joinBefore(antlrContext.getId()).joinBefore(antlrContext.getOutputDir())
                .replaceLiteral("package", replaceDotByFileSeparator(antlrContext.getPackaging()))
                .replaceRegExp(".ftl", "").getAbsolutePath();
    }
}
