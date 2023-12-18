package Sem3;

import java.io.*;
import java.util.UUID;

public class Serializer implements Serializable {
    public static void serializeToFile(Object obj) {
        String fileName = obj.getClass().getName() + "_" + UUID.randomUUID().toString();
        try (OutputStream outputStream = new FileOutputStream(fileName, false)) {
            ObjectOutput objectOutput = new ObjectOutputStream(outputStream);
            objectOutput.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object deserializeFromFile(String serObj) {
        try (InputStream inputStream = new FileInputStream(serObj)) {
            File file = new File(serObj);
            ObjectInput objectInput = new ObjectInputStream(inputStream);
            file.deleteOnExit();
            return objectInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
