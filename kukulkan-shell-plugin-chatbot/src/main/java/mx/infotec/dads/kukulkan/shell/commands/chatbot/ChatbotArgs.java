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

/**
 * The arguments read from console
 * 
 * @author Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
 *
 */
public class ChatbotArgs extends AbstractArgs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Parameter(names = { "--author" }, description = "The author name or company name")
    private String author = "Kukulkan";

    @Parameter(names = { "--no-web" }, arity = 0, description = "Disable the support for Web")
    private boolean noWebBot;

    @Parameter(names = { "--no-facebook" }, arity = 0, description = "Disable the support for Facebook")
    private boolean noFacebookBot;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isNoFacebookBot() {
        return noFacebookBot;
    }

    public void setNoFacebookBot(boolean noFacebookBot) {
        this.noFacebookBot = noFacebookBot;
    }

    public boolean isNoWebBot() {
        return noWebBot;
    }

    public void setNoWebBot(boolean noWebBot) {
        this.noWebBot = noWebBot;
    }

}
