package com.pierangeloc.foundation.ocp.serialization;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by pierangeloc on 1-12-14.
 */
public class SerializationPlayground {

    public static final String TMP_DIR = "/tmp";
    public static final String FILE1 = "test1";

    private static InputStream getTestFileInputStream() throws IOException {
        Path testPath = Paths.get(TMP_DIR, FILE1);

        return Files.newInputStream(testPath);
    }

    private static OutputStream getTestFileOutputStream() throws IOException {
        Path testPath = Paths.get(TMP_DIR, FILE1);

        return Files.newOutputStream(testPath);
    }

    public static void dataOutputStreamPlayground() throws IOException {
        try(DataOutputStream dataOutputStream = new DataOutputStream(getTestFileOutputStream())) {
            dataOutputStream.writeInt(4);
            dataOutputStream.writeDouble(Math.PI);
            dataOutputStream.writeChars("4 times Pi");
        }

        try(DataInputStream dataInputStream = new DataInputStream(getTestFileInputStream())) {
            System.out.println("reading Int: " + dataInputStream.readInt());
            System.out.println("reading Double: " + dataInputStream.readDouble());
            while(true) {
                System.out.println("reading String: " + dataInputStream.readChar());
            }
        } catch(Exception e) {
            System.out.println("caught " + e);
        }


    }

    public static void main(String[] args) throws IOException {
        dataOutputStreamPlayground();
    }
}
