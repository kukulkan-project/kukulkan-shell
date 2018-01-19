package mx.infotec.dads.kukulkan.shell.commands.git;

import static mx.infotec.dads.kukulkan.shell.commands.git.GitCommands.GIT_COMMAND;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.prompt.event.ChangeLocationAwareness;
import mx.infotec.dads.kukulkan.shell.services.impl.CommandServiceImpl;
import mx.infotec.dads.kukulkan.shell.util.FilesCommons;

@Component
public class GitChangeLocationAwareness implements ChangeLocationAwareness {

    /** The command service. */
    @Autowired
    private CommandServiceImpl commandService;

    @Override
    public AttributedString addPrompt(Path currentLocation) {
        AttributedString dirPrompt = null;
        if (FilesCommons.hasGitFiles(currentLocation)) {
            List<CharSequence> result = commandService.exec(currentLocation,
                    new ShellCommand(GIT_COMMAND).addArg("rev-parse").addArg("--abbrev-ref").addArg("HEAD"),
                    line -> Optional.ofNullable(new AttributedString(line)));
            dirPrompt = AttributedString.join(new AttributedString(""),
                    new AttributedString(" @", AttributedStyle.BOLD.foreground(AttributedStyle.YELLOW)),
                    new AttributedString("git/" + result.get(0).toString(),
                            AttributedStyle.BOLD_OFF.foreground(AttributedStyle.YELLOW)));
        } else {
            dirPrompt = new AttributedString("");
        }
        return dirPrompt;
    }

}
