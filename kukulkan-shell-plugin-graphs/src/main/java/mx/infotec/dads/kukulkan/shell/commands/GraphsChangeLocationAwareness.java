package mx.infotec.dads.kukulkan.shell.commands;

import java.nio.file.Path;
import java.util.Optional;

import org.jline.utils.AttributedString;
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
public class GraphsChangeLocationAwareness extends AbstractChangeLocationAwareness {

    @Autowired
    private KukulkanShellContext context;

    @Override
    public Optional<AttributedString> createPrompt(Path currentLocation) {
        // insert your prompt modifier here
        return Optional.empty();
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
