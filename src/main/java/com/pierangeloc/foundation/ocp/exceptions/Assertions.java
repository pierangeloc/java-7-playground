package com.pierangeloc.foundation.ocp.exceptions;

/**
 * Created by pierangeloc on 29-11-14.
 */
public class Assertions {

    public static void assertionsAreDisabledByDefault() {
        assert false: "message";
    }

    public static void main(String[] args) {
        assertionsAreDisabledByDefault();
    }
}
