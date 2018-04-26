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
    public Optional<File> copyTemplate(String template, String relative, Object model) {
        return writerService.copyTemplate(template, navigator.getCurrentPath(), relative, model);
    }

    @Override
    public Optional<File> copy(String resource, String relative) {
        return writerService.copy(resource, navigator.getCurrentPath(), relative);
    }

    @Override
    public void copyDir(Class clazz, String directory, String relative) {
        writerService.copyDir(clazz, directory, navigator.getCurrentPath(), relative);
    }

    @Override
    public void copyDir(Class clazz, String directory, String pattern, String relative) {
        writerService.copyDir(clazz, directory, navigator.getCurrentPath(), relative);
    }

    @Override
    public void copyDir(Class clazz, String directory, String pattern, String relative, Object model) {
        writerService.copyDir(clazz, directory, navigator.getCurrentPath(), relative);
    }

    @Override
    public void copyDir(Class clazz, String directory, String relative, Object model) {
        writerService.copyDir(clazz, directory, navigator.getCurrentPath(), relative);
    }

    @Override
    public void copySmart(String resource, String toSave, Object model) {
        writerService.copySmart(resource, navigator.getCurrentPath(), toSave, model);
    }

    @Override
    public Optional<File> copy(String resource, String toSave, Object model) {
        return writerService.copy(resource, navigator.getCurrentPath(), toSave, model);
    }

}
