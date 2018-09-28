package mx.infotec.dads.kukulkan.shell.commands.files;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Pattern;

public class ListFiles extends SimpleFileVisitor<Path> {

    final Pattern p = Pattern.compile(".*\\.md$");

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (p.matcher(file.toAbsolutePath().toString()).matches()) {
            System.out.println(file.toAbsolutePath().toString());
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
//        if (FileUtil.isExcludedFolder(dir.getFileName().toString())) {
//            return FileVisitResult.SKIP_SUBTREE;
//        } else {
//            return FileVisitResult.CONTINUE;
//        }
        return FileVisitResult.CONTINUE;
    }
}