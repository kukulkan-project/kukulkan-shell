/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2018 Roberto Villarejo Martínez <robertovillarejom@gmail.com>
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

package mx.infotec.dads.kukulkan.shell.generator;

import static mx.infotec.dads.kukulkan.metamodel.util.Validator.requiredNotEmpty;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.infotec.dads.kukulkan.engine.templating.service.TemplateService;
import mx.infotec.dads.kukulkan.metamodel.annotation.GeneratorComponent;
import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.generator.Generator;
import mx.infotec.dads.kukulkan.metamodel.util.FileUtil;
import mx.infotec.dads.kukulkan.shell.template.DslProjectTemplateFactory;

@GeneratorComponent
public class DslProjectGenerator implements Generator {

	/** The template service. */
	@Autowired
	private TemplateService templateService;

	private static final Logger LOGGER = LoggerFactory.getLogger(DslProjectGenerator.class);

	@Override
	public String getName() {
		return "dsl-generator";
	}

	@Override
	public void process(GeneratorContext context) {
		DslProjectContext dslContext = requiredNotEmpty(context.get(DslProjectContext.class));
		Map<String, Object> model = new HashMap<>();
		model.put("project", dslContext);
		LOGGER.info("Generating {} DSL project", dslContext.getName());

		// Filling extension templates and save
		for (String template : DslProjectTemplateFactory.getExtensionTemplates()) {
			String content = templateService.fillTemplate(template, model);
			Path toSave = Paths.get(dslContext.getOutputDir().toString(),
					template.replaceAll(DslProjectTemplateFactory.DSL_TEMPLATE, "").replaceAll(".ftl", "")
							.replaceAll("name", dslContext.getName()));
			FileUtil.saveToFile(toSave, content);
		}

		// Filling root templates and save
		for (String template : DslProjectTemplateFactory.getRootTemplates()) {
			String content = templateService.fillTemplate(template, model);
			Path toSave = Paths.get(dslContext.getOutputDir().toString(),
					template.replaceAll(DslProjectTemplateFactory.DSL_TEMPLATE, "").replaceAll(".ftl", ""));
			FileUtil.saveToFile(toSave, content);
		}

		// Copying resources
		for (String resource : DslProjectTemplateFactory.getDslProjectResources()) {
			FileUtil.copyFromJar(resource, Paths.get(dslContext.getOutputDir().toString(),
					resource.replaceAll(DslProjectTemplateFactory.DSL_TEMPLATE, "")));
		}
	}

}
