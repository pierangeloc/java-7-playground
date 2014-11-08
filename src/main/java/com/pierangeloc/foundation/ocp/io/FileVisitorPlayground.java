package com.pierangeloc.foundation.ocp.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by pierangeloc on 8-11-14.
 */
public class FileVisitorPlayground {

    public static void walkTree(String path) throws IOException {
        //walk the whole file tree under path
        System.out.println("walking tree under path: " + path);
        Files.walkFileTree(Paths.get(path), new EchoFileVisitor());

    }

    public static void main(String[] args) throws IOException {
        String path = ".";
        walkTree(path);
    }


    }

class EchoFileVisitor extends SimpleFileVisitor<Path> {
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("preVisitDirectory: " + dir + "; attrs: " + attrs);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println("visitFile: " + file + "; attrs: " + attrs);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("visitFileFailed: " + file + "; exception: " + exc);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println("postVisitDirectory: " + dir + "; exception: " + exc);
        return FileVisitResult.CONTINUE;
    }
}
