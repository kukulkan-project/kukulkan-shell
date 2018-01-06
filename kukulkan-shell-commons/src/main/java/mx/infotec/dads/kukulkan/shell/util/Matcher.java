package mx.infotec.dads.kukulkan.shell.util;

import java.util.regex.Pattern;

/**
 * Matcher
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class Matcher {

    String logEntryPattern = "";

    public static final Pattern INITIAL_TAB = Pattern.compile("(\\t)(.*)");
    public static final Pattern MAVEN_LOG_PATTERN = Pattern.compile("^\\[\\w+\\][^\n]*");

    private Matcher() {
    }

}
