
package types;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class SkipListWrapper {

    private final skiplist.SkipList wrappedReference;
    private final java.util.logging.Logger logger;
    private long testTime;
    private Handler fileHandler;

    public SkipListWrapper()
        throws IOException
    {
        wrappedReference = new skiplist.SkipList();
        logger = java.util.logging.Logger.getLogger("SkipListWrapperLogger");
        fileHandler = new FileHandler("./logs.txt");
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }

    private void timerStart() {
        testTime = System.nanoTime();
    }

    private void timerStop(String methodName) {
        logger.log(Level.INFO, ((methodName +": ")+(((testTime*-1)+ System.nanoTime())/ 1000000)));
    }

    public<K, V >V remove(K arg0) {
        timerStart();
        V res;
        res = ((V) wrappedReference.remove(arg0));
        timerStop("remove");
        return res;
    }

    public<K, V >V get(K arg0) {
        timerStart();
        V res;
        res = ((V) wrappedReference.get(arg0));
        timerStop("get");
        return res;
    }

    public<K, V >V put(K arg0, V arg1) {
        timerStart();
        V res;
        res = ((V) wrappedReference.put(arg0, arg1));
        timerStop("put");
        return res;
    }

    public boolean isEmpty() {
        timerStart();
        boolean res;
        res = wrappedReference.isEmpty();
        timerStop("isEmpty");
        return res;
    }

    public void print() {
        timerStart();
        wrappedReference.print();
        timerStop("print");
    }

    public<K >boolean containsKey(K arg0) {
        timerStart();
        boolean res;
        res = wrappedReference.containsKey(arg0);
        timerStop("containsKey");
        return res;
    }

    public<K >K higherKey(K arg0) {
        timerStart();
        K res;
        res = ((K) wrappedReference.higherKey(arg0));
        timerStop("higherKey");
        return res;
    }

    public<K >K lowerKey(K arg0) {
        timerStart();
        K res;
        res = ((K) wrappedReference.lowerKey(arg0));
        timerStop("lowerKey");
        return res;
    }

}
