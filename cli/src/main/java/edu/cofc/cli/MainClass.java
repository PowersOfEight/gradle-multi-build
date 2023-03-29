package edu.cofc.cli;

import edu.cofc.core.serialize.Example;

public class MainClass {
    public static void main(String[] args) {
        Example example = new Example(12,"example",1.24);
        System.out.printf("Example: %s", example.printToCSVRecord());
    }
}
