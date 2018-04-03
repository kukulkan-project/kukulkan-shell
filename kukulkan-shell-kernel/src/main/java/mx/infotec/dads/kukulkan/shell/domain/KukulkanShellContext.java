package mx.infotec.dads.kukulkan.shell.domain;

import java.util.Optional;

import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.shell.prompt.Resettable;

/**
 * KukulkanShellContext
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class KukulkanShellContext implements Resettable {

    private static final long serialVersionUID = 1L;

    private transient Optional<ProjectConfiguration> configuration;

    public KukulkanShellContext() {

    }

    public KukulkanShellContext(Optional<ProjectConfiguration> configuration) {
        this.configuration = configuration;
    }

    public Optional<ProjectConfiguration> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Optional<ProjectConfiguration> configuration) {
        this.configuration = configuration;
    }

    @Override
    public void reset() {
        this.setConfiguration(Optional.empty());
    }
}
