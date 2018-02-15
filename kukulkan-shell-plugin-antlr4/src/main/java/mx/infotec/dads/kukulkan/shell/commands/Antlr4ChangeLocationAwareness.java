package mx.infotec.dads.kukulkan.shell.commands;

import java.nio.file.Path;
import java.util.Optional;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.infotec.dads.kukulkan.shell.domain.KukulkanShellContext;
import mx.infotec.dads.kukulkan.shell.prompt.event.AbstractChangeLocationAwareness;
import mx.infotec.dads.kukulkan.shell.services.CommandService;
import mx.infotec.dads.kukulkan.shell.util.FilesCommons;
import mx.infotec.dads.kukulkan.shell.util.ProjectUtil;

/**
 * Change Location Awareness for kukulkan projects
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Component
public class Antlr4ChangeLocationAwareness extends AbstractChangeLocationAwareness {

    @Autowired
    private KukulkanShellContext context;

    @Autowired
    private CommandService commandService;

    @Override
    public Optional<AttributedString> createPrompt(Path currentLocation) {
        AttributedString dirPrompt = AttributedString.join(new AttributedString(""),
                new AttributedString("❂", AttributedStyle.BOLD.foreground(AttributedStyle.GREEN)),
                new AttributedString(" kukulkan project", AttributedStyle.BOLD_OFF.foreground(AttributedStyle.GREEN)));
        return Optional.of(dirPrompt);
    }

    @Override
    public void doAction(Path currentLocation) {
        context.setConfiguration(ProjectUtil.readKukulkanFile(currentLocation));
        commandService.printf("It is a Kukulkan project");
    }

    @Override
    public boolean isProccesable(Path currentLocation) {
        return FilesCommons.hasKukulkanFile(currentLocation);
    }
}
