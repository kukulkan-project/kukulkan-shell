package mx.infotec.dads.kukulkan.shell.commands.maven;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import mx.infotec.dads.kukulkan.shell.domain.Line;
import mx.infotec.dads.kukulkan.shell.domain.NativeCommandContext;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.services.CommandService;
import mx.infotec.dads.kukulkan.shell.util.TextFormatter;

/**
 * Docker Commands
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@ShellComponent
public class MavenCommands {

    public static final String MVN_COMMAND = "mvn";

    @Autowired
    NativeCommandContext projectContext;

    @Autowired
    CommandService commandService;

    /**
     * Configurate a front end application, It execute the command "mvn package
     * -Pprod -DskiptTests"
     */
    @ShellMethod("Configurate a initial Archetype")
    public void mvnConfigFrontEnd() {
        commandService.exec(new ShellCommand(MVN_COMMAND).addArg("package").addArg("-Pprod").addArg("-DskipTests"),
                line -> {
                    commandService.printf(TextFormatter.formatLogText(line).toAnsi());
                    return Optional.ofNullable(new Line(line));
                });
    }
}
