package mx.infotec.dads.kukulkan.shell.prompt.event;

import java.nio.file.Path;
import java.util.Optional;

import org.jline.utils.AttributedString;

/**
 * Change Location Awareness interface, It is used for change location event
 * registry.
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public abstract class AbstractChangeLocationAwareness implements ChangeLocationAwareness {

    @Override
    public Optional<AttributedString> createPrompt(Path currentLocation) {
        return Optional.empty();
    }

    @Override
    public void doAction(Path currentLocation) {
        // must be implemented in subclass
    }
}
