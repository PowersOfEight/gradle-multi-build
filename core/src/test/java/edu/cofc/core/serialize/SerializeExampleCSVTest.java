package edu.cofc.core.serialize;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class SerializeExampleCSVTest {

    String fileName;

    @BeforeEach
    void setUp() {
        fileName = "exampleCSVTest.csv";
    }

    @Test
    void writeAndReadCSV() throws IOException, ReflectiveOperationException {
        Example example = new Example(1,"example",1.234);
        CommaSeparable.writeToCSVFile(fileName,example);
        Example other = (Example) CommaSeparable.readObjectFromCSVFile(
                fileName, Example.class);
        assertNotSame(example, other);
        assertEquals(example, other);
    }

    @AfterEach
    void tearDown() throws IOException {
        Path path = Paths.get(fileName);
        Files.deleteIfExists(path);
    }
}