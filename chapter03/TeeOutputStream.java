package chapter03;

import java.io.*;

/**
 * Created by Timofey on 28.02.2016.
 */
public class TeeOutputStream extends OutputStream {
    private OutputStream firstOut;
    private OutputStream secondOut;
    public TeeOutputStream(OutputStream firstOut, OutputStream secondOut) {
        this.firstOut = firstOut;
        this.secondOut = secondOut;
    }
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        firstOut.write(b,off,len);
        secondOut.write(b,off,len);
    }
    public void write(int b) {}
}
