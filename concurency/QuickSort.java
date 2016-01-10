package concurency;

import java.util.Random;

public class QuickSort<T extends Comparable<T>> {
    
    private final T[] array;
    private final Random random = new Random();
    
    public QuickSort(T[] array) {
        this.array = array;
    }
    
    public void sort() {
        this.sort(0, this.array.length-1);
    }
    
    private void sort(int from, int to) {
        if(to-from < 1) {
            return;
        }
        int pivotIndex = random.nextInt(to-from)+from;
        int wallIndex = from;
        for(int i=from ; i<to ; ++i) {
            if(i==pivotIndex) continue;
            if(this.array[i].compareTo(this.array[pivotIndex]) > 0) {
                //firstBiggerIndex = i;
            } else {
                T temp = this.array[wallIndex];
                this.array[wallIndex] = this.array[i];
                this.array[i] = temp;
                ++wallIndex;
            }
        }
        T temp = this.array[wallIndex];
        this.array[wallIndex] = this.array[pivotIndex];
        this.array[pivotIndex] = temp;
        
        this.sort(from,wallIndex-1);
        this.sort(wallIndex+1,to);
    }

    public T[] getArray() {
        return array;
    }
    
}
