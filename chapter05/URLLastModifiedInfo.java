package chapter05;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Scanner;

public class URLLastModifiedInfo {
    public final static String DEFAULT_URL = "http://www.oracle.com/ru/java/overview/index.html";
    public static void main(String args[]) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Please enter a URL:");
            System.out.print("> ");
            String url = input.nextLine();
            if (url.length() == 0) {
                System.out.println("Considered default the URL is: " + DEFAULT_URL);
                System.out.println();
                printInfo(new URL(DEFAULT_URL));
            }
            else printInfo(new URL(url));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void printInfo(URL url) throws IOException {
        URLConnection c = url.openConnection();
        c.connect();
        System.out.println("For: " + url.toString());
        System.out.println("Last modified date is: " + new Date(c.getLastModified()));
    }
}
