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
package mx.infotec.dads.kukulkan.shell.commands.git;

import mx.infotec.dads.kukulkan.shell.util.GeneratorException;

/**
 * Docker Commands.
 *
 * @author Daniel Cortes Pichardo
 */
public class GitHelper {

    private GitHelper() {

    }

    /** The Constant GIT_COMMAND. */
    public static final String GIT_COMMAND = "git";

    public static final String INIT = "init";

    public static final String CLONE = "clone";

    public static final String ADD = "add";
    
    public static final String ADD_ALL_PARAM = "-A";

    public static final String COMMIT = "commit";

    public static final String PUSH = "push";

    public static final String PULL = "pull";

    public static final String MERGE = "merge";

    public static final String STATUS = "status";

    public static final String CHECKOUT = "checkout";

    public static final String BRANCH = "branch";

    public static final String LOG = "log";

    public static final String MASTER_BRANCH = "master";
    
    public static final String DEVELOP_BRANCH = "develop";

    public static final String FEATURE_PREFIX = "feature-";

    public static final String RELEASE_PREFIX = "release-";

    public static String formatGitBranchLine(CharSequence charSequence) {
        return String.valueOf(charSequence).replace("*", "").trim();
    }

    public static void validateFeatureBranch(String branchName) {
        validateFeatureBranch(branchName, "You are not on a <Feature> branch");
    }

    public static void validateFeatureBranch(String branchName, String message) {
        if (!branchName.matches("feature-.*")) {
            throw new GeneratorException(message);
        }
    }

}
