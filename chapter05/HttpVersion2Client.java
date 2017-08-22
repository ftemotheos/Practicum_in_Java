package chapter05;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
//import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

public class HttpVersion2Client {
    public final static String DEFAULT_URL = "http://www.oracle.com/ru/java/overview/index.html";
    public final static String DEFAULT_USER_AGENT = "MyOwnUserAgent";
//    public final static int DEFAULT_PORT = 8888;
    public static void main(String[] args) {
        try {
            BufferedOutputStream to_client = new BufferedOutputStream(System.out);
            Scanner input = new Scanner(System.in);
            System.out.println("Please enter a URL:");
            System.out.print("> ");
            String addressDNS = input.nextLine();
            URL url;
            if (addressDNS.length() == 0) {
                url = new URL(DEFAULT_URL);
                System.out.println("Considered default the URL is: " + DEFAULT_URL);
                System.out.println();
            }
            else url = new URL(addressDNS);
            String protocol = url.getProtocol();
            if(!protocol.equals("http")) throw new IllegalArgumentException("Must be used HTTP protocol!");
            String host = url.getHost();
            int port = url.getPort();
            if (port == -1) port = 80;
            String filename = url.getFile();
            Socket socket = new Socket(host, port);
//            Socket socket = new Socket(InetAddress.getLocalHost(),DEFAULT_PORT);
            BufferedInputStream from_server = new BufferedInputStream(socket.getInputStream());
            PrintWriter to_server = new PrintWriter(socket.getOutputStream());
            to_server.print("GET " + filename + " HTTP/2.0\n");
            to_server.print("Host: " + host + "\n");
            to_server.print("User-Agent: " + DEFAULT_USER_AGENT + "\n");
            to_server.print("\n");
            to_server.flush();
            int bytes_read;
            while ((bytes_read = from_server.read()) != -1)
                to_client.write(bytes_read);
            socket.close();
            to_client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
