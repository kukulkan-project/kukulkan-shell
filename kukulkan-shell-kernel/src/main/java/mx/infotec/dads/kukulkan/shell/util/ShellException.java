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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ShellException.
 *
 * @author Daniel Cortes Pichardo
 */
public class ShellException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The words. */
    private final List<String> words;

    /** The type. */
    private final ExceptionType type;

    /**
     * Instantiates a new shell exception.
     *
     * @param words the words
     */
    public ShellException(List<String> words) {
        this.words = words;
        type = ExceptionType.ERROR;
    }

    /**
     * Instantiates a new shell exception.
     *
     * @param type the type
     * @param words the words
     */
    public ShellException(ExceptionType type, List<String> words) {
        this.words = words;
        this.type = type;
    }

    /**
     * Instantiates a new shell exception.
     *
     * @param messages the messages
     */
    public ShellException(String... messages) {
        words = new ArrayList<>();
        type = ExceptionType.ERROR;
        Collections.addAll(words, messages);
    }

    /**
     * Instantiates a new shell exception.
     *
     * @param type the type
     * @param messages the messages
     */
    public ShellException(ExceptionType type, String... messages) {
        words = new ArrayList<>();
        this.type = type;
        Collections.addAll(words, messages);
    }

    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {
        return String.format("Kukulkan error: '%s'", words.stream().collect(Collectors.joining(" ")));
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public ExceptionType getType() {
        return type;
    }

}
