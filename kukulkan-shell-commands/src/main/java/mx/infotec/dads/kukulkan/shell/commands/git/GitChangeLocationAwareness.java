package mx.infotec.dads.kukulkan.shell.commands.git;

import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.GIT_COMMAND;

import java.nio.file.Path;
import java.util.Optional;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.prompt.event.AbstractChangeLocationAwareness;
import mx.infotec.dads.kukulkan.shell.services.impl.CommandServiceImpl;
import mx.infotec.dads.kukulkan.shell.util.FilesCommons;

@Component
public class GitChangeLocationAwareness extends AbstractChangeLocationAwareness {

    /** The command service. */
    @Autowired
    private CommandServiceImpl commandService;
    @Autowired
    private GitContext context;

    @Override
    public Optional<AttributedString> createPrompt(Path currentLocation) {
        String branchName = commandService.exec(currentLocation,
                new ShellCommand(GIT_COMMAND).addArg("rev-parse").addArg("--abbrev-ref").addArg("HEAD"),
                line -> Optional.ofNullable(new AttributedString(line))).get(0).toString();
        
        int color;
        
        context.setMaster(false);
        context.setDevelop(false);

        switch (branchName) {
            case "master":
                context.setMaster(true);
                color = AttributedStyle.RED;
                break;
            case "develop":
                context.setDevelop(true);
                color = AttributedStyle.CYAN;
                break;
            default:
                color = AttributedStyle.MAGENTA;
        }

        AttributedString dirPrompt = AttributedString.join(new AttributedString(""),
                new AttributedString("@", AttributedStyle.BOLD.foreground(AttributedStyle.YELLOW)),
                new AttributedString("git/", AttributedStyle.BOLD_OFF.foreground(AttributedStyle.YELLOW)),
                new AttributedString(branchName, AttributedStyle.BOLD.foreground(color)));
        
        context.setCurrentBranchName(branchName);
        return Optional.of(dirPrompt);
    }

    @Override
    public boolean isProccesable(Path currentLocation) {
        return FilesCommons.hasGitFiles(currentLocation);
    }
}
