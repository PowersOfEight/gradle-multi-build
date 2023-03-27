package edu.cofc.core.serialize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface CommaSeparatable {
    String printToCSVRecord();
    void setFieldsFromCSVRecord(String csvRecord);

    public static void writeToCSVFile(
            String fileName,
            CommaSeparatable record)
            throws IOException {
        PrintStream fileOut = new PrintStream(
                Files.newOutputStream(Paths.get(fileName)));
        fileOut.println(record.printToCSVRecord());
        fileOut.close();
    }

    public static CommaSeparatable readObjectFromCSVFile(
            String fileName,
            Class<? extends CommaSeparatable> clazz)
            throws ReflectiveOperationException, IOException {
        BufferedReader reader = Files.newBufferedReader(
                Paths.get(fileName));
        Constructor constructor = clazz.getConstructor();
        CommaSeparatable instance = (CommaSeparatable)
                constructor.newInstance();
        instance.setFieldsFromCSVRecord(reader.readLine());
        reader.close();
        return instance;
    }
}
