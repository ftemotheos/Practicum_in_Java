package chapter03;
import java.io.*;

public class FileSize {
    private static long count = 0;
    private static File[] files;
    public static void countSize(String directory) throws IOException {
        File f = new File(directory);
        if (!f.exists()) fail("Directory do not exists " + directory);
        if (!f.isDirectory()) fail("There isn't such directory " + directory);
        files = f.listFiles();
        for (File item: files) {
            if (item.isDirectory()) countSize(item.getAbsolutePath());
            else count += item.length();
        }
    }
    public static long getCount() { return count; }
    protected static void fail(String msg) throws IOException {
        throw new IOException("FileSize: " + msg);
    }
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("The command format: java <this package>.FileSize <directory>");
            System.exit(0);
        }
        try {
            countSize(args[0]);
            System.out.println("The size of all files contained in this directory " + args[0] + " is " + getCount() + " bytes!");
        }
        catch (IOException e) { System.err.println(e.getMessage()); }
    }
}