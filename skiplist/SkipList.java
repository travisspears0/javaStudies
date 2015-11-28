package skiplist;

import java.util.Random;

public class SkipList<K,V> {
    
    private static Random random = new Random();
    private SkipListItem<K,V> head;
    private int height = 0;
    
    public SkipList() {
        this.head = new SkipListItem<>();
    }
    
    public V put(K key, V value) {
        SkipListItem<K,V> item = this.search(key);
        if(item.getKey()!=null && item.compareTo(key)==0) {
            while(item!=null) {
                item.setValue(value);
                item = item.getAbove();
            }
            return value;
        }
        int currentLevel = 0;
        SkipListItem<K,V> lower = null;
        while(random.nextBoolean() || currentLevel==0) {
            if(currentLevel > this.height) {
                this.extend();
            }
            SkipListItem<K,V> newItem = new SkipListItem<>(key,value);
            SkipListItem<K,V> insertAfter = this.search(key,currentLevel);
            if(insertAfter.getNext()!=null) {
                insertAfter.getNext().setPrevious(newItem);
                newItem.setNext(insertAfter.getNext());
            }
            insertAfter.setNext(newItem);
            newItem.setPrevious(insertAfter);
            if(lower!=null) {
                lower.setAbove(newItem);
                newItem.setBelow(lower);
            }
            lower = newItem;
            ++currentLevel;
        }
        return value;
    }
    
    private SkipListItem<K,V> search(K key) {
        return this.search(key,0);
    }
    
    private SkipListItem<K,V> search(K key, int level) {
        SkipListItem<K,V> item = this.head;
        int currentLevel = this.height;
        while(true) {
            while(item.getNext()!=null && 
                    item.getNext().compareTo(key)<=0) {
                item = item.getNext();
            }
            if(currentLevel-- > level) {
                item = item.getBelow();
            } else {
                break;
            }
        }
        return item;
    }
    
    public K higherKey(K key) {
        try {
            return this.search(key).getNext().getKey();
        } catch(NullPointerException e) {
            return null;
        }
    }
    
    public K lowerKey(K key) {
        try {
            return this.search(key).getPrevious().getKey();
        } catch(NullPointerException e) {
            return null;
        }
    }
    
    public boolean containsKey(K key) {
        return (this.get(key)!=null);
    }
    
    public V get(K key) {
        try {
            SkipListItem<K,V> item = this.search(key);
            return (item.compareTo(key)==0) ? item.getValue() : null ;
        } catch(NullPointerException e) {
            return null;
        }
    }
    
    public V remove(K key) {
        SkipListItem<K,V> item = this.search(key);
        if(item.getKey()==null || item.compareTo(key)!=0) {
            return null;
        }
        V v = item.getValue();
        while(item!=null) {
            item.getPrevious().setNext(item.getNext());
            if(item.getNext()!=null) {
                item.getNext().setPrevious(item.getPrevious());
            }
            item=item.getAbove();
        }
        return v;
    }
    
    public boolean isEmpty() {
        SkipListItem<K,V> item = this.head;
        while(item.getBelow()!=null)item=item.getBelow();
        return item.getNext()==null;
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
                s += currentItemInner+ ",";
                currentItemInner = currentItemInner.getAbove();
            }
            s = s.substring(0,s.length()-1);
            s+="]";
            System.out.println(s);
            currentItemOuter = currentItemOuter.getNext();
        }
    }
    
    private void extend() {
        SkipListItem<K,V> newHead = new SkipListItem<>();
        this.head.setAbove(newHead);
        newHead.setBelow(this.head);
        this.head = newHead;
        ++this.height;
    }
    
}
