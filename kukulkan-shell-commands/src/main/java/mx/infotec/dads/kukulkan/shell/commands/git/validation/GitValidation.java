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
package mx.infotec.dads.kukulkan.shell.commands.git.validation;

import java.util.Objects;
import java.util.regex.Pattern;

import mx.infotec.dads.kukulkan.shell.commands.git.GitContext;
import mx.infotec.dads.kukulkan.shell.util.GeneratorException;

/**
 * Docker Commands.
 *
 * @author Daniel Cortes Pichardo
 */
public class GitValidation {

    /** The Constant APP_NAME_PATTERN. */
    public static final Pattern BRANCH_NAME_PATTERN = Pattern.compile("^[a-z]*$");

    private GitValidation() {
    }

    public static void validateBranchName(String featureName) {
        Objects.requireNonNull(featureName);
        StringBuilder message = new StringBuilder();
        if (!BRANCH_NAME_PATTERN.matcher(featureName).matches()) {
            message.append("\n\n\t").append("[app-name] no fullfilment the pattern ").append("^[a-z]*$");
        }
        if (message.length() > 0) {
            throw new GeneratorException(message.toString());
        }
    }

    public static void validateDevelopBranch(GitContext gitContext) {
        if (!gitContext.isDevelop()) {
            throw new GeneratorException("You mus be on <develp> branch");
        }
    }

    public static void notYetImplemented() {
        throw new GeneratorException("Not yet implemented, await for the next release");
    }
}