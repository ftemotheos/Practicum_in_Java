package chapter02;

public class SortObject {
    public static interface Comparer {
        public int compare(Object a, Object b);
    }
    public static interface Comparable {
        public int compareTo(Object other);
    }
    public static void sort(Object[] a, int from, int to, boolean up, Comparer c) {
        if ((a == null) || (a.length < 2)) return;
        int i = from, j = to;
        Object centre = a[(from + to) / 2];
        do {
            if (up) {
                while ((i < to) && (c.compare(centre, a[i]) > 0)) i++;
                while ((j > from) && (c.compare(centre, a[j]) < 0)) j--;
            } else {
                while ((i < to) && (c.compare(centre, a[i]) < 0)) i++;
                while ((j > from) && (c.compare(centre, a[j]) > 0)) j--;
            }
            if (i < j) {
                Object tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
            }
            if (i <= j) { i++; j--; }
        } while (i <= j);
        if (from < j) sort(a, from, j, up, c);
        if (i > to) sort(a, i, to, up, c);
    }
}