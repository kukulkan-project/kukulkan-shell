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
package mx.infotec.dads.kukulkan.shell.commands.valueprovided;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

/**
 * The Class KukulkanFilesProvider.
 */
@Component
public class KukulkanFilesProvider extends ValueProviderSupport {
    
    /** The Constant KUKULKAN_FILE_PATTERN. */
    private static final String KUKULKAN_FILE_PATTERN = "^[a-zA-Z0-9]*\\.3k$";
    
    /* (non-Javadoc)
     * @see org.springframework.shell.standard.ValueProvider#complete(org.springframework.core.MethodParameter, org.springframework.shell.CompletionContext, java.lang.String[])
     */
    @Override
    public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext,
            String[] hints) {
        try {
            return Files.list(Paths.get("."))
                    .filter(path -> path.getFileName().toString().matches(KUKULKAN_FILE_PATTERN))
                    .map(path -> new CompletionProposal(path.getFileName().toString()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            List<CompletionProposal> defaultList = new ArrayList<>();
            defaultList.add(new CompletionProposal("[No Files Found with .3K extension]"));
            return defaultList;
        }
    }
}