
package mx.dads.infotec.archetype;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;

import mx.dads.infotec.${project.id}.${grammar.name}Lexer;
import mx.dads.infotec.${project.id}.${grammar.name}Parser;

/**
 * This main class is for grammar testing
 * 
 * @author ${project.id?cap_first} Generated
 *
 */
public class Main {

    private static final String EXTENSION = "${grammar.extension}";

    public static void main(String[] args) throws IOException {
        String program = args.length > 1 ? args[1] : "test/test." + EXTENSION;

        System.out.println("Interpreting file " + program);

        ${grammar.name}Lexer lexer = new ${grammar.name}Lexer(new ANTLRFileStream(program));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ${grammar.name}Parser parser = new ${grammar.name}Parser(tokens);

        ${grammar.name}Parser.StartContext tree = parser.start();

        ${grammar.name}CustomVisitor visitor = new ${grammar.name}CustomVisitor();
        visitor.visit(tree);

        System.out.println("Interpretation finished");
    }
}
