package com.pierangeloc.foundation.ocp.innerclasses;

/**
 * Created by pierangelo on 12/5/14.
 */
public class OuterClass {

    private String outerField;

    public void doSomething() {

    }

    class InnerClass {
        private String innerField;

        //it is allowed to have an inner field with same name as a field outside
        private String outerField;

        public void doInner() {

            OuterClass.this.outerField = "outer field set from inside";
            outerField = "inner field with same name of outer field";
        }
    }


}


class InnerClassesTest {
    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();
        OuterClass.InnerClass innerClass = outerClass.new InnerClass();
        innerClass.doInner();
    }
}
