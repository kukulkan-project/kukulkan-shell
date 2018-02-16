/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2016 Daniel Cortes Pichardo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package mx.infotec.dads.kukulkan.shell.commands.git;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.jline.utils.AttributedString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.domain.NativeCommand;
import mx.infotec.dads.kukulkan.shell.domain.NativeCommandContext;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.event.message.EventType;
import mx.infotec.dads.kukulkan.shell.event.message.LocationUpdatedEvent;
import mx.infotec.dads.kukulkan.shell.services.CommandService;
import mx.infotec.dads.kukulkan.shell.util.TextFormatter;

/**
 * Docker Commands.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class GitCommands {

	private static final Logger LOGGER = LoggerFactory.getLogger(GitCommands.class);

	/** The command service. */
	@Autowired
	CommandService commandService;

	/** The Constant GIT_COMMAND. */
	public static final String GIT_COMMAND = "git";

	private static final String DEVELOP_BRANCH = "develop";

	private static final String FEATURE_PREFIX = "feature-";

	private static final String RELEASE_PREFIX = "release-";

	private static final String CHECKOUT = "checkout";

	/** The project context. */
	@Autowired
	NativeCommandContext projectContext;

	/** The nav. */
	@Autowired
	Navigator nav;

	/** The publisher. */
	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * Git status.
	 *
	 * @return the list
	 */
	@ShellMethod("Show the status of the current git project")
	public List<AttributedString> gitStatus() {
		List<CharSequence> exec = commandService.exec(new ShellCommand(GIT_COMMAND, "status"));
		publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
		return TextFormatter.formatToGitOutput(exec);
	}

	/**
	 * Git create feature.
	 *
	 * @param name
	 *            the name
	 */
	@ShellMethod("Create a new Feature")
	public void gitCreateFeature(@NotNull String name) {
		commandService.exec(new ShellCommand(GIT_COMMAND, CHECKOUT, "-b", FEATURE_PREFIX + name, DEVELOP_BRANCH));
		publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
	}

	/**
	 * Git terminate feature.
	 *
	 * @param name
	 *            the name
	 */
	@ShellMethod("Terminate a Feature")
	public void gitTerminateFeature(@NotNull String name) {
		commandService.exec(new ShellCommand(GIT_COMMAND, CHECKOUT, DEVELOP_BRANCH));
		commandService.exec(new ShellCommand(GIT_COMMAND, "merge", "--no-ff", name));
		commandService.exec(new ShellCommand(GIT_COMMAND, "branch", "-d", FEATURE_PREFIX + name));
		publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
	}

	/**
	 * Git publish feature.
	 *
	 * @param name
	 *            the name
	 */
	@ShellMethod("Publish a Feature to a remote server")
	public void gitPublishFeature(@NotNull String name) {
		commandService.exec(new ShellCommand(GIT_COMMAND, "push", "origin", FEATURE_PREFIX + name));
		publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
	}

	/**
	 * Git create release.
	 *
	 * @param name
	 *            the name
	 */
	@ShellMethod("Create a new Release")
	public void gitCreateRelease(@NotNull String name) {
		commandService.exec(new ShellCommand(GIT_COMMAND, CHECKOUT, "-b", RELEASE_PREFIX + name, DEVELOP_BRANCH));
		publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
	}

	/**
	 * Git terminate release.
	 *
	 * @param name
	 *            the name
	 */
	@ShellMethod("Terminate a Release")
	public void gitTerminateRelease(@NotNull String name) {
		commandService.exec(new ShellCommand(GIT_COMMAND, CHECKOUT, DEVELOP_BRANCH));
		commandService.exec(new ShellCommand(GIT_COMMAND, "merge", "--no-ff", RELEASE_PREFIX + name));
		commandService.exec(new ShellCommand(GIT_COMMAND, "branch", "-d", RELEASE_PREFIX + name));
		publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
	}

	/**
	 * Git publish release.
	 *
	 * @param name
	 *            the name
	 */
	@ShellMethod("Publish a Release to a remote server")
	public void gitPublishRelease(@NotNull String name) {
		commandService.exec(new ShellCommand(GIT_COMMAND, "push", "origin", DEVELOP_BRANCH));
		publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
	}

	/**
	 * Docker show running process availability.
	 *
	 * @return the availability
	 */
	@ShellMethodAvailability({ "gitCreateFeature", "gitTerminateFeature", "gitPublishFeature", "gitCreateRelease",
			"gitTerminateRelease", "gitPublishRelease" })
	public Availability dockerShowRunningProcessAvailability() {
		NativeCommand gitCmd = projectContext.getAvailableCommands().get(GIT_COMMAND);
		if (gitCmd != null && gitCmd.isActive()) {
			return Availability.available();
		} else {
			return Availability.unavailable("you must install git");
		}
	}
}
