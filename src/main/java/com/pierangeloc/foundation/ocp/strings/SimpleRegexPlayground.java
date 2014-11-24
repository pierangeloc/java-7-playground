package com.pierangeloc.foundation.ocp.strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pierangeloc on 22-11-14.
 */
public class SimpleRegexPlayground {

    public static void printMatchingPositions(String regex, String testString) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(testString);
        System.out.println("Regex: " + matcher.pattern());
        System.out.println("String: >" + testString + "<");
        System.out.println("        012345678901234567890123456789");
        while(matcher.find()) {
            System.out.println("matcher start at: " + matcher.start() + "; end: " + matcher.end() +  "; group: " + matcher.group());
        }
    }


    public static void main(String[] args) {
        String regex = "ab";
        String test = "abaaaab";
        printMatchingPositions(regex, test);
        printMatchingPositions("\\b", "w2w w$ &#w2");
        printMatchingPositions("\\B", "#ab de#");
        printMatchingPositions("[a-cA-C]", "cafeBABE");
        printMatchingPositions("\\d+", "1 a12 234b");
        //with greedy quantifier only the whole string is matched
        printMatchingPositions(".*xx", "yyxxxyxx");
        //with reluctant quantifier, 2 results are returned
        printMatchingPositions(".*?xx", "yyxxxyxx");

        //zero-length match
        printMatchingPositions("a?", "bbbbbabbbbbba");
        printMatchingPositions("a?", "");
    }
}
