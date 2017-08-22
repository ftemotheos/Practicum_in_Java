package chapter03;

import java.io.*;
/**
 * Created by Timofey on 28.02.2016.
 */
public class TestTeeOutputStream {
    public static void main(String[] args) throws IOException {
        try {
            TeeOutputStream out =
                    new TeeOutputStream(new FileOutputStream("D:\\test1.txt"), new TeeOutputStream(new FileOutputStream("D:\\test2.txt"), System.out));
            System.out.println("Please, input no more 50 ASCII symbols to write or if you want to stop writing input more than 50:");
            byte[] buffer = new byte[4096];
            int numByte;
            try {
                do {
                    System.out.print(">");
                    numByte = System.in.read(buffer);
                    out.write(buffer, 0, numByte);
                } while (numByte != -1 && numByte < 50);
                } catch(IOException e) {
                    System.out.println("Invalid input!");
                } finally {
                    out.close();
                }
            } catch(FileNotFoundException e) {
                System.out.println("File hasn't been created!");
            }
    }
}