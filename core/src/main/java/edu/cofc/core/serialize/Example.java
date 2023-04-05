package edu.cofc.core.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

public class Example implements Serializable, CommaSeparable {

    private int id;
    private String name;
    private double data;

    public Example(int id, String name, double data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        Example example = (Example) o;
        return id == example.id &&
                Double.compare(example.data, data) == 0 &&
                Objects.equals(name, example.name);
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

    /**
     * <p>
     *     Serializes the example to an XML file.<br/>
     *     <a href="https://www.baeldung.com/xstream-serialize-object-to-xml">
     *         Serializing an object to XML with XStream</a>
     * </p>
     * @param example The example object to serialize
     * @param fileName The name of the file to serialize the object to
     * @throws IOException If the event of a problem creating or writing the file
     * @throws XStreamException If there is a problem with the xml encoding
     */
    public static void serializeToXML(Example example, String fileName)
            throws IOException, XStreamException {
        XStream xStream = new XStream();
        xStream.processAnnotations(Example.class);
        OutputStream stream =
                Files.newOutputStream(Paths.get(fileName));
        stream.write(xStream.toXML(example).getBytes());
        stream.close();
    }

    /**
     * <p>
     *      Deserializes and returns an instance of <code>Example</code> from
     *      the given file.<br/>
     *      <a href="https://www.baeldung.com/xstream-deserialize-xml-to-object">
     *          Deserializing an object from XML using XStream</a>
     * </p>
     * @param fileName The name of the file to deserialize from
     * @return A deserialized instance of the <code>Example</code> class
     * @throws IOException If the file doesn't exist or cannot be opened
     * @throws XStreamException If there is a problem parsing the XML
     */
    public static Example deserializeFromXML(String fileName)
            throws IOException, XStreamException {
        InputStream stream =
                Files.newInputStream(Paths.get(fileName));
        XStream xStream = new XStream();
        xStream.allowTypesByWildcard(new String[]{"edu.cofc.johnson.**"});
        xStream.processAnnotations(Example.class);
        Example example = (Example) xStream.fromXML(stream);
        return example;
    }
}
