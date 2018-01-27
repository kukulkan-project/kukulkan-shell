/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2016 Daniel Cortes Pichardo
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

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * TemplateFactory for Immutable template list.
 *
 * @author Daniel Cortes Pichardo
 */
public class TemplateFactory {

    /** The Constant MONGO_TEMPLATE_LIST. */
    public static final List<String> ANTLR4_TEMPLATE_LIST;

    public static final String ANTLR4_TEMPLATE = "archetypes/antlr4";
    static {
        ANTLR4_TEMPLATE_LIST = ImmutableList.copyOf(getAntrl4Templates());
    }

    /**
     * Instantiates a new template factory.
     */
    private TemplateFactory() {
    }

    public static List<String> getAntrl4Templates() {
        List<String> templates = new ArrayList<>();
        templates.add(ANTLR4_TEMPLATE + "/src/main/antlr4/package/MyGrammar.g4.ftl");
        templates.add(ANTLR4_TEMPLATE + "/src/main/java/package/Main.java.ftl");
        templates.add(ANTLR4_TEMPLATE + "/src/main/java/package/MyGrammarCustomVisitor.java.ftl");
        templates.add(ANTLR4_TEMPLATE + "/pom.xml.ftl");
        templates.add(ANTLR4_TEMPLATE + "/test/test.MyExtension.ftl");
        return templates;
    }

}
