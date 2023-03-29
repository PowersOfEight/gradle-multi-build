package edu.cofc.core.serialize;

import java.io.Serializable;
import java.util.Objects;

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
}
