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
package mx.infotec.dads.kukulkan.shell.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;

import mx.infotec.dads.kukulkan.shell.domain.ShellCompletionProposal;

/**
 * AbstractListValueProvider for excluded layers
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public abstract class AbstractListValueProvider extends ValueProviderSupport {

    private static final String COMMA = ",";
    private List<String> inputValues = getInputValues();

    @Override
    public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext,
            String[] hints) {
        String input = completionContext.currentWord();
        if (input == null || input.isEmpty() || (input.lastIndexOf(COMMA) == -1)) {
            return defaultValues();
        } else {
            return encodedValues(input);
        }
    }

    private List<CompletionProposal> encodedValues(String input) {
        String substring = input.substring(0, input.lastIndexOf(COMMA));
        List<String> layers = Arrays.asList(substring.split(COMMA));
        List<String> excludedValues = createExcludedValuesList(inputValues, layers);
        return createCompletitionList(excludedValues, layers);
    }

    private List<CompletionProposal> createCompletitionList(List<String> excludedValues, List<String> layers) {
        StringBuilder sb = new StringBuilder();
        for (String layer : layers) {
            sb.append(layer).append(COMMA);
        }
        String formatedString = sb.toString();
        List<CompletionProposal> cp = new ArrayList<>();

        for (String value : excludedValues) {
            cp.add(new ShellCompletionProposal(formatedString + value + COMMA, value));
        }
        return cp;
    }

    private List<String> createExcludedValuesList(List<String> inputs, List<String> excludedList) {
        List<String> newList = new ArrayList<>();
        for (String input : inputs) {
            if (!excludedList.contains(input)) {
                newList.add(input);
            }
        }
        return newList;
    }

    private List<CompletionProposal> defaultValues() {
        List<CompletionProposal> completitionList = new ArrayList<>();
        for (String input : inputValues) {
            completitionList.add(new ShellCompletionProposal(input + COMMA, input));
        }
        return completitionList;
    }

    public abstract List<String> getInputValues();
}