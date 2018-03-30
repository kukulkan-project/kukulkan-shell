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
package mx.infotec.dads.kukulkan.shell.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.CompletionProposal;

import mx.infotec.dads.kukulkan.shell.domain.ShellCompletionProposal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FilesCommon operations.
 *
 * @author Daniel Cortes Pichardo
 */
public final class FilesCommons {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilesCommons.class);

    /**
     * Instantiates a new files commons.
     */
    private FilesCommons() {
    }

    /**
     * Filter files.
     *
     * @param currentPath
     *            the current path
     * @return the list
     */
    public static List<CompletionProposal> filterFiles(Path currentPath) {
        List<CompletionProposal> completionProposal = new ArrayList<>();
        try (DirectoryStream<Path> directories = Files.newDirectoryStream(currentPath);) {
            for (Path path : directories) {
                if (!path.toFile().isDirectory()) {
                    completionProposal.add(new ShellCompletionProposal(path.getFileName().toString(), "file"));
                } else {
                    completionProposal.add(new ShellCompletionProposal(path.getFileName().toString(), "directory"));
                }
            }
        } catch (IOException e) {
            LOGGER.debug("Error at filter files", e);
        }
        return completionProposal;
    }

    /**
     * Show files.
     *
     * @param currentPath
     *            the current path
     * @return the list
     */
    public static List<AttributedString> showFiles(Path currentPath) {
        List<AttributedString> fileList = new ArrayList<>();
        fileList.add(new AttributedString(""));
        try (DirectoryStream<Path> directories = Files.newDirectoryStream(currentPath);) {
            for (Path path : directories) {
                if (!path.toFile().isDirectory()) {
                    fileList.add(
                            new AttributedStringBuilder()
                                    .append("f ", AttributedStyle.BOLD.foreground(AttributedStyle.WHITE))
                                    .append(path.getFileName().toString(),
                                            AttributedStyle.BOLD.foreground(AttributedStyle.WHITE))
                                    .toAttributedString());
                } else {
                    fileList.add(
                            new AttributedStringBuilder()
                                    .append("d ", AttributedStyle.BOLD.foreground(AttributedStyle.WHITE))
                                    .append(path.getFileName().toString(),
                                            AttributedStyle.BOLD.foreground(AttributedStyle.BLUE))
                                    .toAttributedString());
                }
            }
        } catch (IOException e) {
            LOGGER.debug("Error at show files", e);
        }
        fileList.add(new AttributedString(""));
        return fileList;
    }

    /**
     * Filter dirs.
     *
     * @param currentPath
     *            the current path
     * @return the list
     */
    public static List<CompletionProposal> filterDirs(Path currentPath) {
        return filterDirs(null, currentPath);
    }

    /**
     * Filter dirs.
     *
     * @param pathToSearch
     *            the current path
     * @return the list
     */
    public static List<CompletionProposal> filterDirs(Path prepend, Path pathToSearch) {
        List<CompletionProposal> completionProposal = new ArrayList<>();
        try (DirectoryStream<Path> directories = Files.newDirectoryStream(pathToSearch);) {
            for (Path path : directories) {
                if (path.toFile().isDirectory()) {
                    Path valuePath = null;
                    if (prepend != null) {
                        valuePath = prepend.resolve(path.getFileName());
                    } else {
                        valuePath = path.getFileName();
                    }
                    completionProposal.add(
                            new ShellCompletionProposal(valuePath.toString() + "/", path.getFileName().toString()));
                }
            }
        } catch (IOException e) {
            LOGGER.debug("Error at filter dir", e);
        }
        return completionProposal;
    }

    /**
     * Checks for git files.
     *
     * @param currentPath
     *            the current path
     * @return true, if successful
     */
    public static boolean hasGitFiles(Path currentPath) {
        try (DirectoryStream<Path> directories = Files.newDirectoryStream(currentPath)) {
            for (Path path : directories) {
                if (path.toFile().isDirectory() && path.toFile().getName().equals(".git")) {
                    return true;
                }
            }
        } catch (IOException e) {
            LOGGER.debug("Error at get git files", e);
        }
        return false;
    }

    /**
     * Checks for git files.
     *
     * @param currentPath
     *            the current path
     * @return true, if successful
     */
    public static boolean hasKukulkanFile(Path currentPath) {
        File[] files = currentPath.toFile().listFiles();
        for (File file : files) {
            if (file.getName().equals(".kukulkan.json")) {
                return true;
            }
        }
        return false;
    }
}
