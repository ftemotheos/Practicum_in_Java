package chapter03;
import java.util.*;
import java.io.*;

public class FileList {
    private static File[] files;
    private static List<String> names = new ArrayList<>();
    private static List<Long> size = new ArrayList<>();
    private static List<Long> data = new ArrayList<>();
    private static List<File> sortFiles = new ArrayList<>();
    public static void printFiles(String directory, String flag) throws IOException {
        File dir = new File(directory);
        if (!dir.exists()) fail("Directory do not exists " + directory);
        if (!dir.isDirectory()) fail("There isn't such a directory " + directory);
        files = dir.listFiles();
        for (File item: files) {
            names.add(item.getName());
            size.add(item.length());
            data.add(item.lastModified());
        }
        if (flag.equals("-n")) {
            System.out.println("Names of files and dir:" + " | Size of files, 0 - subdir:" + " | Date of last modified:");
            Collections.sort(names);
            for (int i = 0; i < names.size(); i++) {
                for (int j = 0; j < files.length; j++) {
                    if (files[j].getName() != null && names.get(i).equals(files[j].getName()) && !sortFiles.contains(files[j])) {
                        sortFiles.add(i, files[j]);
                    }
                }
            }
            for (File item: sortFiles) {
                System.out.println("Name:" + item.getName() + " | Size:" + item.length() + " | Date:" + new Date(item.lastModified()));
            }
        }
        if (flag.equals("-s")) {
            System.out.println("Size of files, 0 - subdir:" + " | Names of files and dir:" + " | Date of last modified:");
            Collections.sort(size);
            for (int i = 0; i < size.size(); i++) {
                for (int j = 0; j < files.length; j++) {
                    if (size.get(i) == files[j].length() && !sortFiles.contains(files[j])) {
                        sortFiles.add(i, files[j]);
                    }
                }
            }
            for (File item: sortFiles) {
                System.out.println("Size:" + item.length() + " | Name:" + item.getName() + " | Date:" + new Date(item.lastModified()));
            }
        }
        if (flag.equals("-d")) {
            System.out.println("Date of last modified:" + " | Names of files and dir:" + " | Size of files, 0 - subdir:");
            Collections.sort(data);
            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < files.length; j++) {
                    if (data.get(i) == files[j].lastModified() && !sortFiles.contains(files[j])) {
                        sortFiles.add(i, files[j]);
                    }
                }
            }
            for (File item: sortFiles) {
                System.out.println("Date:" + new Date(item.lastModified()) + " | Name:" + item.getName() + " | Size:" + item.length());
            }
        }
    }
    protected static void fail(String msg) throws IOException {
        throw new IOException("FileList: " + msg);
    }
    public static void main(String[] args) {
        String directory = null;
        String flag = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-n") || args[i].equals("-s") || args[i].equals("-d")) {
                flag = args[i];
            }
            else {
                if (directory != null) {
                    System.err.println("The command format: java <this package>.FileList <directory> [flag]");
                    System.exit(0);
                }
                else directory = args[i];
            }
        }
        if (directory == null) { directory = System.getProperty("user.dir"); }
        if (flag == null) { flag = "-n"; }
        try { printFiles(directory, flag); }
        catch (IOException e) { System.err.println(e.getMessage()); }
    }
}