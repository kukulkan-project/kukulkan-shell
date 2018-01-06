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
 * TextFormatter
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class TextFormatter {
    private TextFormatter() {
    }

    /**
     * format a List<CharSequence> in order to highlight some git command
     * results
     * 
     * @param input
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

    public static AttributedString formatNormalText(String text) {
        return new AttributedString(text, AttributedStyle.BOLD.blinkOff().foreground(AttributedStyle.WHITE));
    }

    public static AttributedString formatDirNotExistText(String text) {
        return formatErrorText("The dir does not exist: " + text);
    }

    public static AttributedString formatErrorText(String text) {
        return new AttributedString(text, AttributedStyle.BOLD.foreground(AttributedStyle.RED));
    }

    public static AttributedString defaulBasePrompt() {
        return new AttributedString("kukulkan", AttributedStyle.BOLD.foreground(AttributedStyle.BLUE));
    }

    public static AttributedString defaulEndPrompt() {
        return new AttributedString("> ", AttributedStyle.BOLD.foreground(AttributedStyle.BLUE));
    }

    public static AttributedString formatLogText(String text) {
        Objects.requireNonNull(text);
        if (Matcher.MAVEN_LOG_PATTERN.matcher(text).matches()) {
            AttributedStringBuilder asb = new AttributedStringBuilder();
            int rightBracketIndex = text.indexOf(']');
            asb.append(formatLogLeve(text.substring(1, rightBracketIndex)));
            asb.append(new AttributedString(text.substring(rightBracketIndex + 1, text.length()),
                    AttributedStyle.BOLD.foreground(AttributedStyle.WHITE)));
            return asb.toAttributedString();
        } else {
            return formatNormalText(text);
        }
    }

    public static AttributedString formatLikeGlossary(String key, String term) {
        AttributedStringBuilder asb = new AttributedStringBuilder();
        asb.append(new AttributedString(key, AttributedStyle.BOLD.foreground(AttributedStyle.GREEN)));
        asb.append(Constants.SEMICOLON).append(TAB).append(TAB);
        asb.append(new AttributedString(term, AttributedStyle.BOLD.foreground(AttributedStyle.WHITE)));
        return asb.toAttributedString();
    }

    private static AttributedString formatLogLeve(String logLevel) {
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
