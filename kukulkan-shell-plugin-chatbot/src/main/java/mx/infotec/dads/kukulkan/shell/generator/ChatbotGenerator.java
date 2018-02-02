/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2018 Roberto Villarejo Martínez
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

import java.nio.file.Path;
import static mx.infotec.dads.kukulkan.metamodel.util.Validator.requiredNotEmpty;

import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import edu.emory.mathcs.backport.java.util.Collections;
import mx.infotec.dads.kukulkan.engine.templating.service.TemplateService;
import mx.infotec.dads.kukulkan.metamodel.annotation.GeneratorComponent;
import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.generator.Generator;
import mx.infotec.dads.kukulkan.metamodel.generator.Layer;
import mx.infotec.dads.kukulkan.metamodel.util.FileUtil;
import mx.infotec.dads.kukulkan.shell.template.ChatbotTemplateFactory;

/**
 * Generator for Angular 1.5.8, Spring boot and Spring Data
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@GeneratorComponent
public class ChatbotGenerator implements Generator {

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
		return "chatbot-generator";
	}

	@Override
	public void process(GeneratorContext context) {
		ChatbotContext chatbotCtx = requiredNotEmpty(context.get(ChatbotContext.class));
		Map<String, Object> model = new HashMap<>();
		model.put("project", chatbotCtx);
		for (String template : ChatbotTemplateFactory.CHATBOT_TEMPLATE_LIST) {
			String content = templateService.fillTemplate(template, model);
			Path toSave = Paths.get(chatbotCtx.getOutputDir().toString(),
					template.replaceAll(ChatbotTemplateFactory.CHATBOT_TEMPLATE, "").replaceAll(".ftl", ""));
			FileUtil.saveToFile(toSave, content);
		}

	}

}
