package mx.infotec.dads.kukulkan.shell.commands.validation;

import java.util.Objects;
import java.util.regex.Pattern;

import mx.infotec.dads.kukulkan.shell.commands.exception.GeneratorException;

/**
 * User Input Validation
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class UserInputValidation {
    public static final Pattern APP_NAME_PATTERN = Pattern.compile("^[a-z]*$");
    public static final Pattern GROUP_ID_PATTERN = Pattern.compile(
            "^([a-zA-Z_]{1}[a-zA-Z]*){2,10}\\.([a-zA-Z_]{1}[a-zA-Z0-9_]*){1,30}((\\.([a-zA-Z_]{1}[a-zA-Z0-9_]*){1,61})*)?$");

    private UserInputValidation() {

    }

    public static void createProjectValidation(String appName, String groupId) {
        Objects.requireNonNull(appName);
        Objects.requireNonNull(groupId);
        StringBuilder message = new StringBuilder();
        if (!APP_NAME_PATTERN.matcher(appName).matches()) {
            message.append("\n\n\t").append("[app-name] no fullfilment the pattern ").append("^[a-z]*$");
        }
        if (!GROUP_ID_PATTERN.matcher(groupId).matches()) {
            message.append("\n\n\t").append("[group-id] no fullfilment the pattern ").append(
                    "^([a-zA-Z_]{1}[a-zA-Z]*){2,10}\\.([a-zA-Z_]{1}[a-zA-Z0-9_]*){1,30}((\\.([a-zA-Z_]{1}[a-zA-Z0-9_]*){1,61})*)?$").append("\n\n");
        }
        if (message.length() > 0) {
            throw new GeneratorException(message.toString());
        }
    }
}
