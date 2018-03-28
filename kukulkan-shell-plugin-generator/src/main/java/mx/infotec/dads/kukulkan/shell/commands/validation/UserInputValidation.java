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
package mx.infotec.dads.kukulkan.shell.commands.validation;

import java.util.Objects;
import java.util.regex.Pattern;

import mx.infotec.dads.kukulkan.shell.util.GeneratorException;


/**
 * User Input Validation.
 *
 * @author Daniel Cortes Pichardo
 */
public class UserInputValidation {
    
    /** The Constant APP_NAME_PATTERN. */
    public static final Pattern APP_NAME_PATTERN = Pattern.compile("^[a-z]*$");
    
    /** The Constant PACKAGE_ID_PATTERN. */
    public static final Pattern PACKAGE_ID_PATTERN = Pattern.compile(
            "^([a-zA-Z_]{1}[a-zA-Z]*){2,10}\\.([a-zA-Z_]{1}[a-zA-Z0-9_]*){1,30}((\\.([a-zA-Z_]{1}[a-zA-Z0-9_]*){1,61})*)?$");

    /**
     * Instantiates a new user input validation.
     */
    private UserInputValidation() {

    }

    /**
     * Validate project params.
     *
     * @param appName the app name
     * @param packaging the packaging
     */
    public static void validateParams(String appName, String packaging) {
        Objects.requireNonNull(appName);
        Objects.requireNonNull(packaging);
        StringBuilder message = new StringBuilder();
        if (!APP_NAME_PATTERN.matcher(appName).matches()) {
            message.append("\n\n\t").append("[app-name] no fullfilment the pattern ").append("^[a-z]*$");
        }
        if (!PACKAGE_ID_PATTERN.matcher(packaging).matches()) {
            message.append("\n\n\t").append("[group-id] no fullfilment the pattern ").append(
                    "^([a-zA-Z_]{1}[a-zA-Z]*){2,10}\\.([a-zA-Z_]{1}[a-zA-Z0-9_]*){1,30}((\\.([a-zA-Z_]{1}[a-zA-Z0-9_]*){1,61})*)?$").append("\n\n");
        }
        if (message.length() > 0) {
            throw new GeneratorException(message.toString());
        }
    }
}
