package chapter05;

import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class UDPSend {
    public static final String usage = "Format is: java UDPSend <hostname> <port> <msg> ...\n" +
            "or: java UDPSend <hostname> <port> -f <file>";
    private static int attempts = 1;
//    args[] = "192.168.0.101", "3000", "Hello world! In this time with help of datagram!"
//    args[] = "192.168.0.101", "3000", "-f", "C:\Windows\DirectX.log"
    public static void main(String[] args) {
        try {
            if (args.length < 3) {
                throw new IllegalArgumentException();
            }
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            byte[] message;
            if (args[2].equals("-f")) {
                File f = new File(args[3]);
                int len = (int) f.length();
                message = new byte[len];
                FileInputStream in = new FileInputStream(f);
                int bytes_read = 0;
                int n;
                do {
                    n = in.read(message, bytes_read, len - bytes_read);
                    bytes_read += n;
                } while ((bytes_read < len) && (n != -1));
            }
            else {
                String msg = args[2];
                for (int i = 3; i < args.length; i++) {
                    msg += " " + args[i];
                }
                message = msg.getBytes();
            }
            InetAddress address = InetAddress.getByName(host);
            DatagramPacket packet = new DatagramPacket(message, message.length, address, port);
            DatagramSocket dsocket = new DatagramSocket();
            dsocket.send(packet);
            dsocket.setSoTimeout(3000);
            dsocket.receive(packet);
            String msg = new String(packet.getData(), 0, packet.getLength());
            System.out.println(packet.getAddress().getHostName() + ":\n" + msg);
            dsocket.close();
        }
        catch (SocketTimeoutException e) {
            if (attempts < 3) {
                ++attempts;
                UDPSend.main(args);
            }
            else {
                e.printStackTrace();
                System.err.println(usage);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(usage);
        }
    }
}
