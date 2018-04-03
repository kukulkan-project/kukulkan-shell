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
package mx.infotec.dads.kukulkan.shell.commands.navigation;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.util.FilesCommons;

/**
 * The Class GitValueProvider.
 */
@Component
public class DirectoryValueProvider extends ValueProviderSupport {

    /** The nav. */
    @Autowired
    private Navigator nav;

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
        Path completionWordPath = Paths.get(completionContext.currentWord());

        if (completionWordPath.toString().isEmpty()) {
            return FilesCommons.filterDirs(nav.getCurrentPath());
        } else {
            Path pathToSearch = resolvePathToSearchDirectory(nav.getCurrentPath().resolve(completionWordPath));
            return FilesCommons.filterDirs(resolveParent(completionWordPath), pathToSearch);
        }
    }

    private Path resolveParent(Path currentWord) {
        Path resolve = nav.getCurrentPath().resolve(currentWord);
        return testIfPathExist(resolve, currentWord);
    }

    private Path resolvePathToSearchDirectory(Path currentWord) {
        return testIfPathExist(currentWord, currentWord);
    }

    private Path testIfPathExist(Path testPath, Path returnedPath) {
        File f = new File(testPath.toString());
        if (f.exists() && f.isDirectory()) {
            return returnedPath;
        } else {
            return returnedPath.getParent();
        }
    }
}