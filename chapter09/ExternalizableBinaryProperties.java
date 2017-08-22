package chapter09;

import java.io.*;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ExternalizableBinaryProperties extends Properties implements Externalizable {

    public static final String FILE_NAME = "ExternalizableBinaryProperties.prop";

    private static final long serialVersionUID =  -8068490707334975232L;

    public void writeExternal(ObjectOutput out) {
        byte[] writingBytes;
        try (ByteArrayOutputStream countingOut = new ByteArrayOutputStream())
        {
            store(countingOut, "Testing properties");
            writingBytes = countingOut.toByteArray();
            out.writeInt(writingBytes.length);
            out.write(writingBytes);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readExternal(ObjectInput in) {
        byte[] readingBytes;
        try {
            readingBytes = new byte[in.readInt()];
            try (ByteArrayInputStream countingIn = new ByteArrayInputStream(readingBytes))
            {
                in.read(readingBytes);
                load(countingIn);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExternalizableBinaryProperties storingProperties = new ExternalizableBinaryProperties();
        ExternalizableBinaryProperties loadingProperties = null;
        storingProperties.setProperty("First key", "Some value");
        storingProperties.setProperty("Second key", "Another value");
        storingProperties.setProperty("Third key", "Other values");
        try {
            try (ObjectOutputStream out = new ObjectOutputStream(new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(FILE_NAME)))))
            {
                out.writeObject(storingProperties);
            }
            try (ObjectInputStream in = new ObjectInputStream(new GZIPInputStream(new BufferedInputStream(new FileInputStream(FILE_NAME)))))
            {
                loadingProperties = (ExternalizableBinaryProperties)in.readObject();
            }
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        if (loadingProperties != null) {
            System.out.println("The storing properties is:");
            System.out.println(storingProperties.toString());
            System.out.println("The loading properties is:");
            System.out.println(loadingProperties.toString());
        }
        else {
            System.err.println("Cannot load the properties!");
        }
    }
}
