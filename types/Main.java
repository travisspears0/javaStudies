package types;

public class Main {
    
    public static void main(String[] args) {
        
        try {
            String s = WrapperGenerator.generateWrapper(skiplist.SkipList.class);
            SkipListWrapper slw = new SkipListWrapper();
            slw.put(5,6);
            slw.print();
        } catch(Exception e) {
            e.printStackTrace();
        }
            
    }
    
}
