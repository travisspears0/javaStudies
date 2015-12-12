package types;

public class Main {
    
    public static void main(String[] args) {
        
        try {
            WrapperGenerator.generateWrapper(skiplist.SkipList.class);
        } catch(Exception e) {
            e.printStackTrace();
        }
            
    }
    
}
