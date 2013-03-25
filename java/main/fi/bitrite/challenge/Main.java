package fi.bitrite.challenge;

import java.util.Scanner;

public class Main {

    private static CircularBuffer buffer;

    private static int numToAdd;
    private static int numAdded;

    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);
        CommandParser parser = new CommandParser();
        while (true) {
            System.out.print("circular-buffer> ");
            String input = scanner.nextLine().trim();
            parser.parse(input);
        }
    }

    private static class CommandParser {

        public void parse(String input) {
            Scanner scanner = new Scanner(input);
            String cmd = scanner.next();

            if (numAdded < numToAdd) {
                buffer.add(cmd);
                numAdded++;
                return;
            }

            if (cmd.equals("N")) {
                if (scanner.hasNextInt()) {
                    buffer = new CircularBuffer(scanner.nextInt());
                }
            }

            if (cmd.equals("A")) {
                if (scanner.hasNextInt()) {
                    numToAdd = scanner.nextInt();
                    numAdded = 0;
                }
            }

            if (cmd.equals("R")) {
                if (scanner.hasNextInt()) {
                    buffer.remove(scanner.nextInt());
                }
            }

            if (cmd.equals("L")) {
                System.out.println(buffer.getItems());
            }

            if (cmd.equals("Q")) {
                System.exit(0);
            }

        }
    }
}

