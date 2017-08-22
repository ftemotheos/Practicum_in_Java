package chapter03;
import java.io.*;

public class CountChar extends FilterReader {
    private static int charCount = 0;
    private static int wordCount = 0;
    private static int lineCount = 0;
    public CountChar(Reader in) {
        super(in);
    }
    public int read() throws IOException {
        Character previous = Character.CURRENCY_SYMBOL, next;
        int c;
        while ((c = super.read()) != -1) {
            next = new Character((char)c);
            if (!Character.isISOControl(next)) charCount++;
            if (Character.isWhitespace(next) && !Character.isWhitespace(previous)) wordCount++;
            if (next == '\n') lineCount++;
            previous = next;
        }
        return c;
    }
    protected static void fail(String msg) throws IOException {
        throw new IOException("CountChar: " + msg);
    }
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("The command format: java <this package>.CountChar <file>");
            System.exit(0);
        }
        try {
            File f = new File(args[0]);
            if (!f.exists()) fail("File do not exists " + args[0]);
            if (!f.isFile()) fail("It's not a file " + args[0]);
            if (!f.canRead()) fail("Protected from read" + args[0]);
            CountChar in = new CountChar(new FileReader(f));
            in.read();
            System.out.println("The quantity of characters:" + charCount);
            System.out.println("The quantity of words:" + wordCount);
            System.out.println("The quantity of lines:" + lineCount);
            in.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}