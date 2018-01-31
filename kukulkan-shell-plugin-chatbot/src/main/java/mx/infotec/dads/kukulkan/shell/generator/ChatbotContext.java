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

public class ChatbotContext {
	
	private Path outputDir;

	private String name;

	private String description;

	private String author;

	private int port;

	private boolean webhook;

	private WebhookConfig webhookConfig;

	private boolean facebookBot;

	private FacebookBotConfig facebookBotConfig;
	
	private boolean webBot;

	private String page;

	private NlpService nlpService;

	private DialogFlowConfig dialogflowConfig;

	private String gitRepository;

	private License license;

	public Path getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(Path outputDir) {
		this.outputDir = outputDir;
	}

	public String getName() {
		return name;
	}

	public void setName(String appName) {
		this.name = appName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isWebhook() {
		return webhook;
	}

	public void setWebhook(boolean webhook) {
		this.webhook = webhook;
	}

	public WebhookConfig getWebhookConfig() {
		return webhookConfig;
	}

	public void setWebhookConfig(WebhookConfig webhookConfig) {
		this.webhookConfig = webhookConfig;
	}

	public boolean isFacebookBot() {
		return facebookBot;
	}

	public void setFacebookBot(boolean facebookBot) {
		this.facebookBot = facebookBot;
	}

	public FacebookBotConfig getFacebookBotConfig() {
		return facebookBotConfig;
	}

	public void setFacebookBotConfig(FacebookBotConfig facebookBotConfig) {
		this.facebookBotConfig = facebookBotConfig;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String appPage) {
		this.page = appPage;
	}

	public NlpService getNlpService() {
		return nlpService;
	}

	public void setNlpService(NlpService nlpService) {
		this.nlpService = nlpService;
	}

	public DialogFlowConfig getDialogflowConfig() {
		return dialogflowConfig;
	}

	public void setDialogflowConfig(DialogFlowConfig dialogflowConfig) {
		this.dialogflowConfig = dialogflowConfig;
	}

	public String getGitRepository() {
		return gitRepository;
	}

	public void setGitRepository(String gitRepository) {
		this.gitRepository = gitRepository;
	}

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}
	
	public boolean isWebBot() {
		return webBot;
	}

	public void setWebBot(boolean webBot) {
		this.webBot = webBot;
	}

	@Override
	public String toString() {
		return "ChatbotContext{" +
	            "appName='" + name + '\'' +
	            ", description='" + description + '\'' +
	            ", author='" + author + '\'' +
	            ", port='" + port + '\'' +
	            ", webhook='" + webhook + '\'' +
	            ", webhookConfig='" + webhookConfig + '\'' +
	            ", facebookBot='" + facebookBot + '\'' +
	            ", facebookBotConfig='" + facebookBotConfig + '\'' +
	            ", webBot='" + webBot + '\'' +
	            ", appPage='" + page + '\'' +
	            ", nlpService='" + nlpService + '\'' +
	            ", dialogflowConfig='" + dialogflowConfig + '\'' +
	            ", gitRepository='" + gitRepository + '\'' +
	            ", license='" + license + '\'' +
	            ", outputDir='" + outputDir + '\'' +
	            "}";
	}

}
