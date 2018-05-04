package mx.infotec.dads.kukulkan.shell.util;

import mx.infotec.dads.kukulkan.shell.commands.GraphsArgs;
import mx.infotec.dads.kukulkan.shell.generator.GraphsContext;

/**
 * Mapper for Command Args to Command Context
 *
 *
 */
public class MapperG {

    private MapperG() {

    }

    public static GraphsContext toContext(GraphsArgs params) {
        GraphsContext context = new GraphsContext();
        return context;
    }
}
