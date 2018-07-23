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

import java.util.function.Function;

import mx.infotec.dads.kukulkan.shell.services.WriterHelper;

public class ChatbotArchetypeWriter {

    private static final String CHATBOT_TEMPLATE = "archetypes/chatbot/";

    private ChatbotArchetypeWriter() {
    }

    public static void writeArchetype(WriterHelper writer, Object model) {
        final Function<String, String> pathBuilderChatbot = template -> template.replace(".ftl", "")
                .replace(CHATBOT_TEMPLATE, "${project.name}/");

        writer.copySmart(CHATBOT_TEMPLATE + "tslint.json.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "yarn.lock.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/constant/types.ts.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/controller/fulfillment.ts.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/installer.ts.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/configureApp.ts.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/service/jokeService.ts.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/service/chuckNorrisJokeService.ts.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/public/css/embed.css.map.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/public/css/styles.css.map.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/public/css/embed.css.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/public/css/biggerstyles.css.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/public/css/styles.css.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/public/chat.html.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/public/images/botkit_icon.png", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/public/sass/embed.scss", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/public/sass/_chat.scss", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/public/sass/_home.scss", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/public/sass/_botkit.scss", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/public/sass/styles.scss", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/public/javascripts/beep.mp3", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/public/javascripts/sent.mp3", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/public/javascripts/client.js.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/public/javascripts/embed.js.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/bots/facebook/skills.ts.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/bots/facebook/menuConfigurer.ts.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/bots/facebook/FacebookBotConfigurer.ts.ftl", pathBuilderChatbot,
                model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/bots/conversation/create-conversation.js.ftl", pathBuilderChatbot,
                model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/bots/conversation/starter-conversation.json.ftl", pathBuilderChatbot,
                model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/bots/common/middlewares.ts.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/bots/web/skills.ts.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/bots/web/WebBotConfigurer.ts.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/bots/console/consoleBot.js.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/bootstrap.ts.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/views/embed.hbs.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/views/layouts/default.hbs.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/views/index.hbs.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/views/partials/header.hbs.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/views/partials/head.hbs.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/views/partials/footer.hbs.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "src/utils/utils.ts.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "README.md.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "Procfile.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + ".env.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "package.json.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + "tsconfig.json.ftl", pathBuilderChatbot, model);
        writer.copySmart(CHATBOT_TEMPLATE + ".gitignore.ftl", pathBuilderChatbot, model);
    }

}
