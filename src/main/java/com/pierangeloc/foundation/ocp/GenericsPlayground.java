package com.pierangeloc.foundation.ocp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierangeloc on 18-11-14.
 */
public class GenericsPlayground {

    private static void test() {
        List l = new ArrayList();
        l.add("");
    }

    public static void addCat(Animal[] animals) {
        animals[0] = new Cat();
    }

    public void addAnimal(List<? extends Animal> l ) {
        //this won't compile! adding a cat might compromise the type safety of the list
//        l.add(new Cat());
    }

    public void addCat(List<? super Cat> l) {
        //this is allowed, because adding a Cat to a list that we are sure can host Cats is ok
        l.add(new Cat());
    }

    public void addSomethingToGenericList(List<?> l) {
        String s = (String) l.get(0);
    }

    //generic on a method
    public static <T> int getHash(T t) {
        return t.hashCode();
    }

    public static void main(String[] args) {
        //can assign a typed collection to an untyped collection
        List l = new ArrayList<Integer>();
        System.out.println(l);
        //...as long as you don't modify the untyped collection, then you get a warning, because this breaks the type-safety of any typed list you assign to l.
        // Compiler cannot refuse compiling, because it would break backward compatibility!
        l.add("");

        //assigning a non-typed list to a typed list issues a warning, just because I might get something that I don't expect at runtime from l1
        List tmpL1 = new ArrayList();
        List<Integer> l1 = tmpL1;
        //if then we also modify the list, even worse. Don't call modifier methods with raw types
//        tmpL1.add("something");
//        int i = l1.get(0); //ClassCastException
        System.out.println(l1);

        //arrays are covariant, but this covariance is painful at runtime (can add a Dog to array of Cat)
        Animal[] animals = new Dog[3];
//        addCat(animals); //ArrayStoreException

        //lists are not covariant, just for the same reason
//        List<Animal> doggies = new ArrayList<Dog>();

        List<?> listCats = new ArrayList<Cat>();
        List<?> listDog = new ArrayList<Dog>();
        //won't compile
        //listDog.add(new Cat());



    }


    static class Animal {}
    static class Quadruped extends Animal {}
    static class Dog extends Quadruped {}
    static class Cat extends Quadruped {}
}
