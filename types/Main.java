package types;

import static skiplist.Main.random;

public class Main {
    
    public static void main(String[] args) {
        
        try {
            WrapperGenerator.generateWrapper(skiplist.SkipList.class);
            /*SkipListWrapper skipList = new SkipListWrapper();
            int size=100;
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
            for(int i=0 ; i<keys.length ; ++i) {
                skipList.put(keys[i], random.nextInt(100));
            }
            skipList.print();
            */
        } catch(Exception e) {
            e.printStackTrace();
        }
            
    }
    
}
