package chapter03;

import java.io.*;

/**
 * Created by Timofey on 05.03.2016.
 */
public class NullWriter extends Writer {
    public void write(char[] cbuf, int off, int len) { }
    @Override
    public void write(String str, int off, int len) { }
    public void flush() { }
    public void close() { }
}
