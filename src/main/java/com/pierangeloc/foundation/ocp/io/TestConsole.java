package com.pierangeloc.foundation.ocp.io;

/**
 * Created by pierangeloc on 29-10-14.
 */
public class TestConsole {
    public static void main(String[] args) {

        String  weirdString = "å, Ă, ạ, Ễ";
        System.out.println(weirdString);
        System.console().printf(weirdString);
        System.console().format("Hello!");
        while(true) {
            String s = System.console().readLine("Please enter %s, exit to exit\n", "something");

            if(s.equals("exit")){
                break;
            }
        }

        while(true) {
            char[] chars = System.console().readPassword("...and now enter a password %s, exit to exit\n", "please");

            if(new String(chars).equals("exit")){
                System.console().printf("doidoi\n");
                break;
            }
        }

    }
}
