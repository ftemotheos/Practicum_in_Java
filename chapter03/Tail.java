package chapter03;
import java.io.*;

public class Tail {
    private static final int lines = 10;
    public static void printTail(String filename) throws IOException {
        File f = new File(filename);
        if (!f.exists()) fail("File do not exists " + filename);
        if (!f.isFile()) fail("It's not a file " + filename);
        if (!f.canRead()) fail("Protected from read " + filename);
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(f));
            String str = null;
            StringBuffer buff = new StringBuffer();
            while ((str = in.readLine()) != null) {
                buff.append(str).append("\n");
            }
            int last = buff.length() - 1;
            for (int i = 0; i <= lines; i++) {
                last = buff.lastIndexOf("\n", last);
                if (last != -1) --last;
                else break;
            }
            if (last != -1) buff.delete(0, last + 2);
            if (buff.length() != 0) System.out.print(new String(buff));
            else System.out.println("The file is empty!");
        } finally { if (in != null) in.close(); }
    }
    protected static void fail(String msg) throws IOException {
        throw new IOException("printTail: " + msg);
    }
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("The comand format: java <this package>.Tail <file>");
            System.exit(0);
        }
        try { printTail(args[0]); }
        catch (IOException e) { System.err.println(e.getMessage()); }
    }
}