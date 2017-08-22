package chapter03;

import java.io.*;

/**
 * Created by Timofey on 05.03.2016.
 */
public class TestReader extends Reader {
    private int anyChar = -1;
    public TestReader (char anyChar) {
        this.anyChar = anyChar;
    }
    @Override
    public int read() {
        return this.anyChar;
    }
    public int read(char[] cbuf, int off, int len) {
        return read();
    }
    public void close() {
        this.anyChar = -1;
    }
}
