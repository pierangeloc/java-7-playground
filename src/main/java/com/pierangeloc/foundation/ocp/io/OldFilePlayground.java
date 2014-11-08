package com.pierangeloc.foundation.ocp.io;

import java.io.File;
import java.io.IOException;

/**
 * Created by pierangeloc on 4-11-14.
 */
public class OldFilePlayground {

    public static void main(String[] args) throws IOException, InterruptedException {

        File tmpDir = new File("/tmp"); //I'm not creating a file!!
        System.out.println("tmpDir exists: " + tmpDir.exists());
        System.out.println("unlikely file exists: " + new File("/sfdasfd/dfasdf").exists());

        File relativeFile = new File("relative/position/text.txt");
        System.out.println("relativeFile exists: " + relativeFile.exists());

        File newFileInTmpDir = new File(tmpDir, "newFile.txt"); //I'm not creating it yet!
        System.out.println("before creation, exists: " + newFileInTmpDir.exists());
        System.out.println("creating....");
        boolean created = newFileInTmpDir.createNewFile();
        System.out.println("file created: " + created);
        System.out.println("creating again:");
        created = newFileInTmpDir.createNewFile();
        System.out.println("file created: " + created);

        File newDirectoryInTmpDir = new File(tmpDir, "newDirectory");
        boolean dirCreated = newDirectoryInTmpDir.mkdir();
        System.out.println("dir created: " + dirCreated);
        System.out.println("creating dir again: ");
        dirCreated = newDirectoryInTmpDir.mkdir();
        System.out.println("dir created: " + dirCreated);

        System.out.println("now testing edge cases:");
        System.out.println("create a new file /tmp/test.txt");
        File file1 = new File(tmpDir, "test.txt");
        file1.createNewFile();
        File file2 = new File(file1, "test2.txt");
        System.out.println("create a new file having as parent another file: will it work?");
        file2.createNewFile();
        Thread.sleep(4000);
        cleanup();
    }

    private static void cleanup() {
        File tmpDir = new File("/tmp"); //I'm not creating a file!!

        File newFileInTmpDir = new File(tmpDir, "newFile.txt"); //I'm not creating it yet!
        newFileInTmpDir.delete();
        File newDirectoryInTmpDir = new File(tmpDir, "newDirectory");
        newDirectoryInTmpDir.delete();

    }
}
