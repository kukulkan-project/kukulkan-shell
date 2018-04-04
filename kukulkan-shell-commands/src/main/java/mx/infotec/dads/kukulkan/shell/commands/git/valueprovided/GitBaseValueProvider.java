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
package mx.infotec.dads.kukulkan.shell.commands.git.valueprovided;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.services.CommandService;

/**
 * The Class GitValueProvider.
 * 
 * @author Daniel Cortes Pichardo
 */

public abstract class GitBaseValueProvider extends ValueProviderSupport {
    @Autowired
    private CommandService commandService;

    private ShellCommand shellCommand;

    private GitLineFormatter formatter;

    private Predicate<CharSequence> predicate;

    @PostConstruct
    private void init() {
        shellCommand = getShellCommand();
        formatter = getLineFormatter();
        predicate = getFilter();
    }

    @Override
    public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext,
            String[] hints) {
        return commandService.exec(shellCommand).stream().filter(predicate)
                .map(charSequence -> new CompletionProposal(formatter.formatLine(charSequence)))
                .collect(Collectors.toList());
    }

    public GitLineFormatter getLineFormatter() {
        return (line) -> String.valueOf(line).trim();
    }

    public Predicate<CharSequence> getFilter() {
        return (charSequence) -> true;
    }

    public abstract ShellCommand getShellCommand();
}