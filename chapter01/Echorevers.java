package chapter01;
import java.io.*;

public class Echorevers {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                System.out.print("> ");
                String inLine = in.readLine();
                StringBuffer outLine = new StringBuffer(inLine);
                if (inLine == null || inLine.equals("tiuq")) break;
                for (int i = 0; i < inLine.length(); i++) {
                    outLine.setCharAt(i, inLine.charAt(inLine.length() - 1- i));
                }
                System.out.println(outLine);
            }
        } catch (IOException e) {
            System.out.println(e + " Invalid Input");
        }
    }
}