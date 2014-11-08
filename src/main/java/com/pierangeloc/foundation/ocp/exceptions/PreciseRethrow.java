package com.pierangeloc.foundation.ocp.exceptions;

public class PreciseRethrow {

    public void rethrowException(String exceptionOrdinal) throws ExceptionB, ExceptionA {
        try {
            if(exceptionOrdinal.equals("first"))
                throw new ExceptionA();
            else
                throw new ExceptionB();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}

class ExceptionA extends Exception {}

class ExceptionB extends Exception {}
