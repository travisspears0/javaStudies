package skiplist;

public class SkipList<K,V> {
    
    private final int maxHeight;
    private int currentLevel = 0;
    private final SkipListItem<K,V> head;
    private final SkipListItem<K,V> tail;
    
    public SkipList(int maxHeight) {
        this.maxHeight = (maxHeight <= 0) ? 1 : maxHeight;
        
        SkipListItem<K,V> currentHead = new SkipListItem<>(null, null);
        SkipListItem<K,V> currentTail = new SkipListItem<>(null, null);
        for(int i=0 ; i<this.maxHeight-1 ; ++i) {
            SkipListItem<K,V> nextHead = new SkipListItem<>(null, null);
            nextHead.setBelow(currentHead);
            currentHead.setAbove(nextHead);
            
            SkipListItem<K,V> nextTail = new SkipListItem<>(null, null);
            nextTail.setBelow(currentTail);
            currentTail.setAbove(nextTail);
            
            currentHead.setNext(currentTail);
            currentTail.setPrevious(currentHead);
            
            currentHead = nextHead;
            currentTail = nextTail;
        }
        this.head = currentHead;
        this.tail = currentTail;
        this.head.setNext(this.tail);
        this.tail.setPrevious(this.head);
    }
    
    public V put(K key, V value) {
        this.currentLevel %= this.maxHeight;
        ++this.currentLevel;
        SkipListItem<K,V> previousItem = null;
        for(int i=0 ; i<this.currentLevel ; ++i) {
            SkipListItem<K,V> currentItem = this.findLowerItem(key,i+1);
            SkipListItem<K,V> addedItem = new SkipListItem<>(key, value);
            if(Main.testing)System.out.println("putting "+addedItem+" with prev "+currentItem+" on lvl "+(i+1));
            currentItem.getNext().setPrevious(addedItem);
            addedItem.setNext(currentItem.getNext());
            currentItem.setNext(addedItem);
            addedItem.setPrevious(currentItem);
            if(previousItem != null) {
                previousItem.setAbove(addedItem);
                addedItem.setBelow(previousItem);
            }
            previousItem = addedItem;
        }
        
        return value;
    }
    
    private SkipListItem<K,V> findLowerItem(K key) {
        return this.findLowerItem(key,0);
    }
    
    private SkipListItem<K,V> findLowerItem(K key, int level) {
        SkipListItem<K,V> currentItem = this.head;
        SkipListItem<K,V> switchToItem = null;
        int levelCounter = this.maxHeight;
        while(currentItem.getNext() != null) {
            int comp = (currentItem.getNext().getKey() == null) ? 
                    1 : 
                    currentItem.getNext().compareTo(key);
            if( comp >= 0 && levelCounter > level) {
                switchToItem = currentItem.getBelow();
                --levelCounter;
            } else if(comp < 0) {
                switchToItem = currentItem.getNext();
            }
            if(switchToItem == null) {
                break;
            }
            currentItem = switchToItem;
            switchToItem = null;
        }
        return currentItem;
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
        try {
            SkipListItem<K,V> item = this.findLowerItem(key);
            int steps = (!this.containsKey(key)) ? 1 : 2 ;
            for(int i=0 ; i<steps ; ++i) {
                item = item.getNext();
            }
            return item.getKey();
        } catch(NullPointerException e) {
            return null;
        }
    }
    
    public K lowerKey(K key) {
        if(key == null) return null;
        return this.findLowerItem(key).getKey();
    }
    
    public boolean containsKey(K key) {
        try {
            return this.findLowerItem(key).getNext().compareTo(key) == 0;
        } catch(NullPointerException e) {
            return false;
        }
    }
    
    public V get(K key) {
        try {
            SkipListItem<K,V> item = this.findLowerItem(key);
            item = item.getNext();
            return item.getValue();
        } catch(NullPointerException e) {
            return null;
        }
    }
    
    public V remove(K key) {
        SkipListItem<K,V> item = this.findLowerItem(key).getNext();
        if(item==null || item.getKey()==null)return null;
        V v = item.getValue();
        while(item != null) {
            if(item.getBelow()!= null) {
                item.getBelow().setAbove(null);
                item.setBelow(null);
            }
            item.getNext().setPrevious(item.getPrevious());
            item.getPrevious().setNext(item.getNext());
            item.setNext(null);
            item.setPrevious(null);
            item = item.getAbove();
        }
        return v;
    }
    
    public boolean isEmpty() {
        SkipListItem<K,V> item = this.head;
        while(item.getBelow()!=null)item=item.getBelow();
        return item.getNext().getKey() == null;
    }
    
    @Override
    public String toString() {
        String res="";
        
        res = res.substring(0,res.length()-1);
        return res;
    }
    
}
