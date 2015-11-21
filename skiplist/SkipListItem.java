package skiplist;

public class SkipListItem<K,V> implements Comparable<K> {
    
    private SkipListItem<K,V>
        above,
        below,
        previous,
        next;
    private final K key;
    private final V value;
    private static int CURRENT_ID = 0;
    private final int id = ++CURRENT_ID;

    public SkipListItem(K key,V value) {
        this.value = value;
        this.key = key;
    }
    
    @Override
    public String toString() {
        return "[" + this.key +"]" ;
    }
    
    public int getId() {
        return this.id;
    }

    @Override
    public int compareTo(K k) {
        return ((Comparable)this.key).compareTo(k);
    }

    public SkipListItem<K,V> getAbove() {
        return above;
    }

    public void setAbove(SkipListItem<K,V> above) {
        this.above = above;
    }

    public SkipListItem<K,V> getBelow() {
        return below;
    }

    public void setBelow(SkipListItem<K,V> below) {
        this.below = below;
    }

    public SkipListItem<K,V> getPrevious() {
        return previous;
    }

    public void setPrevious(SkipListItem<K,V> previous) {
        this.previous = previous;
    }

    public SkipListItem<K,V> getNext() {
        return next;
    }

    public void setNext(SkipListItem<K,V> next) {
        this.next = next;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
    
}
