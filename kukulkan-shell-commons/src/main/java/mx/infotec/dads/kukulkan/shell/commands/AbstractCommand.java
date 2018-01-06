package mx.infotec.dads.kukulkan.shell.commands;

import org.springframework.beans.factory.annotation.Autowired;

import mx.infotec.dads.kukulkan.engine.service.GenerationService;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.util.KukulkanConfigurationProperties;
import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.services.CommandService;

/**
 * AbstractCommand, It encapsulate the main properties used for most commands
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public abstract class AbstractCommand {

    @Autowired
    protected GenerationService generationService;
    @Autowired
    protected CommandService commandService;
    @Autowired
    protected Navigator navigator;
    @Autowired
    protected KukulkanConfigurationProperties configurationProperties;
    @Autowired
    protected ProjectConfiguration projectConfiguration;
}
