package mx.infotec.dads.kukulkan.shell.commands.kukulkan;

import java.nio.file.Path;
import java.util.Optional;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.infotec.dads.kukulkan.shell.domain.KukulkanShellContext;
import mx.infotec.dads.kukulkan.shell.prompt.event.AbstractChangeLocationAwareness;
import mx.infotec.dads.kukulkan.shell.util.FilesCommons;
import mx.infotec.dads.kukulkan.shell.util.ProjectUtil;

/**
 * Change Location Awareness for kukulkan projects
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Component
public class AppChangeLocationAwareness extends AbstractChangeLocationAwareness {

    @Autowired
    private KukulkanShellContext context;

    @Override
    public Optional<AttributedString> createPrompt(Path currentLocation) {
        AttributedString dirPrompt = AttributedString.join(new AttributedString(""),
                new AttributedString("@", AttributedStyle.BOLD.foreground(AttributedStyle.GREEN)),
                new AttributedString("app", AttributedStyle.BOLD_OFF.foreground(AttributedStyle.GREEN)));
        return Optional.of(dirPrompt);
    }

    @Override
    public void doAction(Path currentLocation) {
        context.setConfiguration(ProjectUtil.readKukulkanFile(currentLocation));
    }

    @Override
    public boolean isProccesable(Path currentLocation) {
        return FilesCommons.hasKukulkanFile(currentLocation);
    }
}
