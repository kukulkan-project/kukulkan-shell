package mx.infotec.dads.kukulkan.shell.prompt;

import java.io.Serializable;

/**
 * Cleaneable interface, it is used by
 * {@link mx.infotec.dads.kukulkan.shell.prompt.KukulkanPromptProvided} for
 * clean objects before to process it.
 * 
 * @author Daniel Cortes Pichardo
 *
 */

@FunctionalInterface
public interface Resettable extends Serializable {

    public void reset();
}
