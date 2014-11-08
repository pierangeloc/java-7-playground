package com.pierangeloc.foundation.ocp.io;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created by pierangeloc on 29-10-14.
 */
public class TestReaderWriter {

    public static final String FILE_FULL_PATH = "/home/pierangeloc/Documents/projects/java/ania/shop/ania-shop/src/main/resources/persistence.properties";

    private static void readWithFileReader() throws IOException {

        FileReader reader = new FileReader(FILE_FULL_PATH);
        int c;
        while((c = reader.read()) != -1) {
            System.out.print((char)c);
        }
    }

    private static void readWithFileReaderAndBufferArray() throws IOException {
        FileReader reader = new FileReader(FILE_FULL_PATH);
        char[] buffer = new char[16];
        while(reader.read(buffer) != -1) {
            System.out.print(buffer);
        }

    }

    public static void main(String[] args) throws IOException {
        System.out.println("\nReading persistence.properties with simple FileReader, one char at a time");
        readWithFileReader();
        System.out.println("\nReading persistence.properties with FileReader and char[], 16 charsat a time");
        readWithFileReaderAndBufferArray();
    }
}
