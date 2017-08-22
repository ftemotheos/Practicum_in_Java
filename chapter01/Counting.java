package chapter01;

public class Counting {
    public static void main(String[] args) {
        for (int i = 1; i <= 15; i++) {
            System.out.print(i);
            System.out.print(' ');
        }
        System.out.println();
        for (int i = 15; i >= 1; i = i - 2) {
            System.out.print(i);
            System.out.print(' ');
        }
        System.out.println();
    }
}