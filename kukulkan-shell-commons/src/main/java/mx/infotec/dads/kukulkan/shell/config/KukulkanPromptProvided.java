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
import mx.infotec.dads.kukulkan.shell.services.PromptService;

/**
 * KukulkanPrompt Provided: * A provider that sets the shell prompt to
 * 'kukulkan'
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Component
public class KukulkanPromptProvided implements PromptProvider {

    @Autowired
    private Navigator nav;

    @Autowired
    private PromptService promptService;

    private AttributedString prompt;

    private AttributedString basePrompt;

    private AttributedString endPrompt;

    @PostConstruct
    private void init() {
        basePrompt = defaulBasePrompt();
        endPrompt = defaulEndPrompt();
        prompt = promptService.createPrompt(nav.getCurrentPath(), basePrompt, endPrompt);
    }

    @Override
    public AttributedString getPrompt() {
        return prompt;
    }

    @EventListener
    public void handle(LocationUpdatedEvent event) {
        prompt = promptService.createPrompt(nav.getCurrentPath(), basePrompt, endPrompt);
    }

}
