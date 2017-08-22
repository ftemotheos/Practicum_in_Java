package chapter02;

public class Circle implements Comparable {
    private int x, y, r;
    Circle(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
    Circle(int r) { this(0, 0, r); }
    Circle() { this(0, 0, 0); }
    public int getRadius() { return r; }
    public void move(int deltax, int deltay) {
        x += deltax;
        y += deltay;
    }
    public boolean isInstance(int xp, int yp) {
        return (xp - x) * (xp - x) + (yp - y) * (yp - y) <= r * r;
    }
    public Rect boundingBox() {
        return new Rect(x + r, y + r, x - r, y - r);
    }
    public String toString() {
        return "[ " + x + ", " + y + " ]" + " R = " + r;
    }
    public int compareTo(Object other) {
        Circle o = (Circle)other;
        int result = this.r - o.getRadius();
        if (result > 0) return 1;
        else if (result < 0) return -1;
        else return 0;
    }
}