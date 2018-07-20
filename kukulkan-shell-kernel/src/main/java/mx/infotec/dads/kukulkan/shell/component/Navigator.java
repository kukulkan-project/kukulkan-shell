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
package mx.infotec.dads.kukulkan.shell.component;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
 * FileNavigator.
 *
 * @author Daniel Cortes Pichardo
 */
@Component
public class Navigator {

    /** The current path. */
    private Path currentPath;

    /** The previus path. */
    private Path previousPath;

    /**
     * Inits the.
     */
    @PostConstruct
    private void init() {
        this.currentPath = Paths.get(System.getProperty("user.dir"));
        this.previousPath = null;
    }

    /**
     * Gets the current path.
     *
     * @return the current path
     */
    public Path getCurrentPath() {
        return currentPath;
    }

    /**
     * Sets the current path.
     *
     * @param currentPath
     *            the new current path
     */
    public void setCurrentPath(Path currentPath) {
        this.previousPath = this.currentPath;
        this.currentPath = currentPath;
    }

    /**
     * Gets the previus path.
     *
     * @return the previus path
     */
    public Path getPreviusPath() {
        return previousPath;
    }
}
