package mx.infotec.dads.kukulkan.shell.util;

import mx.infotec.dads.kukulkan.shell.commands.Antlr4Args;
import mx.infotec.dads.kukulkan.shell.generator.Antlr4Context;

/**
 * Mapper for Command Args to Command Context
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class Mapper {

    private Mapper() {

    }

    public static Antlr4Context toContext(Antlr4Args params) {
        Antlr4Context context = new Antlr4Context();
        context.setId(params.getId());
        context.setGrammarExtension(params.getGrammarExtension());
        context.setGrammarName(params.getGrammarName());
        context.setPackaging(params.getPackaging());
        return context;
    }
}
