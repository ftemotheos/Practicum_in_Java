package chapter05;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class CreateProperties {
    private static Properties properties = new Properties();
    public static void main(String[] args) {
        try {
            FileOutputStream file  = new FileOutputStream("property.prop");
            properties.setProperty("USER", "temotheos@i.ua");
            properties.setProperty("PASS", "futyj,fh,f");
            properties.store(file, "Login and password for e-mail account");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
