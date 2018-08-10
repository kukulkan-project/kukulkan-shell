/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2018 Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
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

import java.io.File;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.engine.service.WriterService;
import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.services.WriterHelper;

/**
 * 
 * @author Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
 *
 */
@Service
public class WriterHelperImpl implements WriterHelper {

    @Autowired
    private Navigator navigator;

    @Autowired
    private WriterService writerService;

    @Override
    public Optional<File> save(String path, String content) {
        return writerService.save(navigator.getCurrentPath().resolve(path), content);
    }

    @Override
    public Optional<File> copyTemplate(String template, String relative, Object model) {
        return writerService.copyTemplate(template, navigator.getCurrentPath(), relative, model);
    }

    @Override
    public Optional<File> copyTemplate(String template, Function<String, String> function, Object model) {
        return writerService.copyTemplate(template, navigator.getCurrentPath(), function, model);
    }

    @Override
    public Optional<File> copy(String resource, String relative) {
        return writerService.copy(resource, navigator.getCurrentPath(), relative);
    }

    @Override
    public Optional<File> copy(String resource, Function<String, String> function) {
        return writerService.copy(resource, navigator.getCurrentPath(), function);
    }

    @Override
    public Optional<File> copy(String resource, String relative, Object model) {
        return writerService.copy(resource, navigator.getCurrentPath(), relative, model);
    }

    @Override
    public Optional<File> copy(String resource, Function<String, String> function, Object model) {
        return writerService.copy(resource, navigator.getCurrentPath(), function, model);
    }

    @Override
    public void copyDir(Class clazz, String directory, String pattern, String relative) {
        writerService.copyDir(clazz, directory, navigator.getCurrentPath(), relative);
    }

    @Override
    public void copyDir(Class clazz, String directory, String relative) {
        writerService.copyDir(clazz, directory, navigator.getCurrentPath(), relative);
    }

    @Override
    public void copyDir(Class clazz, String directory, String relative, Object model) {
        writerService.copyDir(clazz, directory, navigator.getCurrentPath(), relative, model);
    }

    @Override
    public void copyDir(Class clazz, String directory, String pattern, String relative, Object model) {
        writerService.copyDir(clazz, directory, pattern, navigator.getCurrentPath(), relative);
    }

    @Override
    public Optional<File> copySmart(String resourceOrTemplate, String relative, Object model) {
        return writerService.copySmart(resourceOrTemplate, navigator.getCurrentPath(), relative, model);
    }

    @Override
    public Optional<File> copySmart(String template, Function<String, String> function, Object model) {
        return writerService.copySmart(template, navigator.getCurrentPath(), function, model);
    }

    @Override
    public Optional<File> rewriteFile(String template, String fileRelativePath, Object model, String needle) {
        return writerService.rewriteFile(template, navigator.getCurrentPath().resolve(fileRelativePath), model, needle);
    }

}
