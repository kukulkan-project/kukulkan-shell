package mx.infotec.dads.kukulkan.shell.commands.kukulkan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;

import mx.infotec.dads.kukulkan.metamodel.foundation.Database;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.shell.commands.AbstractCommand;
import mx.infotec.dads.kukulkan.shell.commands.git.service.GitCommandsService;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.services.PrintService;
import mx.infotec.dads.kukulkan.shell.services.WriterHelper;

@ShellComponent
public class HerokuCommands extends AbstractCommand {

	private static final String PROCFILE = "Procfile";

	private static final String HEROKU = "heroku";

	@Autowired
	private GitCommandsService git;

	@Autowired
	private WriterHelper writer;

	@Autowired
	private PrintService printer;

	@ShellMethodAvailability("addHerokuSupportAvailability")
	@ShellMethod("Add Heroku support to project and commit")
	public void addHerokuSupport(@ShellOption(help = "Deploy to heroku") boolean deploy,
			@ShellOption(help = "The name for the app on Heroku. If already exists then heroku will use a random name", defaultValue = "") String herokuAppName) {
		if (navigator.getCurrentPath().resolve(PROCFILE).toFile().exists()) {
			printer.error("File " + PROCFILE + " already exists!");
			return;
		}
		writer.copy(PROCFILE, PROCFILE);
		git.add(true, PROCFILE);
		git.commit("Add Heroku support", "Kukulkan Team <suport@kukulkan.org.mx>");
		if (StringUtils.isEmpty(herokuAppName)) {
			herokuAppName = buildHerokuAppName(shellContext.getConfiguration().get());
		}
		if (!commandService.execToConsole(new ShellCommand(HEROKU, "create", herokuAppName))) {
			printer.error(String.format("Failed to create heroku app with name %s", herokuAppName));
			printer.info("Creating heroku app with random name...");
			commandService.execToConsole(new ShellCommand(HEROKU, "create"));
		}
		if (deploy) {
			deployToHeroku();
		}
	}

	@ShellMethod("Deploy project to Heroku")
	public void deployToHeroku() {
		commandService.execToConsole(new ShellCommand("git", "push", HEROKU, "HEAD:master"));
	}

	public Availability addHerokuSupportAvailability() {
		if (!shellContext.getConfiguration().isPresent()) {
			return Availability.unavailable("current directory is not a Kukulkán Project");
		}
		if (shellContext.getConfiguration().isPresent()) {
			Database db = shellContext.getConfiguration().get().getTargetDatabase();
			if (!DatabaseType.SQL_MYSQL.equals(db.getDatabaseType())) {
				return Availability
						.unavailable(String.format("%s database type is not supported", db.getDatabaseType()));
			}
		}
		return Availability.available();
	}

	private String buildHerokuAppName(ProjectConfiguration pConf) {
		String[] packageParts = pConf.getPackaging().split("\\.");
		return String.format("%s-%s", packageParts[packageParts.length - 1], pConf.getId());
	}

}
