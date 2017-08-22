package chapter01;
import java.util.*;
import java.io.*;

public class SearchNumbers {
    private static Random rand = new Random();
    private static final int size = 100;
    private static float numbers[] = new float[size];
    public static void find(float num) {
        int first = 0;
        int last = numbers.length;
        while (first < last) {
            int median = first + (last - first) / 2;
            if (num <= numbers[median]) last = median;
            else first = median + 1;
        }
        if (first == numbers.length) first = numbers.length - 1;
        System.out.println("Next lower:\tNext upper:");
        if (num < numbers[first]) {
            if (first > 0) System.out.println(numbers[first - 1] + "\t" + numbers[first]);
            else System.out.println("Is absent!\t" + numbers[first]);
        }
        if (num > numbers[first]) {
            if (first < (numbers.length - 1)) System.out.println(numbers[first] + "\t" + numbers[first + 1]);
            else System.out.println(numbers[first] + "\tIs absent!");
        }
    }
    public static void main(String[] args) {
        for (int i = 0; i < size; i++) {
            numbers[i] = rand.nextFloat() * 100;
        }
        SortNumbers.sort(numbers);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.print("> ");
                String line = in.readLine();
                if (line == null || line.equals("quit")) break;
                float number = Float.parseFloat(line);
                find(number);
            } catch (Exception e) {
                System.out.println("Invalid Input!");
            }
        }
    }
}