package edu.cofc.core.serialize;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * <p>
 *     The tutorial used to create this example can be found
 *     <a href="https://www.vogella.com/tutorials/JAXB/article.html#jaxb_tutorial">here</a>.</br>
 *     Baeldung has another version (without using gradle)
 *     <a href="https://www.baeldung.com/jaxb">available here</a>.
 * </p>
 */
@XmlRootElement(name = "Example")
@XmlType(propOrder = {"id", "name", "data"})
public class Example implements Serializable, CommaSeparable {

    private int id;
    private String name;
    private double data;

    public Example(int id, String name, double data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement(name = "title")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }

    public Example(){
        this(-1,"", -1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Example example = (Example) o;// TODO: Add parentheses around expression
        return id == example.id &&
                Double.compare(example.data, data) == 0 &&
                name.compareTo(((Example) o).getName()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, data);
    }

    @Override
    public String printToCSVRecord() {
        return String.format("%d,%s,%f", id, name, data);
    }

    @Override
    public void setFieldsFromCSVRecord(String csvRecord) {
        String[] fields = csvRecord.split(",");
        this.id = Integer.parseInt(fields[0]);
        this.name = fields[1];
        this.data = Double.parseDouble(fields[2]);
    }



    public static void marshallToXML(Example example, String fileName)
            throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(Example.class);
        Marshaller marshaller = context.createMarshaller();
        //  Creates an indented XML file
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        OutputStream outputStream = Files.newOutputStream(Paths.get(fileName));
        marshaller.marshal(example, outputStream);
        outputStream.close();
    }

    public static Example unmarshallFromXML(String inputFileName)
            throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(Example.class);
        InputStream inputStream = Files.newInputStream(Paths.get(inputFileName));
        Example example = (Example) context.createUnmarshaller().unmarshal(inputStream);
        inputStream.close();
        return example;
    }
}
