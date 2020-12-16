package com.eliteguzhva;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide logName as an argument");
            System.exit(0);
        }

        String logName = args[0];

        LogParser parser = new LogParser();
        boolean didParse = parser.parse(logName);
        if (!didParse) {
            System.out.println("Couldn't parse given log file");
        } else {
            System.out.println("Successfully parsed!");
            parser.print();
        }
    }
}
