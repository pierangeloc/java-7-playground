package com.pierangeloc.foundation.ocp.formattingparsing;

import java.util.Scanner;

/**
 * Created by pierangeloc on 23-11-14.
 */
public class ScannerPlayground {

    public static void findWithScanner(String regex, String input) {
        System.out.println("findWithScanner");
        Scanner scanner = new Scanner(input);
        String out = scanner.findInLine(regex);

        System.out.println("Regex: " + regex + "; input: " + input + ". Found: " + out);
    }

    public static void tokenizeWithScanner(String source, String separatorRegex) {
        System.out.println("tokenizeWithScanner");
        Scanner scanner = new Scanner(source);
        scanner.useDelimiter(separatorRegex);

        while(scanner.hasNext()) {
            if(scanner.hasNextInt()) {
                System.out.println("[int] " + scanner.nextInt());
            } else if (scanner.hasNextBoolean()) {
                System.out.println("[boolean] " + scanner.nextBoolean());
            } else {
                System.out.println("String " + scanner.next());
            }

        }
    }

    public static void format() {
        System.out.println("format");
        System.out.printf("This is the second %2$s and this is the first %1$s argument", "ARG_1", "ARG_2");
        System.out.println();

        System.out.printf("Left justify: %-4s", "          SOMETHING AFTER A FEW SPACES");


    }
    public static void main(String[] args) {
        String twoDigitsRegex = "\\d\\d";
        findWithScanner(twoDigitsRegex, "1b2c34a4567f897");

        tokenizeWithScanner("123 something true false 234.12312,234234 the end", " ");
        format();
    }
}
