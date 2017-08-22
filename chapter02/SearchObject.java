package chapter02;
import java.io.*;

public class SearchObject {
    private static final int size = 10;
    private static Circle[] obj = new Circle[size];
    private static SortObject.Comparer comparator = new SortObject.Comparer() {
        public int compare(Object a, Object b) {
            Circle i = (Circle)a;
            Circle j = (Circle)b;
            int result = i.getRadius() - j.getRadius();
            if (result > 0) return 1;
            else if (result < 0) return -1;
            else return 0;
        }
    };
    public static int search(Object a) {
        int first = 0;
        int last = obj.length;
        while (first < last) {
            int median = first + (last - first) / 2;
            if (obj[median].compareTo(a) < 0) first = median + 1;
            else last = median;
        }
        if (last == obj.length) last = obj.length - 1;
        if (obj[last].compareTo(a) == 0) return last;
        else return -1;
    }
    public static void main(String[] args) {
        for (int i = 0; i < size; i++) {
            obj[i] = new Circle(size - i);
        }
        System.out.println("This is array of circle before sort:");
        for (int i = 0; i < size; i++) {
            System.out.println(obj[i]);
        }
        SortObject.sort(obj, 0, size - 1, true, comparator);
        System.out.println("This is array of circle after sort:");
        for (int i = 0; i < size; i++) {
            System.out.println(obj[i]);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.print("> ");
                String line = in.readLine();
                if (line == null || line.equals("quit")) break;
                int r = Integer.parseInt(line);
                Circle searching = new Circle(r);
                System.out.println(search(searching));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}