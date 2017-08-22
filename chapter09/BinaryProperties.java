package chapter09;

import java.io.*;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class BinaryProperties extends Properties {

    public static final String FILE_NAME = "BinaryProperties.prop";

    private static final long serialVersionUID = -6146756662777029272L;

    private transient BinaryProperties binaryProperties;

    public void storeBinary(File file) {
        try (ObjectOutputStream out = new ObjectOutputStream(new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(file)))))
        {
            out.writeObject(this);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBinary(File file) {
        try (ObjectInputStream in = new ObjectInputStream(new GZIPInputStream(new BufferedInputStream(new FileInputStream(file)))))
        {
            binaryProperties = (BinaryProperties)in.readObject();
            in.close();
        } catch(ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File file = new File(FILE_NAME);
        BinaryProperties properties = new BinaryProperties();
        properties.setProperty("First key", "Some value");
        properties.setProperty("Second key", "Another value");
        properties.setProperty("Third key", "Other values");
        properties.storeBinary(file);
        System.out.println("Before loadBinary method was called the binaryProperties had not initialized:");
        System.out.println(properties.binaryProperties == null);
        properties.loadBinary(file);
        System.out.println("After loadBinary method was called the binaryProperties is:");
        System.out.println(properties.binaryProperties.toString());
    }
}
