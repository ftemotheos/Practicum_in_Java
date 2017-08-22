package chapter02;

public class TestShape {
    public static void main(String[] args) {
        Circle 	circle1 = new Circle(5),
                circle2 = new Circle(20, 30, 10);
        System.out.println("circle1 : " + circle1);
        System.out.println("circle2 : " + circle2);
        System.out.println("circle1 is instance (5, 0) : " + circle1.isInstance(5, 0));
        System.out.println("circle1 is instance (1, 3) : " + circle1.isInstance(1, 3));
        System.out.println("circle1 is instance (4, 8) : " + circle1.isInstance(4, 8));
        circle2.move(-10, -20);
        System.out.println("circle2 after move -10 by X, -20 by Y : " + circle2);
        circle2.move(10, 20);
        System.out.println("circle2 after move 10 by X, 20 by Y : " + circle2);
        System.out.println("a smallest rectangle including the circle1 : " + circle1.boundingBox());
        System.out.println("a smallest rectangle including the circle2 : " + circle2.boundingBox());
    }
}