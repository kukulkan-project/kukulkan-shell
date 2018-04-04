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
     * The license
     */
    private String license = "MIT";

    /**
     * The project description
     */
    private String description;

    /**
     * The author name (individual or company)
     */
    private String author;

    /**
     * Enables facebook support
     */
    private boolean facebookBot;

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

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
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

    public boolean isFacebookBot() {
        return facebookBot;
    }

    public void setFacebookBot(boolean facebookBot) {
        this.facebookBot = facebookBot;
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

    public boolean isWebBot() {
        return webBot;
    }

    public void setWebBot(boolean webBot) {
        this.webBot = webBot;
    }

    @Override
    public String toString() {
        return "ChatbotContext{" + "appName='" + name + '\'' + ", description='" + description + '\'' + ", author='"
                + author + '\'' + '\'' + ", facebookBot='" + facebookBot + '\'' + '\'' + ", webBot='" + webBot + '\''
                + ", appPage='" + page + '\'' + ", nlpService='" + nlpService + '\'' + '\'' + '\'' + ", outputDir='"
                + outputDir + '\'' + "}";
    }

}
