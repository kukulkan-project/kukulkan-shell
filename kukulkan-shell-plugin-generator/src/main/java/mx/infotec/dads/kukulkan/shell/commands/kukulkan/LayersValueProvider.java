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
package mx.infotec.dads.kukulkan.shell.commands.kukulkan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.domain.ShellCompletionProposal;

/**
 * The Class GitValueProvider.
 */
@Component
public class LayersValueProvider extends ValueProviderSupport {

    /** The nav. */
    @Autowired
    private Navigator nav;
    List<CompletionProposal> kd = new ArrayList<>();
    List<String> validInputs = Arrays.asList("angular-js", "spring-service", "spring-repository");

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.shell.standard.ValueProvider#complete(org.
     * springframework.core.MethodParameter,
     * org.springframework.shell.CompletionContext, java.lang.String[])
     */
    @Override
    public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext,
            String[] hints) {
        String input = completionContext.currentWord();
        if (input == null) {
            return defaultValues();
        } else {
            return encodedValues(input);
        }
    }

    private List<CompletionProposal> encodedValues(String input) {
        List<CompletionProposal> completitionList = new ArrayList<>();
        completitionList.add(new ShellCompletionProposal("angular-js,", "angular-js"));
        completitionList.add(new ShellCompletionProposal("spring-service,", "spring-service"));
        completitionList.add(new ShellCompletionProposal("spring-repository,", "spring-repository"));
        List<String> layers = Arrays.asList(input.split(","));
        for (String layer : layers) {
            completitionList.remove(new CompletionProposal(layer));
        }
        return completitionList;
    }

    private List<CompletionProposal> defaultValues() {
        List<CompletionProposal> completitionList = new ArrayList<>();
        completitionList.add(new ShellCompletionProposal("angular-js,", "angular-js"));
        completitionList.add(new ShellCompletionProposal("spring-service,", "spring-service"));
        completitionList.add(new ShellCompletionProposal("spring-repository,", "spring-repository"));
        return completitionList;
    }

    private boolean validateInput(List<String> inputs, List<String> layers) {
        for (String layer : layers) {
            if (!inputs.contains(layer)) {
                return false;
            }
        }
        return true;
    }
}