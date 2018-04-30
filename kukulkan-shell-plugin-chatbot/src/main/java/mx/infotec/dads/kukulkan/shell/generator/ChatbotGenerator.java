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
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.infotec.dads.kukulkan.metamodel.annotation.GeneratorComponent;
import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.generator.Generator;
import mx.infotec.dads.kukulkan.shell.services.WriterHelper;

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

    private WriterHelper writer;

    public ChatbotGenerator(WriterHelper writer) {
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

        Function<String, String> pathBuilder = template -> template.replace(".ftl", "").replace(CHATBOT_ARCHETYPE, "");

        // Common files
        writer.copySmart(CHATBOT_ARCHETYPE + "yarn.lock", pathBuilder, model);
        writer.copySmart(CHATBOT_ARCHETYPE + "README.md.ftl", pathBuilder, model);
        writer.copySmart(CHATBOT_ARCHETYPE + "Procfile", pathBuilder, model);
        writer.copySmart(CHATBOT_ARCHETYPE + "package.json.ftl", pathBuilder, model);
        writer.copySmart(CHATBOT_ARCHETYPE + "app.js", pathBuilder, model);
        writer.copySmart(CHATBOT_ARCHETYPE + ".gitignore", pathBuilder, model);
        writer.copySmart(CHATBOT_ARCHETYPE + ".env.ftl", pathBuilder, model);
        writer.copyDir(ChatbotGenerator.class, CHATBOT_ARCHETYPE + "views", "views");
        writer.copyDir(ChatbotGenerator.class, CHATBOT_ARCHETYPE + "routes", "routes");
        writer.copyDir(ChatbotGenerator.class, CHATBOT_ARCHETYPE + "public", "public");
        writer.copySmart(CHATBOT_ARCHETYPE + "bin/www", pathBuilder, model);
        writer.copySmart(CHATBOT_ARCHETYPE + "bots/index.js.ftl", pathBuilder, model);

        // Facebook bot
        if (chatbotCtx.isFacebookBot()) {
            writer.copySmart(CHATBOT_ARCHETYPE + "bots/facebook/controller.js.ftl", pathBuilder, model);
            writer.copySmart(CHATBOT_ARCHETYPE + "bots/facebook/menu.js.ftl", pathBuilder, model);
            writer.copySmart(CHATBOT_ARCHETYPE + "bots/facebook/middlewares.js.ftl", pathBuilder, model);
            writer.copySmart(CHATBOT_ARCHETYPE + "bots/facebook/skills.js.ftl", pathBuilder, model);
        }

        // Web bot
        if (chatbotCtx.isWebBot()) {
            writer.copySmart(CHATBOT_ARCHETYPE + "bots/web/controller.js.ftl", pathBuilder, model);
            writer.copySmart(CHATBOT_ARCHETYPE + "bots/web/middlewares.js.ftl", pathBuilder, model);
            writer.copySmart(CHATBOT_ARCHETYPE + "bots/web/skills.js.ftl", pathBuilder, model);
        }

        // DialogFlow conversation scripts
        if (chatbotCtx.getNlpService().equals(NlpService.DIALOGFLOW)) {
            writer.copySmart(CHATBOT_ARCHETYPE + "bots/conversation/create-conversation.js.ftl", pathBuilder, model);
            writer.copySmart(CHATBOT_ARCHETYPE + "bots/conversation/starter-conversation.js.ftl", pathBuilder, model);
        }

        LOGGER.info("Finished!");
        LOGGER.info("Run `npm install` to install dependencies");
        LOGGER.info("Run `npm start` to run the project");
    }

}
