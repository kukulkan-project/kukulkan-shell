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
package mx.infotec.dads.kukulkan.shell.config;

import static mx.infotec.dads.kukulkan.shell.util.TextFormatter.defaulBasePrompt;
import static mx.infotec.dads.kukulkan.shell.util.TextFormatter.defaulEndPrompt;

import javax.annotation.PostConstruct;

import org.jline.utils.AttributedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.event.message.LocationUpdatedEvent;
import mx.infotec.dads.kukulkan.shell.services.PromptLocationtUpdateService;

/**
 * KukulkanPrompt Provided: * A provider that sets the shell prompt to
 * 'kukulkan'.
 *
 * @author Daniel Cortes Pichardo
 */
@Component
public class KukulkanPromptProvided implements PromptProvider {

    /** The nav. */
    @Autowired
    private Navigator nav;

    /** The prompt service. */
    @Autowired
    private PromptLocationtUpdateService promptLocationtUpdateService;

    /** The prompt. */
    private AttributedString prompt;

    /** The base prompt. */
    private AttributedString basePrompt;

    /** The end prompt. */
    private AttributedString endPrompt;

    /**
     * Inits the.
     */
    @PostConstruct
    private void init() {
        basePrompt = defaulBasePrompt();
        endPrompt = defaulEndPrompt();
        prompt = promptLocationtUpdateService.createPrompt(nav.getCurrentPath(), basePrompt, endPrompt);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.shell.jline.PromptProvider#getPrompt()
     */
    @Override
    public AttributedString getPrompt() {
        return prompt;
    }

    /**
     * Handle.
     *
     * @param event
     *            the event
     */
    @EventListener
    public void handle(LocationUpdatedEvent event) {
        switch (event.getEventType()) {
        case FILE_NAVIGATION:
            prompt = promptLocationtUpdateService.createPrompt(nav.getCurrentPath(), basePrompt, endPrompt);
            break;
        default:
            break;
        }
    }

}
