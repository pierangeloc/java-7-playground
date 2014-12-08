package com.pierangeloc.foundation.ocp.boxing;

/**
 * Created by pierangelo on 12/1/14.
 */
public class BoxingPlayground {

//    public int add(int a, int b){
//        System.out.println("in add(int, int)");
//        return a + b;
//    }

    public int add(Integer a, Integer b){
        System.out.println("in add(Integer, Integer)");
        return a + b;
    }


    public Integer add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        BoxingPlayground boxingPlayground = new BoxingPlayground();
        //if the add(int, int) would be uncommented, it would bind to that one
        //compiler first looks for exact matching, then falls back to
        boxingPlayground.add(1, 500);

        Object x = boxingPlayground.add(1, 500);
        System.out.println(x.getClass());
        Integer z = (Integer)x;
        int y = z;

        System.out.println("Integer z = " + z);
        System.out.println("int y = " + y);
        System.out.println("z == 3? " + (z == 3));
        System.out.println("z.equals(3)? " + (z.equals(3)));

    }
}
