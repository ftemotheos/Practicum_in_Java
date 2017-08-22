package chapter05;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DateServer {
    public final static int DEFAULT_PORT = 8888;
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(DEFAULT_PORT);
            while(true) {
                Socket client = ss.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                Date currentDate = new Date(System.currentTimeMillis());
                out.println(currentDate.toString());
                out.close();
                in.close();
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
