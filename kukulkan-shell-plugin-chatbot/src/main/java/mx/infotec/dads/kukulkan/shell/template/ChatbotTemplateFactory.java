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
package mx.infotec.dads.kukulkan.shell.template;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * The chatbot template factory
 * 
 * @author Roberto Villarejo Martínez Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
 *
 */
public class ChatbotTemplateFactory {

	/** The whole list of chatbot templates */
	public static final List<String> CHATBOT_TEMPLATE_LIST;

	public static final String CHATBOT_TEMPLATE = "archetypes/chatbot";

	static {
		CHATBOT_TEMPLATE_LIST = ImmutableList.copyOf(getChatbotTemplates());
	}

	/**
	 * Instantiates a new template factory.
	 */
	private ChatbotTemplateFactory() {
	}

	/**
	 * 
	 * @return the whole list of chatbot templates
	 */
	public static List<String> getChatbotTemplates() {
		List<String> templates = new ArrayList<>();
		templates.addAll(getCommonChatbotTemplates());
		templates.addAll(getFacebookChatbotTemplates());
		templates.addAll(getWebChatbotTemplates());
		return templates;
	}

	/**
	 * 
	 * @return the common chatbot templates 
	 */
	public static List<String> getCommonChatbotTemplates() {
		List<String> templates = new ArrayList<>();
		templates.add(CHATBOT_TEMPLATE + "/index.js.ftl");
		templates.add(CHATBOT_TEMPLATE + "/README.md.ftl");
		templates.add(CHATBOT_TEMPLATE + "/Procfile");
		templates.add(CHATBOT_TEMPLATE + "/package.json.ftl");
		templates.add(CHATBOT_TEMPLATE + "/package-lock.json.ftl");
		templates.add(CHATBOT_TEMPLATE + "/README.md.ftl");
		templates.add(CHATBOT_TEMPLATE + "/.env.ftl");
		templates.add(CHATBOT_TEMPLATE + "/.gitignore.ftl");
		templates.add(CHATBOT_TEMPLATE + "/conversation/create-conversation.js.ftl");
		templates.add(CHATBOT_TEMPLATE + "/conversation/starter-conversation.js.ftl");
		templates.add(CHATBOT_TEMPLATE + "/web-server/express-server.js.ftl");
		templates.add(CHATBOT_TEMPLATE + "/web-server/routes.js.ftl");
		return templates;
	}

	/**
	 * 
	 * @return the webhook chatbot templates
	 */
	public static List<String> getWebhookChatbotTemplates() {
		List<String> templates = new ArrayList<>();
		templates.add(CHATBOT_TEMPLATE + "/fulfillment/webhook-fulfillment-middleware.js.ftl");
		return templates;
	}

	/**
	 * 
	 * @return the facebook chatbot templates
	 */
	public static List<String> getFacebookChatbotTemplates() {
		List<String> templates = new ArrayList<>();
		templates.add(CHATBOT_TEMPLATE + "/facebook/bot.js.ftl");
		templates.add(CHATBOT_TEMPLATE + "/facebook/menu.js.ftl");
		templates.add(CHATBOT_TEMPLATE + "/facebook/middlewares.js.ftl");
		templates.add(CHATBOT_TEMPLATE + "/facebook/skills.js.ftl");
		return templates;
	}

	/**
	 * 
	 * @return the web chatbot templates
	 */
	public static List<String> getWebChatbotTemplates() {
		List<String> templates = new ArrayList<>();
		templates.add(CHATBOT_TEMPLATE + "/web/public/css/styles.css");
		templates.add(CHATBOT_TEMPLATE + "/web/public/chat.html");
		templates.add(CHATBOT_TEMPLATE + "/web/public/client.js");
		templates.add(CHATBOT_TEMPLATE + "/web/bot.js.ftl");
		templates.add(CHATBOT_TEMPLATE + "/web/middlewares.js.ftl");
		templates.add(CHATBOT_TEMPLATE + "/web/routes.js.ftl");
		templates.add(CHATBOT_TEMPLATE + "/web/skills.js.ftl");
		return templates;
	}

}
