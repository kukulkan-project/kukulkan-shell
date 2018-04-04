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

import java.nio.file.Path;

/**
 * The ChatbotContext for generator
 * 
 * @author Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
 *
 */
public class ChatbotContext {

    /**
     * The directory where project will be generated
     */
    private Path outputDir;

    /**
     * The project name
     */
    private String name;

    /**
     * The project description
     */
    private String description;

    /**
     * The author name (individual or company)
     */
    private String author;

    /**
     * The port to be exposed
     */
    private int port;

    /**
     * Enables webhook support for fulfillment
     */
    private boolean webhook;

    /**
     * The webhook configuration if enabled
     */
    private WebhookConfig webhookConfig;

    /**
     * Enables facebook support
     */
    private boolean facebookBot;

    /**
     * The facebook configuration if enabled
     */
    private FacebookBotConfig facebookBotConfig;

    /**
     * Enables the web support
     */
    private boolean webBot;

    /**
     * The project page
     */
    private String page;

    /**
     * The NLP service
     */
    private NlpService nlpService;

    /**
     * The configuration for DialogFlow if nlpService is "DIALOGFLOW"
     */
    private DialogFlowConfig dialogflowConfig;

    /**
     * The git repository
     */
    private String gitRepository;

    /**
     * The license
     */
    private String license;

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

    public boolean isWebBot() {
        return webBot;
    }

    public void setWebBot(boolean webBot) {
        this.webBot = webBot;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    @Override
    public String toString() {
        return "ChatbotContext{" + "appName='" + name + '\'' + ", description='" + description + '\'' + ", author='"
                + author + '\'' + ", port='" + port + '\'' + ", webhook='" + webhook + '\'' + ", webhookConfig='"
                + webhookConfig + '\'' + ", facebookBot='" + facebookBot + '\'' + ", facebookBotConfig='"
                + facebookBotConfig + '\'' + ", webBot='" + webBot + '\'' + ", appPage='" + page + '\''
                + ", nlpService='" + nlpService + '\'' + ", dialogflowConfig='" + dialogflowConfig + '\''
                + ", gitRepository='" + gitRepository + '\'' + ", outputDir='" + outputDir + '\'' + "}";
    }

}
