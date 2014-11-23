package com.pierangeloc.foundation.ocp.collections;

import java.util.*;

/**
 * Created by pierangeloc on 18-11-14.
 */
public class CollectionsPlayground {

    public static void listAndQueueWithLinkedList() {
        LinkedList<Integer> linkedList = new LinkedList<>();
        //linkedlist supports all the queue methods
        linkedList.offer(9);
        linkedList.offer(5);
        linkedList.offer(4);

        System.out.println("offered to linkedlist (as queue) 9, 5, 4");
        System.out.println("let's check that peek() works");
        System.out.println("peek(): " + linkedList.peek());
        Integer i;
        while((i = linkedList.poll()) != null) {
            System.out.println("poll(): " + i);
        }

        linkedList.add(100);
        linkedList.add(101);
        linkedList.add(200);
        System.out.println(linkedList);
        System.out.println("adding 400 in position 1");
        linkedList.add(1, 400);
        System.out.println(linkedList);
        linkedList.add(5, 1000);
        System.out.println(linkedList);

    }

    public static void hashSetVsLinkedHashSet() {
        System.out.println("hashSetVsLinkedHashSet");
        Set<Integer> hashSet = new HashSet<>();
        Set<Integer> linkedHashSet = new LinkedHashSet<>();
        for (int i = 10; i > 0; i -= 2 ) {
            hashSet.add(i);
            linkedHashSet.add(i);
            System.out.println("added " + i + " to both hashSets");
        }
        hashSet.add(null);

        System.out.println("iterating through HashSet");
        for(int i : hashSet) {
            System.out.println(i);
        }

        System.out.println("iterating through linkedHashSet");
        for(int i : linkedHashSet) {
            System.out.println(i);
        }
    }

    public static void binarySearch() {
        List<Integer> list = new ArrayList<>();
        list.add(9);
        list.add(8);
        list.add(4);
        list.add(1);
        list.add(6);
        System.out.println("unsorted list: " + list);
        //sort list
        Collections.sort(list);
        System.out.println("after sorting:" + list); // 1,4,6,8,9
        System.out.println("binary search for 6: " + Collections.binarySearch(list, 6)); //should return 2
        System.out.println("binary search for 7: " + Collections.binarySearch(list, 7)); //insertion point: 3 => -4


    }

    public static void arrayToListAndViceversa() {
//        int[] integers = {1,2,3,4,5,6};
        String[] sa = new String[] {"one", "two", "three", "four", "five", "six"};
        List<String> integersAsList = Arrays.asList(sa);
        System.out.println("List backed by array: " + integersAsList);
        System.out.println("now modifying array:");
        sa[3] = "THREE";
        System.out.println("List backed by array: " + integersAsList);
        //now extending list: exception. the list is non extensible
//        integersAsList.add("seven");
    }
public static  void doSomething(Object o){}
    public static void doesCollectionsSupportNull() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(null);
        Object q;

        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add(null);
        Vector<String> vector  = new Vector<>();
        vector.add(null);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(null, "");

        Hashtable<String, Object> hashtable = new Hashtable<>();
        //does not support null keys
//        hashtable.put(null, "");

        TreeMap<String, Object> treeMap = new TreeMap<>();
        //does not support null keys
//        treeMap.put(null, "");

        HashSet<String> hashSet = new HashSet<>();
        hashSet.add(null);

        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(null);

        TreeSet<String> treeSet = new TreeSet<>();
        //does not support null values
//        treeSet.add(null);

    }


    public static void main(String[] args) {
//        listAndQueueWithLinkedList();
//        hashSetVsLinkedHashSet();
//        binarySearch();
//        arrayToListAndViceversa();
        doesCollectionsSupportNull();

        TreeSet<Dog> ts = new TreeSet<>();
    }

    static class Dog{}
}
