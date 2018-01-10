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
package mx.infotec.dads.kukulkan.shell.services.impl;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.services.PromptLocationtUpdateService;
import mx.infotec.dads.kukulkan.shell.util.FilesCommons;

/**
 * PromptFactory, It is used to create a config a prompt.
 *
 * @author Daniel Cortes Pichardo
 */
@Service
public class PromptServiceImpl implements PromptLocationtUpdateService {

    /** The Constant GIT. */
    private static final String GIT = "git";

    /** The command service. */
    @Autowired
    CommandServiceImpl commandService;

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.shell.services.PromptService#createPrompt(java.nio.file.Path, org.jline.utils.AttributedString, org.jline.utils.AttributedString)
     */
    public AttributedString createPrompt(Path currentPath, AttributedString basePrompt, AttributedString endPrompt) {
        AttributedString dirPrompt = null;
        if (FilesCommons.hasGitFiles(currentPath)) {
            List<CharSequence> result = commandService.exec(currentPath,
                    new ShellCommand(GIT).addArg("rev-parse").addArg("--abbrev-ref").addArg("HEAD"),
                    line -> Optional.ofNullable(new AttributedString(line)));

            dirPrompt = AttributedString.join(new AttributedString(""),
                    new AttributedString(" @", AttributedStyle.BOLD.foreground(AttributedStyle.YELLOW)),
                    new AttributedString("git/" + result.get(0).toString(),
                            AttributedStyle.BOLD_OFF.foreground(AttributedStyle.YELLOW)));
        } else {
            dirPrompt = new AttributedString("");
        }
        return AttributedString.join(new AttributedString(""), basePrompt, dirPrompt, endPrompt);
    }
}
