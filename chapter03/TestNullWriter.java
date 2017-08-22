package chapter03;

import java.io.*;

/**
 * Created by Timofey on 05.03.2016.
 */
public class TestNullWriter {
    public static void main(String[] args) {
        TestReader in = new TestReader('a');
        PrintWriter out = new PrintWriter(new NullWriter());
        int character;
        for (int i = 0; i < 10; i++) {
            character = in.read();
            System.out.print((char)character + "\t");
            out.write(character);
        }
    }
}
