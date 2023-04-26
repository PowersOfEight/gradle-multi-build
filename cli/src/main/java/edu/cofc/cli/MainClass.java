package edu.cofc.cli;


import edu.cofc.core.serialize.BinarySerializationEngine;
import edu.cofc.core.serialize.CommaSeparable;
import edu.cofc.core.serialize.Example;
import java.util.Scanner;

public class MainClass {

    public static final String[] MAIN_MENU_OPTIONS = {
            "Save record to file",
            "Load record from file",
            "Exit the program"
    };

    public static final String[] FILE_TYPE_DESCRIPTIONS = {
            "Binary",
            "Comma-separated values",
            "XML"
    };
    public static final char BORDER_CHAR = '*';
    public static final int BORDER_LENGTH = 60;

    public static void printBorder(char borderChar, int borderLength) {
        for(int i = 0; i < borderLength; ++i){
            System.out.print(borderChar);
        }
        System.out.println();
    }

    public static void printBorder() {
        printBorder(BORDER_CHAR, BORDER_LENGTH);
    }

    public static int validateMenuSelection(int numberOfMenuItems, Scanner input) {
        int selection;
        System.out.printf("Please enter your selection (%d..%d): ", 0, numberOfMenuItems - 1);
        selection = input.nextInt();
        while(selection < 0 || selection >= numberOfMenuItems) {
            System.out.println("Sorry, that input is invalid, please try again");
            System.out.printf("Please enter your selection (%d..%d): ", 0, numberOfMenuItems - 1);
            selection = input.nextInt();
        }
        input.nextLine();
        return selection;
    }

    public static void printFileTypeMenu() {
        printBorder();
        for(int i = 0; i < FILE_TYPE_DESCRIPTIONS.length; ++i) {
            System.out.printf("%s%d%-5s%s\n","(",i,")",FILE_TYPE_DESCRIPTIONS[i]);
        }
        printBorder();
    }

    public static void printMainMenu(){
        printBorder();
        for(int i = 0; i < MAIN_MENU_OPTIONS.length; ++i) {
            System.out.printf("%s%d%-5s%s\n","(",i,")",MAIN_MENU_OPTIONS[i]);
        }
        printBorder();
    }

    public static int getRecordIdFromInput(Scanner input) {
        int id;
        System.out.print("Enter the record ID: ");
        id = input.nextInt();
        input.nextLine();
        return id;
    }
    public static String getRecordNameFromInput(Scanner input) {
        String recordName;
        System.out.print("Enter the record name: ");
        recordName = input.nextLine();
        return recordName;
    }

    public static double getRecordDataFromInput(Scanner input) {
        double recordData;
        System.out.print("Enter data for this record: ");
        recordData = input.nextDouble();
        input.nextLine();
        return recordData;
    }

    public static String getFileNameFromInput(Scanner input) {
        System.out.print("Enter the file name: ");
        return input.nextLine();
    }



    public static Example getExampleFromUserInput(Scanner input) {
        int recordID;
        String recordName;
        double recordData;
        recordID = getRecordIdFromInput(input);
        recordName = getRecordNameFromInput(input);
        recordData = getRecordDataFromInput(input);
        return new Example(recordID, recordName, recordData);
    }



    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        printMainMenu();
        int selection = validateMenuSelection(MAIN_MENU_OPTIONS.length, input);
        while(selection != MAIN_MENU_OPTIONS.length - 1) {
            printFileTypeMenu();
            int typeSelection = validateMenuSelection(FILE_TYPE_DESCRIPTIONS.length, input);
            switch (selection) {
                case 0 -> {
                    Example writableExample = getExampleFromUserInput(input);
                    switch (typeSelection) {
                        case 0 -> BinarySerializationEngine.serializeToBinary(writableExample,
                                    getFileNameFromInput(input));
                        case 1 -> CommaSeparable.writeToCSVFile(getFileNameFromInput(input),
                                    writableExample);
                        case 2 -> Example.marshallToXML(writableExample,
                                getFileNameFromInput(input));
                    }
                }
                case 1 -> {
                    Example readableExample = new Example();
                    switch (typeSelection) {
                        case 0 -> readableExample = (Example) BinarySerializationEngine.
                                    deserializeFromBinary(getFileNameFromInput(input));
                        case 1 -> readableExample = (Example) CommaSeparable.
                                    readObjectFromCSVFile(getFileNameFromInput(input), Example.class);
                        case 2 -> readableExample = Example.unmarshallFromXML(getFileNameFromInput(input));
                    }
                    System.out.printf("Example read from file is: %s \n", readableExample.printToCSVRecord());
                }
                default -> throw new IllegalArgumentException("Unexpected value for user input: Main Menu");
            }
            printMainMenu();
            selection = validateMenuSelection(MAIN_MENU_OPTIONS.length, input);
        }
        input.close();
    }
}
