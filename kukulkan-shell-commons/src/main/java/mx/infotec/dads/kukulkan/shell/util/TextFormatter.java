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

import static mx.infotec.dads.kukulkan.metamodel.util.Constants.TAB;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import mx.infotec.dads.kukulkan.metamodel.util.Constants;

/**
 * TextFormatter.
 *
 * @author Daniel Cortes Pichardo
 */
public class TextFormatter {
    
    /**
     * Instantiates a new text formatter.
     */
    private TextFormatter() {
    }

    /**
     * format a List<CharSequence> in order to highlight some git command
     * results.
     *
     * @param input the input
     * @return List<AttributedString>
     */
    public static List<AttributedString> formatToGitOutput(List<CharSequence> input) {
        return input.stream().map(result -> {
            if (Matcher.INITIAL_TAB.matcher(result.toString()).find()) {
                return new AttributedString(result.toString(), AttributedStyle.BOLD.foreground(AttributedStyle.RED));
            } else {
                return new AttributedString(result.toString(), AttributedStyle.BOLD.foreground(AttributedStyle.WHITE));
            }
        }).collect(Collectors.toList());

    }

    /**
     * Format normal text.
     *
     * @param text the text
     * @return the attributed string
     */
    public static AttributedString formatNormalText(String text) {
        return new AttributedString(text, AttributedStyle.BOLD.blinkOff().foreground(AttributedStyle.WHITE));
    }

    /**
     * Format dir not exist text.
     *
     * @param text the text
     * @return the attributed string
     */
    public static AttributedString formatDirNotExistText(String text) {
        return formatErrorText("The dir does not exist: " + text);
    }

    /**
     * Format error text.
     *
     * @param text the text
     * @return the attributed string
     */
    public static AttributedString formatErrorText(String text) {
        return new AttributedString(text, AttributedStyle.BOLD.foreground(AttributedStyle.RED));
    }

    /**
     * Defaul base prompt.
     *
     * @return the attributed string
     */
    public static AttributedString defaulBasePrompt() {
        return new AttributedString("kukulkan", AttributedStyle.BOLD.foreground(AttributedStyle.BLUE));
    }

    /**
     * Defaul end prompt.
     *
     * @return the attributed string
     */
    public static AttributedString defaulEndPrompt() {
        return new AttributedString("> ", AttributedStyle.BOLD.foreground(AttributedStyle.BLUE));
    }

    /**
     * Format log text.
     *
     * @param text the text
     * @return the attributed string
     */
    public static AttributedString formatLogText(String text) {
        Objects.requireNonNull(text);
        if (Matcher.MAVEN_LOG_PATTERN.matcher(text).matches()) {
            AttributedStringBuilder asb = new AttributedStringBuilder();
            int rightBracketIndex = text.indexOf(']');
            asb.append(formatLogLevel(text.substring(1, rightBracketIndex)));
            asb.append(new AttributedString(text.substring(rightBracketIndex + 1, text.length()),
                    AttributedStyle.BOLD.foreground(AttributedStyle.WHITE)));
            return asb.toAttributedString();
        } else {
            return formatNormalText(text);
        }
    }

    /**
     * Format like glossary.
     *
     * @param key the key
     * @param term the term
     * @return the attributed string
     */
    public static AttributedString formatLikeGlossary(String key, String term) {
        AttributedStringBuilder asb = new AttributedStringBuilder();
        asb.append(new AttributedString(key, AttributedStyle.BOLD.foreground(AttributedStyle.GREEN)));
        asb.append(Constants.SEMICOLON).append(TAB).append(TAB);
        asb.append(new AttributedString(term, AttributedStyle.BOLD.foreground(AttributedStyle.WHITE)));
        return asb.toAttributedString();
    }

    /**
     * Format log level.
     *
     * @param logLevel the log level
     * @return the attributed string
     */
    private static AttributedString formatLogLevel(String logLevel) {
        AttributedStringBuilder asb = new AttributedStringBuilder();
        asb.append(new AttributedString("[", AttributedStyle.BOLD.foreground(AttributedStyle.WHITE)));
        if ("INFO".equals(logLevel)) {
            asb.append(new AttributedString(logLevel, AttributedStyle.BOLD.foreground(AttributedStyle.BLUE)));
        } else if ("WARNING".equals(logLevel)) {
            asb.append(new AttributedString(logLevel, AttributedStyle.BOLD.foreground(AttributedStyle.YELLOW)));
            return new AttributedString(logLevel, AttributedStyle.BOLD.foreground(AttributedStyle.YELLOW));
        } else if ("ERROR".equals(logLevel)) {
            asb.append(new AttributedString(logLevel, AttributedStyle.BOLD.foreground(AttributedStyle.RED)));
            return new AttributedString(logLevel, AttributedStyle.BOLD.foreground(AttributedStyle.RED));
        } else {
            asb.append(new AttributedString(logLevel, AttributedStyle.BOLD.foreground(AttributedStyle.WHITE)));
        }
        asb.append(new AttributedString("]", AttributedStyle.BOLD.foreground(AttributedStyle.WHITE)));
        return asb.toAttributedString();
    }
}
