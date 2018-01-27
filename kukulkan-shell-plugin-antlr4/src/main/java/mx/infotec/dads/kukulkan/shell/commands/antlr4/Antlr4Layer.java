package mx.infotec.dads.kukulkan.shell.commands.antlr4;

import static mx.infotec.dads.kukulkan.metamodel.util.Validator.requiredNotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.infotec.dads.kukulkan.engine.templating.service.TemplateService;
import mx.infotec.dads.kukulkan.metamodel.context.GeneratorContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.generator.Layer;

@Component
public class Antlr4Layer implements Layer {

    /** The template service. */
    @Autowired
    private TemplateService templateService;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void process(GeneratorContext context) {
        Antlr4Context antlrContext = requiredNotEmpty(context.get(Antlr4Context.class));
    }
}
