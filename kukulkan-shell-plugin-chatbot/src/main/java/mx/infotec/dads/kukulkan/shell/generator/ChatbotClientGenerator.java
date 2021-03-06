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

import static mx.infotec.dads.kukulkan.metamodel.util.Validator.requiredNotEmpty;
import static mx.infotec.dads.kukulkan.shell.generator.ChatbotResourcesWriter.writeChatbotClient;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import freemarker.template.Configuration;
import mx.infotec.dads.kukulkan.engine.templating.service.TemplateService;
import mx.infotec.dads.kukulkan.metamodel.annotation.GeneratorComponent;
import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.generator.Generator;
import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.services.PrintService;
import mx.infotec.dads.kukulkan.shell.services.WriterHelper;

@GeneratorComponent
public class ChatbotClientGenerator implements Generator {

    @Autowired
    WriterHelper writer;

    @Autowired
    PrintService printer;

    @Autowired
    Configuration config;

    @Autowired
    TemplateService templateService;

    @Autowired
    Navigator nav;

    @Override
    public String getName() {
        return "chatbot-client-generator";
    }

    @Override
    public void process(GeneratorContext context) {
        ProjectConfiguration projectConfig = requiredNotEmpty(context.get(ProjectConfiguration.class));
        Map<String, Object> model = new HashMap<>();
        model.put("project", projectConfig);
        model.put("chatbotUrl", context.get("chatbotUrl").get());
        writeChatbotClient(writer, model);
        addCssStyles(model);
    }

    public void addCssStyles(Map<String, Object> model) {
        String stylesTemplate = ChatbotResourcesWriter.CHATBOT_CLIENT_TEMPLATE
                + "src/main/webapp/content/css/chatbot-styles.css";
        writer.rewriteFile(stylesTemplate, "src/main/webapp/content/css/main.css", model,
                "jhipster-needle-css-add-main");
    }

}
