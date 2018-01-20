package mx.infotec.dads.kukulkan.shell.domain;

import java.io.Serializable;

import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;

/**
 * KukulkanShellContext
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class KukulkanShellContext implements Serializable {

    private static final long serialVersionUID = 1L;

    private ProjectConfiguration configuration;

    public KukulkanShellContext() {

    }

    public KukulkanShellContext(ProjectConfiguration configuration) {
        this.configuration = configuration;
    }

    public ProjectConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ProjectConfiguration configuration) {
        this.configuration = configuration;
    }
}
