
package ${project.packaging};

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;

import mx.dads.infotec.${project.id}.${project.grammarName}Lexer;
import mx.dads.infotec.${project.id}.${project.grammarName}Parser;

/**
 * This main class is for grammar testing
 * 
 * @author ${project.id?cap_first} Generated
 *
 */
public class Main {

    private static final String EXTENSION = "${project.grammarExtension}";

    public static void main(String[] args) throws IOException {
        String program = args.length > 1 ? args[1] : "test/test." + EXTENSION;

        System.out.println("Interpreting file " + program);

        ${project.grammarName}Lexer lexer = new ${project.grammarName}Lexer(new ANTLRFileStream(program));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ${project.grammarName}Parser parser = new ${project.grammarName}Parser(tokens);

        ${project.grammarName}Parser.StartContext tree = parser.start();

        ${project.grammarName}CustomVisitor visitor = new ${project.grammarName}CustomVisitor();
        visitor.visit(tree);

        System.out.println("Interpretation finished");
    }
}
