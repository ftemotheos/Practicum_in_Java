package chapter05;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class GenericClient {
    public final static int DEFAULT_PORT = 3004;
    public static void main(String[] args) {
        try {
            InetAddress host = InetAddress.getLocalHost();
            int port = DEFAULT_PORT;
            Socket s = new Socket(host, port);
            final Reader from_server = new InputStreamReader(s.getInputStream());
            PrintWriter to_server = new PrintWriter(s.getOutputStream());
            BufferedReader from_user = new BufferedReader(new InputStreamReader(System.in));
            final PrintWriter to_user = new PrintWriter(System.out, true);
            to_user.println("Connected to " + s.getInetAddress() + ":" + s.getPort());
            Thread t = new Thread() {
                public void run() {
                    char[] buffer = new char[1024];
                    int chars_read;
                    try {
                        while((chars_read = from_server.read(buffer)) != -1) {
                            for(int i = 0; i < chars_read; i++) {
                                if(buffer[i] == '\n') to_user.println();
                                else to_user.print(buffer[i]);
                            }
                            to_user.flush();
                        }
                    } catch (IOException e) { to_user.println(e); }
                    to_user.println("Server disconnected.");
                    System.exit(0);
                }
            };
            t.setPriority(Thread.currentThread().getPriority() + 1);
            t.start();
            String line;
            while((line = from_user.readLine()) != null) {
                to_server.print(line + "\r\n");
                to_server.flush();
            }
            s.close();
            to_user.println("Client disconnected.");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
