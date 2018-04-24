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

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * Util Commands.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class KukulkanInfo {

    /**
     * Info.
     *
     * @return the string
     */
    @ShellMethod("Show the version of Kukulkan Shell")
    public String info() {
        return "v1.0.0";
    }

    /**
     * Archetypes.
     *
     * @return the string
     * @throws InterruptedException the interrupted exception
     */
    @ShellMethod("Show a list of supported archetypes")
    public String archetypes() throws InterruptedException {
        StringBuilder sb = new StringBuilder();
        sb.append("Angular 4 - Spring - JPA").append("\n");
        sb.append("Angular 4 - Spring - Mongo").append("\n");
        sb.append("Angular 1.5.8 - Spring - JPA").append("\n");
        sb.append("Angular 1.5.8 - Spring - Mongo").append("\n");
        return sb.toString();
    }

    /**
     * Copyright.
     *
     * @return the string
     */
    @ShellMethod("Show the copyright")
    public String copyright() {
        StringBuilder sb = new StringBuilder();
        sb.append("@CONACYT - INFOTEC").append("\n");
        return sb.toString();
    }

    /**
     * Contact.
     *
     * @return the string
     */
    @ShellMethod("Show the contact information")
    public String contact() {
        StringBuilder sb = new StringBuilder();
        sb.append("Daniel Cortes Pichardo").append("\n");
        sb.append("daniel.cortes@infotec.mx").append("\n");
        sb.append("https://github.com/danimaniarqsoft/kukulkan-shell");
        return sb.toString();
    }
}
