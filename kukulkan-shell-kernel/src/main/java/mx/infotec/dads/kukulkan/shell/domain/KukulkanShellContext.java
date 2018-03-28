package mx.infotec.dads.kukulkan.shell.domain;

import java.io.Serializable;
import java.util.Optional;

import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;

/**
 * KukulkanShellContext
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class KukulkanShellContext implements Serializable {

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
}
