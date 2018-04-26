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
package mx.infotec.dads.kukulkan.shell.commands.util;

import mx.infotec.dads.kukulkan.shell.generator.ChatbotContext;
import mx.infotec.dads.kukulkan.shell.generator.NlpService;

/**
 * Mapper from ChatbotArgs to ChatbotContext
 * 
 * @author Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
 *
 */
public class Mapper {

    private Mapper() {
    }

    public static ChatbotContext toContext(String name) {
        ChatbotContext ctx = new ChatbotContext();
        ctx.setName(name);
        ctx.setPage("https://github.com/kukulkan-project");
        ctx.setAuthor("Kukulkán");
        ctx.setDescription(name + " bot");
        ctx.setFacebookBot(true);
        ctx.setWebBot(true);
        ctx.setLicense("MIT");
        ctx.setNlpService(NlpService.DIALOGFLOW);
        return ctx;
    }

}
