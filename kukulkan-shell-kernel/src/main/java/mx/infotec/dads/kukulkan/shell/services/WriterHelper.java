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

package mx.infotec.dads.kukulkan.shell.services;

import java.io.File;
import java.util.Optional;
import java.util.function.Function;

public interface WriterHelper {

    /**
     * Save the content to the given path
     * 
     * @param path
     * @param content
     * @return the file if saved
     */
    public Optional<File> save(String path, String content);

    /**
     * Fill the template with the model. The relative is processed as a template.
     * And finally save to path resolved to processed relative
     * 
     * @param template
     * @param path
     * @param relative
     * @param model
     * @return the file if saved
     */
    public Optional<File> copyTemplate(String template, String relative, Object model);

    /**
     * Fill the template with the model. The function is applied to template string
     * to get the relative. And finally save to path resolved to the relative (the
     * string returned string by function).
     * 
     * @param template
     * @param path
     * @param function
     * @param model
     * @return the file if saved
     */
    public Optional<File> copyTemplate(String template, Function<String, String> function, Object model);

    /**
     * Copy the resource to the path resolved to relative
     */
    public Optional<File> copy(String resource, String relative);

    /**
     * Copy the resource to the path resolved to relative returned by the function
     * 
     * @param resource
     * @param path
     * @param function
     * @return the file if saved
     */
    public Optional<File> copy(String resource, Function<String, String> function);

    /**
     * Copy the resource path resolved to relative. The relative is processed as a
     * template.
     * 
     * @param resource
     * @param path
     * @param relative
     * @param model
     * @return the file if saved
     */
    public Optional<File> copy(String resource, String relative, Object model);

    /**
     * Copy the resource to path resolved to relative returned by the function. The
     * relative is processed as a template.
     * 
     * @param resource
     * @param path
     * @param function
     * @param model
     * @return the file if saved
     */
    public Optional<File> copy(String resource, Function<String, String> function, Object model);

    /**
     * Search in jar containing the class, then copy the files in directory matching
     * with the pattern and save to the path resolved to relative
     * 
     * @param clazz
     * @param directory
     * @param pattern
     * @param path
     * @param relative
     */
    public void copyDir(Class clazz, String directory, String pattern, String relative);

    /**
     * Search in jar containing the class, then copy ALL FILES in directory and save
     * them to the path resolved to relative
     * 
     * @param clazz
     * @param directory
     * @param path
     * @param relative
     */
    public void copyDir(Class clazz, String directory, String relative);

    /**
     * Search in jar containing the class, then copy all files in directory and save
     * them to path resolved to relative
     * 
     * The relative is processed as a template
     * 
     * @param clazz
     * @param directory
     * @param path
     * @param relative
     * @param model
     */
    public void copyDir(Class clazz, String directory, String relative, Object model);

    /**
     * Search in jar containing the class, then copy the files in directory matching
     * with the pattern and save to path resolved to relative
     * 
     * The relative is processed as a template
     * 
     * @param clazz
     * @param directory
     * @param pattern
     * @param path
     * @param relative
     * @param model
     */
    public void copyDir(Class clazz, String directory, String pattern, String relative, Object model);

    /**
     * If resourceOrTemplate ends with '.ftl' then calls the copyTemplate method
     * Else calls the copy method
     * 
     * @param resourceOrTemplate
     * @param path
     * @param relative
     * @param model
     * @return the file if created
     */
    public Optional<File> copySmart(String resourceOrTemplate, String relative, Object model);

    /**
     * If resourceOrTemplate ends with '.ftl' then calls the copyTemplate method
     * Else calls the copy method
     * 
     * @param template
     * @param path
     * @param function
     * @param model
     * @return the file if created
     */
    public Optional<File> copySmart(String template, Function<String, String> function, Object model);


}