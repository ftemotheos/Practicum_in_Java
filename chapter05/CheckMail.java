package chapter05;

import java.io.*;
import java.net.Socket;
import java.util.Properties;
import java.util.StringTokenizer;

public class CheckMail {
    public static String HOST = "pop.i.ua";
    public static int PORT = 110;
    private static Properties properties = new Properties();
    public static void main(String[] args) {
        try {
            Socket s = new Socket(HOST, PORT);
            FileInputStream file = new FileInputStream("property.prop");
            properties.load(file);
            BufferedReader from_server = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter to_server = new PrintWriter(s.getOutputStream());
            StringWriter to_user = new StringWriter();
            System.out.println("Connected to " + s.getInetAddress() + ":" + s.getPort());
            String line = from_server.readLine();
            to_user.write(line + '\n');
            to_user.flush();
            to_server.print("USER " + properties.getProperty("USER") + "\r\n\r\n");
            to_server.flush();
            line = from_server.readLine();
            to_user.write(line + '\n');
            to_user.flush();
            to_server.print("PASS " + properties.getProperty("PASS") + "\r\n\r\n");
            to_server.flush();
            line = from_server.readLine();
            to_user.write(line + '\n');
            to_user.flush();
            to_server.print("STAT" + "\r\n\r\n");
            to_server.flush();
            line = from_server.readLine();
            to_user.write(line + '\n');
            to_user.flush();
            StringTokenizer st = new StringTokenizer(line);
            StringBuffer sb = new StringBuffer();
            while (st.hasMoreTokens()) {
                if (st.nextToken().equals("+OK")) {
                    sb.append(st.nextToken());
                    break;
                }
            }
            Integer amount = Integer.parseInt(sb.toString());
            sb.append("\n");
            while (amount > 0) {
                to_server.print("RETR " + amount + "\r\n\r\n");
                to_server.flush();
                while (!(line = from_server.readLine()).equals(".")) {
                    to_user.write(line + '\n');
                    to_user.flush();
                    st = new StringTokenizer(line);
                    if (st.hasMoreTokens() && st.nextToken().equals("From:")) {
                        while(st.hasMoreTokens()) {
                            sb.append(st.nextToken()).append("\t");
                        }
                    }
                }
                sb.append("\n");
                amount--;
            }
            to_server.print("QUIT" + "\r\n\r\n");
            to_server.flush();
            while ((line = from_server.readLine()) != null) {
                to_user.write(line + '\n');
                to_user.flush();
            }
            s.close();
            System.out.println("Server disconnected.");
            System.out.println(to_user.toString());
            System.out.println(sb.toString());
            System.exit(0);
        } catch(IOException e) { e.printStackTrace(); }
    }
}
