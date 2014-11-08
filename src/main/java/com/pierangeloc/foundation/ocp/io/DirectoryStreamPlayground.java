package com.pierangeloc.foundation.ocp.io;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 */
public class DirectoryStreamPlayground {

    public static void listTmpDirContent() {
        Path tmp = Paths.get("/tmp");
        System.out.println("all content:");
        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(tmp)) {
            for(Path p : directoryStream) {
                System.out.println(p);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("only .tmp files: using the glob \"*.tmp\"");
        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(tmp, "*.tmp")) {
            for(Path p : directoryStream) {
                System.out.println(p);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        listTmpDirContent();
    }
}
