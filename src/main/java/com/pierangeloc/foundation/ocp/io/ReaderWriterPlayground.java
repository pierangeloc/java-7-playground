package com.pierangeloc.foundation.ocp.io;

import java.io.*;

/**
 * Created by pierangeloc on 4-11-14.
 */
public class ReaderWriterPlayground {

    public static void main(String[] args) throws IOException {
        File tmpDir = new File("/tmp");
        File newTxtFile = new File(tmpDir, "sample-writer.txt");
        System.out.println("newTxtFile exists: " + newTxtFile.exists());
        FileWriter writer = new FileWriter(newTxtFile);
        System.out.println("after writer has been created for this file, file exists? " + newTxtFile.exists());

        writer.write("First line\n");
        writer.write("Long string, I want to take only some part of it".toCharArray(), 15, 10);
        writer.flush();
        writer.close();

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newTxtFile));
        System.out.println("rewriting the same file just with a buffered writer");
        bufferedWriter.write("First line");
        bufferedWriter.newLine();
        bufferedWriter.write("Long string, I want to take only some part of it".toCharArray(), 15, 10);
        bufferedWriter.flush();
        bufferedWriter.close();

        FileReader reader = new FileReader(newTxtFile);
        System.out.println("reading file that was just written, using a plain old FileReader, that reads on a char[]");
        char buffer[] = new char[20];
        System.out.println("using a buffer of only size 10, smaller than the nr of chars in the file, I get:");
        reader.read(buffer);
        System.out.println(buffer);

        System.out.println("let's switch to a buffered reader, that can read line by line");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(newTxtFile));
        String line;
        while((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

        System.out.println("Let's check the features of a PrintWriter");
        File printWriterFile = new File(tmpDir,"sample-printwriter.txt");
        PrintWriter printWriter = new PrintWriter(printWriterFile);
        System.out.println("printwriter file exists after printwriter has been created from it? " + printWriterFile.exists());

        printWriter.write("first line");
        printWriter.write("second line");
        printWriter.write("third line");
        printWriter.append("; Appendix");
        printWriter.print(Math.PI);
        printWriter.print(Math.E);
        printWriter.println(Math.PI);
        printWriter.println(Math.E);
        printWriter.flush();
        printWriter.close();

        reader = new FileReader(printWriterFile);
        bufferedReader = new BufferedReader(reader);
        System.out.println("that's what has been written by the printwriter:");
        while((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }



        cleanup();



    }

    private static void cleanup() {
        File tmpDir = new File("/tmp");
        File newTxtFile = new File(tmpDir, "sample-writer.txt");
        File printWriterFile = new File(tmpDir,"sample-printwriter.txt");
        newTxtFile.delete();
        printWriterFile.delete();
    }
}
