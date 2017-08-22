package chapter03;
import java.io.*;
import java.util.zip.*;

public class Uncompress {
    public static void gzipFile(String from, String to) throws IOException {
        GZIPInputStream in = new GZIPInputStream(new FileInputStream(from));
        FileOutputStream out = new FileOutputStream(to);
        byte[] buffer = new byte[4096];
        int bytes_read;
        while ((bytes_read = in.read(buffer)) != -1)
            out.write(buffer, 0, bytes_read);
        in.close();
        out.close();
    }
    public static void zipDirectory(String zipfile, String dir) throws IOException, IllegalArgumentException {
        if (!dir.endsWith("\\"))
            dir = dir + "\\";
        File d = new File(dir);
        if (!d.mkdirs())
            throw new IllegalArgumentException("Uncompress: Can't create the directory: " + dir);
        byte[] buffer = new byte[4096];
        int bytes_read;
        ZipInputStream in = new ZipInputStream(new FileInputStream(zipfile));
        ZipEntry entry;
        while ((entry = in.getNextEntry()) != null) {
            String filename = entry.getName();
            int beginIndex = filename.lastIndexOf("\\");
            filename = filename.substring(beginIndex + 1);
            File f = new File(dir + filename);
            FileOutputStream out = new FileOutputStream(f);
            while ((bytes_read = in.read(buffer)) != -1)
                out.write(buffer, 0, bytes_read);
            out.close();
        }
        in.close();
    }
    public static void main(String[] args) throws IOException {
        if ((args.length != 1) && (args.length != 2)) {
            System.err.println("The command format: java <this package>.Uncompress <from> [to]");
            System.exit(0);
        }
        String from = args[0], to;
        boolean directory = from.endsWith(".zip");
        if (args.length == 2) to = args[1];
        else {
            int endIndex = from.lastIndexOf(".");
            to = from.substring(0, endIndex);
        }
        if ((new File(to)).exists()) {
            System.err.println("Uncompress: Don't overwrite existing files: " + to);
            System.exit(0);
        }
        if (directory) zipDirectory(from, to);
        else gzipFile(from, to);
    }
}