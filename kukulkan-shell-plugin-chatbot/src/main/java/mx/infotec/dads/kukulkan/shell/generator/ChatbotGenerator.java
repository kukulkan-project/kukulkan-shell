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
import static mx.infotec.dads.kukulkan.shell.generator.ChatbotArchetypeWriter.writeArchetype;

import java.util.HashMap;
import java.util.Map;

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
        
        writeArchetype(writer, model);
        
    }

}
