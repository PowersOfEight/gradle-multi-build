package edu.cofc.core.serialize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * <p>
 * Classes that implement this interface can be stored as comma separated values using
 * the static method contained in this class.
 * </p>
 * <p>
 * In addition to the <code>printToCSVRecord</code> and <code>setFieldsFromCSVRecord</code>
 * methods, classes that implement this interface must implement a nullary constructor. Failure
 * to do so will result in an <code>InstantiationException</code> being thrown when using the
 * <code>readObjectFromCSVFile</code> method to instantiate a new instance of the implementing
 * class from a comma separated value file.
 * </p>
 * <a href="https://www.baeldung.com/java-reflection">Baeldung on reflection</a>
 */
public interface CommaSeparable {
    String printToCSVRecord();
    void setFieldsFromCSVRecord(String csvRecord);

    public static void writeToCSVFile(
            String fileName,
            CommaSeparable record)
            throws IOException {
        PrintStream fileOut = new PrintStream(
                Files.newOutputStream(Paths.get(fileName)));
        fileOut.println(record.printToCSVRecord());
        fileOut.close();
    }

    public static CommaSeparable readObjectFromCSVFile(
            String fileName,
            Class<? extends CommaSeparable> clazz)
            throws ReflectiveOperationException, IOException {
        BufferedReader reader = Files.newBufferedReader(
                Paths.get(fileName));
        Constructor constructor = clazz.getConstructor();

        CommaSeparable instance = (CommaSeparable)
                constructor.newInstance();
        instance.setFieldsFromCSVRecord(reader.readLine());
        reader.close();
        return instance;
    }
}
