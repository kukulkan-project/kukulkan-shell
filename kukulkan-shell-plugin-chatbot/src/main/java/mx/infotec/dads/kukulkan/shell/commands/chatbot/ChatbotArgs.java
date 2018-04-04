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
package mx.infotec.dads.kukulkan.shell.commands.chatbot;

import java.io.Serializable;

import com.beust.jcommander.Parameter;

import mx.infotec.dads.kukulkan.shell.domain.AbstractArgs;
import mx.infotec.dads.kukulkan.shell.generator.NlpService;

/**
 * The arguments read from console
 * @author Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
 *
 */
public class ChatbotArgs extends AbstractArgs implements Serializable {

	private static final long serialVersionUID = 1L;

	@Parameter(names = { "--author" }, description = "The author name or company name")
	private String author = "Kukulkan";

	@Parameter(names = { "--port" }, description = "The port to expose the project")
	private int port = 8090;

	@Parameter(names = { "--webhook" }, arity = 0, description = "Enable the creation of a webhook for fulfillment")
	private boolean webhook;

	@Parameter(names = { "--webhook-endpoint" }, description = "The url to make POST for fulfillment")
	private String endpointWebhook = "http://localhost/fulfillment";

	@Parameter(names = { "--no-web" }, arity = 0, description = "Disable the support for Web")
	private boolean noWebBot;

	@Parameter(names = { "--facebook" }, arity = 0, description = "Enable the support for Facebook")
	private boolean facebookBot;

	@Parameter(names = { "--no-fb-menu" }, arity = 0, description = "Disable the menu creation in Facebook chat")
	private boolean noFbMenu;

	@Parameter(names = { "--fb-access-token" }, description = "The access token of your Facebook page")
	private String fbAccessToken = "";

	@Parameter(names = { "--fb-verify-token" }, description = "The verify token of your Facebook webhook")
	private String fbVerifyToken = "";

	@Parameter(names = { "--nlp-service" }, description = "The NLP service which manages the conversation")
	private NlpService nlpService = NlpService.NONE;

	@Parameter(names = { "--dialogflow-client-token" }, description = "The DialogFlow client token")
	private String dfClientToken = "";

	@Parameter(names = { "--dialogflow-dev-token" }, description = "The DialogFlow developer token")
	private String dfDeveloperToken = "";

	@Parameter(names = { "--repository", }, description = "The remote git repository")
	private String gitRepository = "";

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

	public String getEndpointWebhook() {
		return endpointWebhook;
	}

	public void setEndpointWebhook(String endpointWebhook) {
		this.endpointWebhook = endpointWebhook;
	}

	public String getFbAccessToken() {
		return fbAccessToken;
	}

	public void setFbAccessToken(String fbAccessToken) {
		this.fbAccessToken = fbAccessToken;
	}

	public String getFbVerifyToken() {
		return fbVerifyToken;
	}

	public void setFbVerifyToken(String fbVerifyToken) {
		this.fbVerifyToken = fbVerifyToken;
	}

	public NlpService getNlpService() {
		return nlpService;
	}

	public void setNlpService(NlpService nlpService) {
		this.nlpService = nlpService;
	}

	public String getDfClientToken() {
		return dfClientToken;
	}

	public void setDfClientToken(String dfClientToken) {
		this.dfClientToken = dfClientToken;
	}

	public String getDfDeveloperToken() {
		return dfDeveloperToken;
	}

	public void setDfDeveloperToken(String dfDeveloperToken) {
		this.dfDeveloperToken = dfDeveloperToken;
	}

	public String getGitRepository() {
		return gitRepository;
	}

	public void setGitRepository(String gitRepository) {
		this.gitRepository = gitRepository;
	}

	public boolean isFacebookBot() {
		return facebookBot;
	}

	public void setFacebookBot(boolean facebookBot) {
		this.facebookBot = facebookBot;
	}

	public boolean isNoFbMenu() {
		return noFbMenu;
	}

	public void setNoFbMenu(boolean noFbMenu) {
		this.noFbMenu = noFbMenu;
	}

	public boolean isNoWebBot() {
		return noWebBot;
	}

	public void setNoWebBot(boolean noWebBot) {
		this.noWebBot = noWebBot;
	}

}
