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

package mx.infotec.dads.kukulkan.shell.services.impl;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import mx.infotec.dads.kukulkan.engine.templating.service.TemplateService;
import mx.infotec.dads.kukulkan.engine.util.ListFileUtil;
import mx.infotec.dads.kukulkan.engine.util.TemplateUtil;
import mx.infotec.dads.kukulkan.metamodel.util.FileUtil;
import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.services.WriterService;

/**
 * 
 * @author Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
 *
 */
@Service
public class WriterServiceImpl implements WriterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriterServiceImpl.class);

    private static final String ALL_FILES_PATTERN = ".*";

    private static final String TEMPLATES_DIR = "templates/";

    private TemplateService templateService;

    private Configuration fmConfiguration;

    private Navigator navigator;

    public WriterServiceImpl(TemplateService templateService, Navigator navigator, Configuration fmConfiguration) {
        this.templateService = templateService;
        this.fmConfiguration = fmConfiguration;
        this.navigator = navigator;
    }

    @Override
    public void copyTemplate(String template, String relative, Object model) {
        String content = templateService.fillTemplate(template, model);
        Path toSave = navigator.getCurrentPath().resolve(processString(relative, model));
        FileUtil.saveToFile(toSave, content);
    }

    @Override
    public void copy(String resource, String relative) {
        Path toSave = navigator.getCurrentPath().resolve(relative);
        FileUtil.copyFromJar(TEMPLATES_DIR + resource, toSave);
    }

    @Override
    public void copyDir(Class clazz, String directory, String relative) {
        copyDir(clazz, directory, ALL_FILES_PATTERN, relative);
    }

    @Override
    public void copyDir(Class clazz, String directory, String pattern, String relative) {
        List<String> files = ListFileUtil.listFiles(clazz, TEMPLATES_DIR + directory, pattern);
        for (String file : files) {
            FileUtil.copyFromJar(TEMPLATES_DIR + directory + "/" + file,
                    navigator.getCurrentPath().resolve(relative).resolve(file));
        }
    }

    @Override
    public void copyDir(Class clazz, String directory, String pattern, String relative, Object model) {
        String processed = processString(relative, model);
        copyDir(clazz, directory, pattern, processed);
    }

    @Override
    public void copyDir(Class clazz, String directory, String relative, Object model) {
        copyDir(clazz, directory, ALL_FILES_PATTERN, relative, model);
    }

    @Override
    public void copySmart(String resource, String toSave, Object model) {
        if (resource.endsWith(".ftl")) {
            copyTemplate(resource, toSave, model);
        } else {
            copy(resource, toSave);
        }
    }

    private String processString(String text, Object model) {
        Template template = null;
        try {
            template = new Template("tmp", new StringReader(text), fmConfiguration);
            return TemplateUtil.processTemplate(model, template);
        } catch (IOException e) {
            LOGGER.error("Error while processing {} as Template", text);
        }
        return text;
    }

    @Override
    public void copy(String resource, String toSave, Object model) {
        copy(resource, processString(toSave, model));
    }

}
