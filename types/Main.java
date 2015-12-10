package types;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    
    public static void main(String[] args) {
        
        Logger log = Logger.getLogger("hehe");
        log.log(Level.INFO, "siema " + log.getName());
        
        try {
            WrapperGenerator.generateWrapper(A.class);
        } catch(Exception e) {
            e.printStackTrace();
        }
            
    }
    
}
