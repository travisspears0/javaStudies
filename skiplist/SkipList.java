package skiplist;

public class SkipList<K,V> {
    
    private SkipListItem<K,V> head;
    
    public SkipList() {
        this.head = new SkipListItem<>();
    }
    
    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }
    
    private SkipListItem<K,V> search() {
        throw new UnsupportedOperationException();
    }
    
    public void print() {
        SkipListItem<K,V> currentItemOuter = this.head;
        while(currentItemOuter.getBelow()!=null)currentItemOuter = currentItemOuter.getBelow();
        while(currentItemOuter != null) {
            SkipListItem<K,V> currentItemInner = currentItemOuter;
            while(currentItemInner.getBelow()!=null)currentItemInner = currentItemInner.getBelow();
            V value = currentItemInner.getValue();
            String s = "[" ;
            while(currentItemInner != null) {
                if(currentItemInner.getValue()!=null) {
                    s += currentItemInner+ ",";
                } else {
                    s += "-,";
                }
                currentItemInner = currentItemInner.getAbove();
            }
            s = s.substring(0,s.length()-1);
            s+="]";
            System.out.println(s);
            currentItemOuter = currentItemOuter.getNext();
        }
    }
    
    public K higherKey(K key) {
        throw new UnsupportedOperationException();
    }
    
    public K lowerKey(K key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean containsKey(K key) {
        throw new UnsupportedOperationException();
    }
    
    public V get(K key) {
        throw new UnsupportedOperationException();
    }
    
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean isEmpty() {
        SkipListItem<K,V> item = this.head;
        while(item.getBelow()!=null)item=item.getBelow();
        return item.getNext()==null;
    }
    
}
