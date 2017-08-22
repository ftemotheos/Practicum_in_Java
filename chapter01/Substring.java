package chapter01;

public class Substring {
    public static void main(String[] args) {
        try {
            if (args.length == 3) {
                String str = args[0];
                int beginIndex = Integer.parseInt(args[1]);
                int endIndex = Integer.parseInt(args[2]);
                if (endIndex < beginIndex)
                    throw new IndexOutOfBoundsException("The second argument must be greater than the third!");
                String substr = str.substring(beginIndex, endIndex + 1);
                System.out.println(substr);
            } else throw new IllegalArgumentException("Must be three arguments!");
        } catch (NumberFormatException e) {
            System.out.println(e + " The second and the third arguments should be integer numbers!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e + " Incorrect border of substring!");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }
}