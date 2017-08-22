package chapter05;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.zip.CRC32;

public class UDPReceive {
    public static final String usage = "Format is: java UDPReceive <port>";
//    args[] = "3000"
    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new IllegalArgumentException("Incorrect number of arguments!");
            }
            int port = Integer.parseInt(args[0]);
            DatagramSocket dsocket = new DatagramSocket(port);
            byte[] buffer = new byte[2048];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            CRC32 crc32 = new CRC32();
            long checksum;
            for (;;) {
                dsocket.receive(packet);
                crc32.update(buffer);
                checksum = crc32.getValue();
                String msg = new String(buffer, 0, packet.getLength());
                System.out.println(packet.getAddress().getHostName() + ":\n" + msg);
                msg = String.valueOf(checksum);
                packet.setData(msg.getBytes());
                packet.setLength(msg.length());
                dsocket.send(packet);
                packet.setData(buffer);
                packet.setLength(buffer.length);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            System.err.println(usage);
        }
    }
}
