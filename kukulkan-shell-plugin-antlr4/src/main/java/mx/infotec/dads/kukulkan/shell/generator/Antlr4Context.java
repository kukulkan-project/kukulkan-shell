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
package mx.infotec.dads.kukulkan.shell.generator;

import mx.infotec.dads.kukulkan.metamodel.context.BaseContext;

/**
 * Antlr4Context.
 *
 * @author Daniel Cortes Pichardo
 */
public class Antlr4Context extends BaseContext {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The grammar name. */
    private String grammarName;

    /** The grammar extension. */
    private String grammarExtension;

    /**
     * Gets the grammar name.
     *
     * @return the grammar name
     */
    public String getGrammarName() {
        return grammarName;
    }

    /**
     * Sets the grammar name.
     *
     * @param grammarName
     *            the new grammar name
     */
    public void setGrammarName(String grammarName) {
        this.grammarName = grammarName;
    }

    /**
     * Gets the grammar extension.
     *
     * @return the grammar extension
     */
    public String getGrammarExtension() {
        return grammarExtension;
    }

    /**
     * Sets the grammar extension.
     *
     * @param grammarExtension
     *            the new grammar extension
     */
    public void setGrammarExtension(String grammarExtension) {
        this.grammarExtension = grammarExtension;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Args{" + "projectName=" + this.getId() + ", grammarExtension=" + grammarExtension + ", grammarName='"
                + grammarName + '\'' + ", packageName=" + this.getPackaging() + '}';
    }
}
