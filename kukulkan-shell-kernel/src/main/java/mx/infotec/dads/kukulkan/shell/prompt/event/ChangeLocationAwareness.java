package mx.infotec.dads.kukulkan.shell.prompt.event;

import java.nio.file.Path;

import org.jline.utils.AttributedString;

/**
 * Change Location Awareness interface, It is used for change location event
 * registry.
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@FunctionalInterface
public interface ChangeLocationAwareness {

    AttributedString addPrompt(Path currentLocation);

}
