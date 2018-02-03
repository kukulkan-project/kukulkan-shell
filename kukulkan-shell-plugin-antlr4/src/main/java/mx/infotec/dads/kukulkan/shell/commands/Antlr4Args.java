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
package mx.infotec.dads.kukulkan.shell.commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

/**
 * Antlr4Context
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class Antlr4Args implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = { "--id" }, description = "the project name")
    private String id;

    @Parameter(names = { "--grammar-name" }, description = "the name of the grammar")
    private String grammarName;

    @Parameter(names = { "--grammar-extension" }, description = "the grammar Extension")
    private String grammarExtension;

    @Parameter(names = { "--package-name" }, description = "the package name of the project")
    private String packaging;

    public String getGrammarName() {
        return grammarName;
    }

    public void setGrammarName(String grammarName) {
        this.grammarName = grammarName;
    }

    public String getGrammarExtension() {
        return grammarExtension;
    }

    public void setGrammarExtension(String grammarExtension) {
        this.grammarExtension = grammarExtension;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Args{" + "projectName=" + id + ", grammarExtension=" + grammarExtension + ", grammarName='"
                + grammarName + '\'' + ", packageName=" + packaging + '}';
    }
}
