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
public interface ChangeLocationAwareness {

    boolean isProccesable(Path currentLocation);

    void doAction(Path currentLocation);

    Optional<AttributedString> createPrompt(Path currentLocation);

}
