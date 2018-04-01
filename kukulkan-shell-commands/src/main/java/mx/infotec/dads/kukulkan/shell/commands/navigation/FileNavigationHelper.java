package mx.infotec.dads.kukulkan.shell.commands.navigation;

import static mx.infotec.dads.kukulkan.shell.util.TextFormatter.formatDirNotExistText;
import static mx.infotec.dads.kukulkan.shell.util.TextFormatter.formatNormalText;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.jline.utils.AttributedString;
import org.unix4j.unix.Ls;
import org.unix4j.unix.ls.LsOptionSet_Rahlrt;

import mx.infotec.dads.kukulkan.shell.component.Navigator;
import mx.infotec.dads.kukulkan.shell.util.GeneratorException;

/**
 * File Navigation Helper, It is used for do common operations in the
 * FileNavigator Command
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class FileNavigationHelper {

    private FileNavigationHelper() {

    }

    public static Path convertToPath(String dir) {
        Path toChange = null;
        if ("@home".equals(dir)) {
            toChange = Paths.get(System.getProperty("user.home"));
        } else {
            toChange = Paths.get(dir);
        }
        return toChange;
    }

    /**
     * Validate new path.
     *
     * @param newPath
     *            the new path
     * @return the attributed string
     */
    public static AttributedString processActions(Path newPath, Navigator nav) {
        if (newPath.toFile().exists()) {
            nav.setCurrentPath(newPath);
            // publisher.publishEvent(new
            // LocationUpdatedEvent(EventType.FILE_NAVIGATION));
            return formatNormalText(newPath.toString());
        } else {
            return formatDirNotExistText(newPath.toString());
        }
    }

    /**
     * Gets the new path.
     *
     * @param toChange
     *            the to change
     * @return the new path
     */
    public static Path calculateNewPath(String dir, Navigator nav) {
        Path toChange = convertToPath(dir);
        Path newPath;
        if ("..".equals(toChange.toString())) {
            newPath = nav.getCurrentPath().getParent();
        } else if (toChange.isAbsolute()) {
            newPath = toChange;
        } else {
            newPath = Paths.get(nav.getCurrentPath().toAbsolutePath().toString(), toChange.toString());
        }
        return newPath;
    }

    public static LsOptionSet_Rahlrt parseOptions(String params) {
        switch (codeInput(params)) {
        case 338:
            return Ls.Options.l.r.t;
        case 222:
            return Ls.Options.l.r;
        case 224:
            return Ls.Options.l.t;
        case 108:
            return Ls.Options.l;
        default:
            throw new GeneratorException("invalid input");
        }
    }

    public static int codeInput(String params) {
        char[] options = params.toCharArray();
        int count = 0;
        for (char c : options) {
            count = count + c;
        }
        return count;
    }
}
