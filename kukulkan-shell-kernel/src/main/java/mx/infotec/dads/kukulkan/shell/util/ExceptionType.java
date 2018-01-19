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
package mx.infotec.dads.kukulkan.shell.util;

import org.jline.utils.AttributedStyle;

/**
 * The Enum ExceptionType.
 */
public enum ExceptionType {
    
    /** The error. */
    ERROR("ERROR: ", AttributedStyle.BOLD.foreground(AttributedStyle.RED)), 
    
    /** The info. */
    INFO("INFO: ",AttributedStyle.BOLD.foreground(AttributedStyle.BLUE)), 
    
    /** The warning. */
    WARNING("WARNING: ", AttributedStyle.BOLD.foreground(AttributedStyle.YELLOW));
    
    /** The title. */
    private String title;
    
    /** The style. */
    private AttributedStyle style;

    /**
     * Instantiates a new exception type.
     *
     * @param title the title
     * @param style the style
     */
    private ExceptionType(String title, AttributedStyle style) {
        this.title = title;
        this.style = style;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the style.
     *
     * @return the style
     */
    public AttributedStyle getStyle() {
        return style;
    }
}
