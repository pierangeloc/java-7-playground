package com.pierangeloc.foundation.ocp.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

/**
 * Created by pierangeloc on 5-11-14.
 */
public class PathsAndFilesPlayground {

    public static final String TMP = "/tmp";
    public static final String PATHS_DIR = "paths";
    public static final String SUBPATHS_DIR = "subpaths";

    public static void pathsEquivalence() {
        //these are all EQUIVALENT path definitions
        Path tmpPaths = Paths.get("/tmp", "paths");
        Path tmpPaths2 = Paths.get("/tmp/paths");
        Path tmpPaths3 = Paths.get("/tmp/", "/paths");
        Path tmpPaths4 = Paths.get("/tmp", "//////////", "paths");
        Path tmpPaths5WithFileUri = Paths.get(URI.create("file:///tmp/paths"));
        System.out.println(tmpPaths.equals(tmpPaths2));
        System.out.println(tmpPaths2.equals(tmpPaths3));
        System.out.println(tmpPaths3.equals(tmpPaths4));
        System.out.println(tmpPaths4.equals(tmpPaths5WithFileUri));


        //these also are equivalent definitions
        Path relative = Paths.get("parent/child");
        Path relative1 = Paths.get("parent", "child");
        Path relative2 = Paths.get("parent/", "child");
        Path relative3 = Paths.get(URI.create("file:///parent/child"));

        System.out.println(relative.equals(relative1));
        System.out.println(relative1.equals(relative2));
        System.out.println(relative2.equals(relative3));

        //get information out of a path
        Path complexPath = Paths.get("/tmp/parent/child/grandchild/descendant/file.txt");
        System.out.println("Path: " + complexPath);
        System.out.println("Path filename: " + complexPath.getFileName());
        //iterate over names with classic for;
        for(int i = 0; i < complexPath.getNameCount(); i++) {
            System.out.println("Path name[" + i + "] = " + complexPath.getName(i));
        }
        //same effect with an enhanced for (as path is iterable<Path>)
        for(Path p : complexPath) {
            System.out.println("(enhanced) Path name: " + p);
        }

        System.out.println("subpath(1, 3): " + complexPath.subpath(1, 3
        ));
        System.out.println("root : " + complexPath.getRoot());
        System.out.println("parent: " + complexPath.getParent());

        Path relativePath = Paths.get("relative/to/something/else");
        System.out.println("root of a relative path:" + relative.getRoot());
    }

    public static void filesManagement() throws Exception {
        Path parentDir = Paths.get("/tmp/parent/child");
        Files.createDirectories(parentDir);
        Path tmpPaths = Paths.get("/tmp/parent/child/test.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tmpPaths.toFile()));
        writer.write("There's something in the way she moves");
        writer.newLine();
        writer.write("looks my way or calls my name");
        writer.flush();
        writer.close();

        Path tmpPathsCopy = Paths.get("/tmp/parent/child/test-copy.txt");
        Files.copy(tmpPaths, tmpPathsCopy, StandardCopyOption.REPLACE_EXISTING);
        Thread.sleep(10000);
        Files.delete(tmpPaths);
        Files.delete(tmpPathsCopy);

        Path nonExistingFile = Paths.get("/tmp/parent/child/nonexisting.txt");
        try {
            Files.delete(nonExistingFile);
        } catch(Exception e) {
            System.out.println("exception occurred as can't delete an existing file: " + e.getMessage());
        }
        System.out.println("going to deleteIfExists");
        Files.deleteIfExists(nonExistingFile);
    }

    public static void resolveVsRelativizevsNormalize() {
        //path1.resolve(path2) is to merge path2 in the context of path1. It makes sense if path2 is relative,
        //otherwise it just returns the path2
        Path absolute1 = Paths.get("/tmp/parent/child/grandchild/file.txt");

        Path absolute2 = Paths.get("/tmp/parent/child");
        Path relative3 = Paths.get("grandchild/file.txt");
        Path relative4 = Paths.get("src/main/resources");
        System.out.println("absolute1: " + absolute1);
        System.out.println("absolute2: " + absolute2);
        System.out.println("relative3: " + relative3);
        System.out.println("relative4: " + relative4);

        System.out.println("absolute2.resolve(relative3): " + absolute2.resolve(relative3)); //   /tmp/parent/child/grandchild/file.txt
        System.out.println("absolute1.resolve(\"../..\"): " + absolute1.resolve("../..")); //   /tmp/parent/child/grandchild/file.txt
        System.out.println("relative3.resolve(absolute2): " + relative3.resolve(absolute2));
        System.out.println("relative4.resolve(relative3): " + relative4.resolve(relative3));
        System.out.println("relative4.resolve(\"something/else/file.txt\"): " + relative4.resolve("/something/else/file.txt"));

        //path3.relativize(path1) -> path2 such that after normalization we have path1.resolve(path2) == path3
        System.out.println("absolute1.relativize(absolute2): " + absolute1.relativize(absolute2));
        Path relativized = absolute1.relativize(absolute2);
        System.out.println("Path relativized = absolute1.relativize(absolute2): " + relativized);

        System.out.println("absolute1.resolve(relativized).normalize().equals(absolute2.normalize()): " + absolute1.resolve(relativized).normalize().equals(absolute2.normalize()));
    }


    public static void filesAttributes() throws IOException {
        //prepare one test file
        Path tmpPaths = Paths.get("/tmp/parent/child/test.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tmpPaths.toFile()));
        writer.write("There's something in the way she moves");
        writer.newLine();
        writer.write("looks my way or calls my name");
        writer.flush();
        writer.close();

        System.out.println("Get attributes with Files:");
        System.out.println("Files.getLastModifiedTime [FileTime]: " + Files.getLastModifiedTime(tmpPaths));
        System.out.println("Files.isExecutable(): " + Files.isExecutable(tmpPaths));
        System.out.println("Files.isReadable(): " + Files.isReadable(tmpPaths));
        System.out.println("Files.isWritable(): " + Files.isWritable(tmpPaths));

        System.out.println("Get attributes the old way (with File)");
        File tmpFile = new File("/tmp/parent/child/test.txt");
        System.out.println("tmpFile.lastModified() [long]: " + tmpFile.lastModified());
        System.out.println("tmpFile.canExecute(): " + tmpFile.canExecute());
        System.out.println("tmpFile.canRead(): " + tmpFile.canRead());
        System.out.println("tmpFile.canWrite(): " + tmpFile.canWrite());

        //get all attributes. BasicFileAttributes
        BasicFileAttributes basicFileAttributes = Files.readAttributes(tmpPaths, BasicFileAttributes.class);
        PosixFileAttributes posixFileAttributes = Files.readAttributes(tmpPaths, PosixFileAttributes.class);

        System.out.println("permissions of file: " + tmpPaths);
        Set<PosixFilePermission> permissions = posixFileAttributes.permissions();
        for(PosixFilePermission permission : permissions) {
            System.out.println(permission);
        }

        BasicFileAttributeView basicFileAttributeView = Files.getFileAttributeView(tmpPaths, BasicFileAttributeView.class);
//        basicFileAttributeView.
    }




    public static void main(String[] args) throws Exception {
//        pathsEquivalence();
//        filesManagement();
//        resolveVsRelativizevsNormalize();
        filesAttributes();
    }
}
