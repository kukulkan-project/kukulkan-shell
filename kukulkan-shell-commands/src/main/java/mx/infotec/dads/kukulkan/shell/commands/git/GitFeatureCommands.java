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

import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.CHECKOUT;
import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.DEVELOP_BRANCH;
import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.FEATURE_PREFIX;
import static mx.infotec.dads.kukulkan.shell.commands.git.GitHelper.GIT_COMMAND;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import mx.infotec.dads.kukulkan.shell.commands.git.validation.GitValidation;
import mx.infotec.dads.kukulkan.shell.commands.git.valueprovided.GitValueProvider;
import mx.infotec.dads.kukulkan.shell.domain.ShellCommand;
import mx.infotec.dads.kukulkan.shell.event.message.EventType;
import mx.infotec.dads.kukulkan.shell.event.message.LocationUpdatedEvent;

/**
 * Docker Commands.
 *
 * @author Daniel Cortes Pichardo
 */
@ShellComponent
public class GitFeatureCommands extends GitBaseCommands {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitFeatureCommands.class);

    @ShellMethod("Create a new Feature")
    public void gitFeatureList() {
        commandService.exec(new ShellCommand(GIT_COMMAND, CHECKOUT, "-b", FEATURE_PREFIX, DEVELOP_BRANCH));
        publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
    }

    /**
     * Git create feature.
     *
     * @param name
     *            the name
     */
    @ShellMethod("Create a new Feature")
    public void gitFeatureStart(@NotNull String name) {
        GitValidation.validateBranchName(name);
        GitValidation.validateDevelopBranch(gitContext);
        commandService.exec(new ShellCommand(GIT_COMMAND, CHECKOUT, "-b", FEATURE_PREFIX + name, DEVELOP_BRANCH));
        publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
    }

    /**
     * Git terminate feature.
     *
     * @param featureBranchName
     *            the name
     */
    @ShellMethod("Terminate a Feature")
    public List<CharSequence> gitFeatureFinish(
            @ShellOption(valueProvider = GitValueProvider.GitFeatureValueProvider.class) @NotNull String featureBranchName) {
        GitHelper.validateFeatureBranch(featureBranchName, "The branch name is no a valid <feature> branch name");
        GitHelper.validateFeatureBranch(gitContext.getCurrentBranchName());
        List<CharSequence> exec = commandService.exec(new ShellCommand(GIT_COMMAND, CHECKOUT, DEVELOP_BRANCH));
        exec.addAll(commandService.exec(new ShellCommand(GIT_COMMAND, "merge", "--no-ff", featureBranchName)));
        exec.addAll(commandService.exec(new ShellCommand(GIT_COMMAND, "branch", "-d", featureBranchName)));
        publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
        return exec;
    }

    /**
     * Git publish feature.
     *
     * @param name
     *            the name
     */
    @ShellMethod("Publish a Feature to a remote server")
    public void gitFeaturePublish() {
        LOGGER.debug(LOGGER_EXEC, GIT_COMMAND, gitContext.getCurrentBranchName());
        GitHelper.validateFeatureBranch(gitContext.getCurrentBranchName());
        commandService.exec(new ShellCommand(GIT_COMMAND, "push", "origin").addArg(gitContext.getCurrentBranchName()));
        publisher.publishEvent(new LocationUpdatedEvent(EventType.FILE_NAVIGATION));
    }

    @ShellMethod("Publish a Feature to a remote server")
    public void gitFeatureTrack(@NotNull String name) {

    }

    @ShellMethod("Publish a Feature to a remote server")
    public void gitFeatureDiff(@NotNull String name) {
        GitValidation.notYetImplemented();
    }

    @ShellMethod("Publish a Feature to a remote server")
    public void gitFeatureRebase(@NotNull String name) {
        GitValidation.notYetImplemented();
    }

    @ShellMethod("Publish a Feature to a remote server")
    public void gitFeatureCheckout(@NotNull String name) {
        GitValidation.notYetImplemented();
    }

    @ShellMethod("Publish a Feature to a remote server")
    public void gitFeaturePull(@NotNull String name) {
        GitValidation.notYetImplemented();
    }
}
