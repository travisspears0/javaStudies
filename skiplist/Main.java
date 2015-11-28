package skiplist;

import java.util.Random;

public class Main {
    
    public static final Random random = new Random();
    public static boolean testing = false;
    public static long testTime = 0;
    public static final int size = 100000;
    
    public static void main(String[] args) {
        
        Integer[] keys = new Integer[size];
        for(int i=0 ; i<size ; ++i) {
            keys[i] = i+1;
        }
        
        for(int i=0,j=0 ; i<size ; ++i,j=i) {
            while(j==i) {
                j = random.nextInt(size);
            }
            keys[i] += keys[j];
            keys[j] = keys[i]-keys[j];
            keys[i] -= keys[j];
        }
        
        SkipList<Integer,Integer> skipList = new SkipList<>();
        
        testPut(skipList, keys);
        testContainsKey(skipList, size);
        testGet(skipList, size);
        testRemove(skipList);
        skipList.print();
        
    }
    
    public static void testPut(SkipList skipList, Object[] items) {
        testStart();
        for(int i=0 ; i<items.length ; ++i) {
            skipList.put(items[i], random.nextInt(100));
            //if(i%1000==0)System.out.println("put " + i);
        }
        System.out.println("PUT method time elsapsed: " + testEnd());
    }
    
    public static void testContainsKey(SkipList skipList,int size) {
        testStart();
        for(int i=0 ; i<size ; ++i) {
            boolean b = skipList.containsKey(i+1);
        }
        System.out.println("CONTAINS KEY method time elsapsed: " + testEnd());
    }
    
    public static void testGet(SkipList skipList,int size) {
        testStart();
        for(int i=0 ; i<size ; ++i) {
            Integer it = (Integer)skipList.get(i);
        }
        System.out.println("GET method time elsapsed: " + testEnd());
    }
    
    public static void testRemove(SkipList skipList) {
        testStart();
        while(!skipList.isEmpty()) {
            skipList.remove(0);
        }
        System.out.println("REMOVE method time elsapsed: " + testEnd());
    }
    
    public static void testStart() {
        Main.testTime = System.nanoTime();
    }
    
    public static long testEnd() {
        return (System.nanoTime()-Main.testTime)/1000000;
    }
    
}
