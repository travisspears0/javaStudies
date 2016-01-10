package concurency;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {
    
    public static long testTime = 0;
    
    public static void main(String[] args) {
        
        ForkJoinPool fjp = new ForkJoinPool();
        Random random = new Random();
        int size = 1000000;
        Integer[] arrParallel = new Integer[size];
        Integer[] arrForkJoin = new Integer[size];
        Integer[] arrQuickSort = new Integer[size];
        for(int i=0 ; i<size ; ++i) {
            arrParallel[i] = random.nextInt(10000);
            arrForkJoin[i] = arrParallel[i];
            arrQuickSort[i] = arrParallel[i];
        }
        System.out.println("generated");
        
        testStart();
        Arrays.parallelSort(arrParallel);
        testEnd("parallelSort");
        
        testStart();
        fjp.invoke(new ForkJoinQuickort<>(arrForkJoin));
        testEnd("fork/join");
        
        testStart();
        new QuickSort<>(arrQuickSort).sort();
        testEnd("quicksort");
    }
    
    public static void testStart() {
        skiplist.Main.testTime = System.nanoTime();
    }
    
    public static void testEnd(String label) {
        System.out.println(label+": "+(System.nanoTime()-skiplist.Main.testTime)/1000000 + " ms");
    }
    
}
