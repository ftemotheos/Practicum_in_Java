package chapter08;

public class PrintAllParentClassesAndInterfaces {
    public static void main(String[] args) {
        try {
            if (args == null || args.length != 1 || args[0] == null) {
                throw new IllegalArgumentException();
            }
            Class<?> currentClass = Class.forName(args[0]);
            StringBuilder classes = new StringBuilder();
            String name = currentClass.toString();
            System.out.println(name);
            StringBuilder interfaces = new StringBuilder();
            appendInterfaces(interfaces, currentClass);
            Class<?> superClass;
            while ((superClass = currentClass.getSuperclass()) != null) {
                classes.insert(0, ' ');
                name = superClass.toString();
                classes.insert(0, name.substring(name.indexOf(" ") + 1));
                appendInterfaces(interfaces, superClass);
                currentClass = superClass;
            }
            System.out.println("extends: " + classes.toString().trim());
            System.out.println("implements: " + interfaces.toString().trim());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    private static void appendInterfaces(StringBuilder interfaces, Class<?> someClass) {
        Class<?>[] someClassInterfaces = someClass.getInterfaces();
        for (Class<?> item: someClassInterfaces) {
            appendInterfaces(interfaces, item);
            String name = item.toString();
            interfaces.append(name.substring(name.indexOf(" ") + 1));
            interfaces.append(' ');
        }
    }
}
