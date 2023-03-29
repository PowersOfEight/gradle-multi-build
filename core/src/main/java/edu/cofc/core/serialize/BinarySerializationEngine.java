package edu.cofc.core.serialize;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>
 * This class holds static methods to serialize and deserialize
 * objects in files.
 * </p>
 * @author James Daniel Johnson
 */
public class BinarySerializationEngine {
    /**
     * <p>
     *     Serializes an object to a file.  The object must implement the <code>Serializable</code>
     *     interface to be stored.
     * </p>
     * @param serializableObject The object to serialize
     * @param outputFileName The name of the file to serialize this object to
     * @throws IOException An <code>IOException</code> will be thrown if there is trouble opening a file
     */
    public static void serializeToBinary(Serializable serializableObject,
                                         String outputFileName) throws IOException{
        Path outputFilePath = Paths.get(outputFileName);
        ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(outputFilePath));
        outputStream.writeObject(serializableObject);
        outputStream.close();
    }

    /**
     * <p>
     *     Deserializes an object from the given binary file. As the method returns an <code>Object</code>
     *     instance, the surrounding code must cast the return value to the proper type. For instance, if the
     *     class signature for <code>Example</code> is:
     *     <pre><code>
     *         public class Example implements Serializable
     *     </code></pre>
     *     then the calling code should look like:
     *     <pre><code>
     *         Example example = (Example) BinarySerializationEngine.deserializeFromBinary(exampleBinaryFileName);
     *     </code></pre>
     *     to return the proper type
     * </p>
     * @param inputFileName The binary file from which to read the instance from
     * @return The deserialized <code>Object</code> type
     * @throws IOException if there is a problem opening the file
     * @throws ClassNotFoundException if the definition for the class can not be found
     */
    public static Object deserializeFromBinary(String inputFileName)
            throws IOException, ClassNotFoundException {
        Path inputFilePath = Paths.get(inputFileName);
        ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(inputFilePath));
        Object object = inputStream.readObject();
        inputStream.close();
        return object;
    }
}
