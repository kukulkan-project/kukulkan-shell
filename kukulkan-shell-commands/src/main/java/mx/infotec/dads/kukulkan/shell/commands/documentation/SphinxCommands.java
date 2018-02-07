package mx.infotec.dads.kukulkan.shell.commands.documentation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.validation.constraints.NotNull;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.domain.NativeCommand;
import mx.infotec.dads.kukulkan.shell.domain.NativeCommandContext;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.services.CommandService;

@ShellComponent
public class SphinxCommands {

	private static final Logger LOGGER = LoggerFactory.getLogger(SphinxCommands.class);

	/** The command service. */
	@Autowired
	CommandService commandService;

	/** The Constant GIT_COMMAND. */
	public static final String SPHINX_COMMAND = "sphinx-quickstart";

	/** The project context. */
	@Autowired
	NativeCommandContext projectContext;

	/** The nav. */
	@Autowired
	Navigator nav;

	@ShellMethod("Generate a documentation static site")
	public void initDocs(@NotNull String project, @NotNull String author, Float version, Float release, String lang) {
		copyResources();
		ShellCommand command = prepareCommand(project, author, version, release, lang);
		commandService.exec(command);
		commandService.exec(new ShellCommand("make").addArg("--directory").addArg("docs").addArg("html"));
	}

	@ShellMethodAvailability({ "initDocs" })
	public Availability sphinxAvailability() {
		NativeCommand sphinxCmd = projectContext.getAvailableCommands().get(SPHINX_COMMAND + " -v");
		if (sphinxCmd != null && sphinxCmd.isActive()) {
			return Availability.available();
		} else {
			return Availability.unavailable("you must install " + SPHINX_COMMAND);
		}
	}

	private ShellCommand prepareCommand(String project, String author, Float version, Float release, String lang) {
		ShellCommand command = new ShellCommand(SPHINX_COMMAND);
		command.addArg("--quiet").addArg("--sep").addArg("--project").addArg(project).addArg("--author").addArg(author)
				.addArg("-v").addArg(version.toString()).addArg("--release").addArg(release.toString())
				.addArg("--language").addArg(lang).addArg("--makefile").addArg("--batchfile").addArg("-t")
				.addArg(nav.getCurrentPath().toString() + "/docs/template-DADS")
				.addArg(nav.getCurrentPath().toString() + "/docs");
		return command;
	}

	private void copyResources() {
		File src = new File(
				Paths.get("/home/roberto/git/kukulkan-shell/kukulkan-shell-commands/src/main/resources/docs").toUri());
		try {
			FileUtils.copyDirectory(src, Paths.get(nav.getCurrentPath().toString(), "/docs").toFile());
		} catch (IOException e) {
			LOGGER.error("Failed to copy the docs resources");
		}
	}

}
