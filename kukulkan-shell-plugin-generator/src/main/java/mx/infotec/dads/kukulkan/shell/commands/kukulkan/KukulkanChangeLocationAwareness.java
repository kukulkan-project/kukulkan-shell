package mx.infotec.dads.kukulkan.shell.commands.kukulkan;

import java.nio.file.Path;
import java.util.Optional;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.stereotype.Component;

import mx.infotec.dads.kukulkan.shell.prompt.event.ChangeLocationAwareness;
import mx.infotec.dads.kukulkan.shell.util.FilesCommons;

/**
 * Change Location Awareness for kukulkan projects
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Component
public class KukulkanChangeLocationAwareness implements ChangeLocationAwareness {

    @Override
    public Optional<AttributedString> addPrompt(Path currentLocation) {
        if (FilesCommons.hasKukulkanFile(currentLocation)) {
            AttributedString dirPrompt = AttributedString.join(new AttributedString(""),
                    new AttributedString("❂", AttributedStyle.BOLD.foreground(AttributedStyle.GREEN)),
                    new AttributedString(" kukulkan project",
                            AttributedStyle.BOLD_OFF.foreground(AttributedStyle.GREEN)));
            return Optional.of(dirPrompt);
        } else {
            return Optional.empty();
        }
    }
}
