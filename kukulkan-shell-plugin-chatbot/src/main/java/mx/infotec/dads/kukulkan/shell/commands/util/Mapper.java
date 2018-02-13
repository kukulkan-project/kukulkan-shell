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
package mx.infotec.dads.kukulkan.shell.commands.util;

import mx.infotec.dads.kukulkan.shell.commands.chatbot.ChatbotArgs;
import mx.infotec.dads.kukulkan.shell.generator.ChatbotContext;
import mx.infotec.dads.kukulkan.shell.generator.DialogFlowConfig;
import mx.infotec.dads.kukulkan.shell.generator.FacebookBotConfig;
import mx.infotec.dads.kukulkan.shell.generator.WebhookConfig;

public class Mapper {
	
	private Mapper() {
	}

	public static ChatbotContext to(ChatbotArgs params) {
		ChatbotContext ctx = new ChatbotContext();
		ctx.setName(params.getAppName());
		ctx.setPage(params.getAppPage());
		ctx.setAuthor(params.getAuthor());
		ctx.setDescription(params.getDescription());
		ctx.setDialogflowConfig(toDialogFlowConfig(params));
		ctx.setFacebookBot(params.isFacebookBot());
		ctx.setFacebookBotConfig(toFacebookBotConfig(params));
		ctx.setWebBot(!params.isNoWebBot());
		ctx.setGitRepository(params.getGitRepository());
		ctx.setLicense(params.getLicense());
		ctx.setNlpService(params.getNlpService());
		ctx.setPort(params.getPort());
		ctx.setWebhook(params.isWebhook());
		ctx.setWebhookConfig(toWebhookConfig(params));
		return ctx;
	}

	public static DialogFlowConfig toDialogFlowConfig(ChatbotArgs params) {
		DialogFlowConfig dialogflowConfig = new DialogFlowConfig();
		dialogflowConfig.setClientToken(params.getDfClientToken());
		dialogflowConfig.setDeveloperToken(params.getDfDeveloperToken());
		return dialogflowConfig;
	}

	public static FacebookBotConfig toFacebookBotConfig(ChatbotArgs params) {
		FacebookBotConfig fbBotConfig = new FacebookBotConfig();
		fbBotConfig.setAccessToken(params.getFbAccessToken());
		fbBotConfig.setVerifyToken(params.getFbVerifyToken());
		fbBotConfig.setMenu(!params.isNoFbMenu());
		return fbBotConfig;
	}

	public static WebhookConfig toWebhookConfig(ChatbotArgs params) {
		WebhookConfig webhookConfig = new WebhookConfig();
		webhookConfig.setEndpoint(params.getEndpointWebhook());
		return webhookConfig;
	}

}
