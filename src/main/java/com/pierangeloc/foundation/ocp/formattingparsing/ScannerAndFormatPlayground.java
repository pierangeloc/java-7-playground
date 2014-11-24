package com.pierangeloc.foundation.ocp.formattingparsing;

import java.util.Locale;
import java.util.Scanner;

/**
 * Created by pierangeloc on 23-11-14.
 */
public class ScannerAndFormatPlayground {

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
        System.out.println("***************");
        System.out.println("format()");
        System.out.printf("This is the second %2$s and this is the first %1$s argument", "ARG_1", "ARG_2");
        System.out.println();

        System.out.println("1234567890123456789012345678901234567890");
        System.out.printf("%35s", "default justified, long 35");
        System.out.printf("|%n");
        System.out.printf("%-35s", "left justified, long 35");
        System.out.printf("|%n");
        System.out.println("decimal with sign, taking 35 positions");
        System.out.printf("%+35d", 456);
        System.out.printf("|%n");
        System.out.println("padding left with 0, taking 35 positions");
        System.out.printf("%035d", 456);
        System.out.printf("|%n");
        System.out.println("using locale specific separators for thousands");
        Locale.setDefault(Locale.ITALY);
        System.out.printf("%,35f", 456000000.654);
        System.out.printf("|%n");
        System.out.println("using locale specific separators for thousands [US]");
        Locale.setDefault(Locale.US);
        System.out.printf("%,35f", 456000000.654);
        System.out.printf("|%n");
        System.out.println("normal formatting of a floating point");
        System.out.printf("%35f", 456000000.654);
        System.out.printf("|%n");

        System.out.println("negative numbers within parenthesis");
        System.out.printf("%(35f", -456000000.654);
        System.out.printf("|%n");
        System.out.println("negative numbers within parenthesis");
        System.out.printf("%(35d", +456000000);
        System.out.printf("|%n");

        System.out.println("precision 4, length 35");
        System.out.printf("%35.4f", +456000000.0);
        System.out.printf("|%n");

        //this would throw Exception!!!
//        System.out.printf("%35.4f", + 456000000);





    }
    public static void main(String[] args) {
        String twoDigitsRegex = "\\d\\d";
        findWithScanner(twoDigitsRegex, "1b2c34a4567f897");

        tokenizeWithScanner("123 something true false 234.12312,234234 the end", " ");
        format();
    }
}
