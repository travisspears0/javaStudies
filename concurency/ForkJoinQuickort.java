package concurency;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.RecursiveTask;
import org.apache.commons.lang3.ArrayUtils;

public class ForkJoinQuickort<T extends Comparable<T>> extends RecursiveTask<T[]> {
    
    private static final int MAXIMUM_COMPUTE_SIZE = 10000;
    private final T[] array;
    private final Random random = new Random();
    private int pivot = -1;

    public ForkJoinQuickort(T[] array) {
        this.array = array;
    }
    
    public void sort() {
        this.sort(false);
    }
    
    public void sort(boolean onlyOnce) {
        this.sort(0,this.array.length-1, onlyOnce);
    }
    
    public void sort(int from, int to) {
        this.sort(from, to, false);
    }
    
    public void sort(int from, int to, boolean onlyOnce) {
        if(to-from < 1) {
            return;
        }
        int pivotIndex = random.nextInt(to-from)+from;
        int wallIndex = from;
        for(int i=from ; i<to ; ++i) {
            if(i != pivotIndex && this.array[i].compareTo(this.array[pivotIndex]) <= 0) {
                swap(wallIndex, i);
                ++wallIndex;
            }
        }
        swap(wallIndex, pivotIndex);
        
        if(onlyOnce) {
            pivot = wallIndex;
        } else {
            this.sort(from,wallIndex-1);
            this.sort(wallIndex+1,to);
        }
    }
    
    private void swap(int a, int b) {
        T temp = this.array[a];
        this.array[a] = this.array[b];
        this.array[b] = temp;
    }

    public T[] getArray() {
        return array;
    }
    
    @Override
    protected T[] compute() {
        if(array.length < ForkJoinQuickort.MAXIMUM_COMPUTE_SIZE) {
            this.sort();
            return this.array;
        }
        this.sort(true);
        T[] arrayLeft = Arrays.copyOf(array, pivot);
        int rightSize = array.length-pivot;
        T[] arrayRight = Arrays.copyOf(array, rightSize);
        for(int i=pivot,j=0 ; i<array.length ; ++i,++j) {
            arrayRight[j] = array[i];
        }
        ForkJoinQuickort<T> sortLeft = new ForkJoinQuickort<>(arrayLeft);
        ForkJoinQuickort<T> sortRight = new ForkJoinQuickort<>(arrayRight);
        sortLeft.fork();
        T[] sortedRight = sortRight.compute();
        T[] sortedLeft = sortLeft.join();
        return ArrayUtils.addAll(sortedLeft, sortedRight);
    }
    
}
