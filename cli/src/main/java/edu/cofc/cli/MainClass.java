package edu.cofc.cli;


import edu.cofc.core.serialize.Example;
import edu.cofc.core.serialize.exception.XMLException;

import java.io.IOException;
import java.util.Scanner;

public class MainClass {

    public static final String[] MAIN_MENU_OPTIONS = {
            "Save record to file",
            "Load record from file",
            "Exit the program"
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
        System.out.print("Input file name: ");
        return input.nextLine();
    }

    public static void saveUserInputToFile(Scanner input)
            throws XMLException, IOException {
        //  Get the Example attributes
        int recordID;
        String recordName;
        double recordData;
        String outputFileName;
        Example example;
        recordID = getRecordIdFromInput(input);
        recordName = getRecordNameFromInput(input);
        recordData = getRecordDataFromInput(input);
        example = new Example(recordID, recordName, recordData);
        outputFileName = getFileNameFromInput(input);
//        CommaSeparable.writeToCSVFile(outputFileName, example);
        Example.marshallToXML(example, outputFileName);
    }



    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        printMainMenu();
        int selection = validateMenuSelection(MAIN_MENU_OPTIONS.length, input);
        while(selection != MAIN_MENU_OPTIONS.length - 1) {
            switch (selection) {
                case 0 -> saveUserInputToFile(input);
                case 1 -> {
                    System.out.print("Output file name: ");
//                    Example example = (Example) CommaSeparable.readObjectFromCSVFile(input.nextLine(),Example.class);
                    Example example = Example.unmarshallFromXML(getFileNameFromInput(input));
                    System.out.printf("The example read from the file is: %s\n", example.printToCSVRecord());
                }
                default -> throw new IllegalArgumentException("Unexpected value for user input");
            }
            printMainMenu();
            selection = validateMenuSelection(MAIN_MENU_OPTIONS.length, input);
        }
        input.close();
    }
}
