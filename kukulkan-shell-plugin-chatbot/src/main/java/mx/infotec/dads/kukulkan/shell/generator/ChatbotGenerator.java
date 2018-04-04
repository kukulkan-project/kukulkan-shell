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
package mx.infotec.dads.kukulkan.shell.generator;

import static mx.infotec.dads.kukulkan.metamodel.util.Validator.requiredNotEmpty;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.infotec.dads.kukulkan.metamodel.annotation.GeneratorComponent;
import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.generator.Generator;
import mx.infotec.dads.kukulkan.shell.services.WriterService;

/**
 * Generator for Chatbot
 * 
 * @author Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
 *
 */
@GeneratorComponent
public class ChatbotGenerator implements Generator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatbotGenerator.class);

    private static final String CHATBOT_ARCHETYPE = "archetypes/chatbot/";

    private WriterService writer;

    public ChatbotGenerator(WriterService writer) {
        this.writer = writer;
    }

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

        // Common files
        writer.copy(CHATBOT_ARCHETYPE + "yarn.lock", "yarn.lock");
        writer.copyTemplate(CHATBOT_ARCHETYPE + "README.md.ftl", "README.md", model);
        writer.copy(CHATBOT_ARCHETYPE + "Procfile", "Procfile");
        writer.copyTemplate(CHATBOT_ARCHETYPE + "package.json.ftl", "package.json", model);
        writer.copy(CHATBOT_ARCHETYPE + "app.js", "app.js");
        writer.copy(CHATBOT_ARCHETYPE + ".gitignore", ".gitignore");
        writer.copyTemplate(CHATBOT_ARCHETYPE + ".env.ftl", ".env", model);
        writer.copyDir(ChatbotGenerator.class, CHATBOT_ARCHETYPE + "views", "views");
        writer.copyDir(ChatbotGenerator.class, CHATBOT_ARCHETYPE + "routes", "routes");
        writer.copyDir(ChatbotGenerator.class, CHATBOT_ARCHETYPE + "public", "public");
        writer.copy(CHATBOT_ARCHETYPE + "bin/www", "bin/www");
        writer.copyTemplate(CHATBOT_ARCHETYPE + "bots/index.js.ftl", "bots/index.js", model);
        
        // Facebook bot
        if (chatbotCtx.isFacebookBot()) {
            writer.copyTemplate(CHATBOT_ARCHETYPE + "bots/facebook/controller.js.ftl", "bots/facebook/controller.js", model);
            writer.copyTemplate(CHATBOT_ARCHETYPE + "bots/facebook/menu.js.ftl", "bots/facebook/menu.js", model);
            writer.copyTemplate(CHATBOT_ARCHETYPE + "bots/facebook/middlewares.js.ftl", "bots/facebook/middlewares.js", model);
            writer.copyTemplate(CHATBOT_ARCHETYPE + "bots/facebook/skills.js.ftl", "bots/facebook/skills.js", model);
        }

        // Web bot
        if (chatbotCtx.isWebBot()) {
            writer.copyTemplate(CHATBOT_ARCHETYPE + "bots/web/controller.js.ftl", "bots/web/controller.js", model);
            writer.copyTemplate(CHATBOT_ARCHETYPE + "bots/web/middlewares.js.ftl", "bots/web/middlewares.js", model);
            writer.copyTemplate(CHATBOT_ARCHETYPE + "bots/web/skills.js.ftl", "bots/web/skills.js", model);
        }

        // DialogFlow conversation scripts
        if (chatbotCtx.getNlpService().equals(NlpService.DIALOGFLOW)) {
            writer.copyTemplate(CHATBOT_ARCHETYPE + "bots/conversation/create-conversation.js.ftl", "bots/conversation/create-conversation.js", model);
            writer.copyTemplate(CHATBOT_ARCHETYPE + "bots/conversation/starter-conversation.js.ftl", "bots/conversation/starter-conversation.js", model);
        }

        LOGGER.info("Finished!");
        LOGGER.info("Run `npm install` to install dependencies");
        LOGGER.info("Run `npm start` to run the project");
    }

}
