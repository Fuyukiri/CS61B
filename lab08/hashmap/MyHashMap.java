package hashmap;

import java.util.*;

/**
 * A hash table-backed Map implementation. Provides amortized constant time
 * access to elements via get(), remove(), and put() in the best case.
 * <p>
 * Assumes null keys will never be inserted, and does not resize down upon remove().
 *
 * @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /* Instance Variables */
    private int capacity;
    private int size;
    private final double loadFactor;
    private final double DEFAULT_LOAD_FACTOR = 0.75;
    private final int DEFAULT_INITIAL_SIZE = 16;
    private Collection<Node>[] buckets;
    // You should probably define some more!

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private class MyHashMapIterator implements Iterator<K> {
        private int index;
        private Iterator<Node> current;

        public MyHashMapIterator() {
            index = 0;
            for (; index < capacity; index++) {
                if (buckets[index] == null || buckets[index].size() == 0) {
                    continue;
                }
                current = buckets[index].iterator();
                break;
            }
        }
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public K next() {
            var node = current.next();
            if (!current.hasNext()) {
                index++;
                for (; index < capacity; index++) {
                    if (buckets[index] == null || buckets[index].size() == 0) {
                        continue;
                    }
                    current = buckets[index].iterator();
                    break;
                }
            }

            if (!current.hasNext()) {
                current = null;
            }
            return node.key;
        }
    }

    /**
     * Constructors
     */
    public MyHashMap() {
        capacity = DEFAULT_INITIAL_SIZE;
        loadFactor = DEFAULT_LOAD_FACTOR;
        buckets = createTable(capacity);
        size = 0;
    }

    public MyHashMap(int initialSize) {
        this.capacity = DEFAULT_INITIAL_SIZE;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        buckets = createTable(initialSize);
        size = 0;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad     maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.capacity = initialSize;
        this.loadFactor = maxLoad;
        buckets = createTable(initialSize);
        size = 0;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    @Override
    public void clear() {
        capacity = DEFAULT_INITIAL_SIZE;
        buckets = createTable(capacity);
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        var hashcode = Math.abs(key.hashCode());
        hashcode %= capacity;
        var bucket = buckets[hashcode];
        if (bucket == null) {
            return false;
        }
        for (var node : bucket) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        var hashcode = Math.abs(key.hashCode());
        hashcode %= capacity;
        var bucket = buckets[hashcode];
        if (bucket == null) {
            return null;
        }
        for (var node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (size >= loadFactor * capacity) {
            resize();
        }
        var hashcode = Math.abs(key.hashCode());
        hashcode %= capacity;
        var bucket = buckets[hashcode];
        if (bucket == null) {
            buckets[hashcode] = createBucket();
        }
        for (var node : buckets[hashcode]) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        buckets[hashcode].add(createNode(key, value));
        size++;
    }

    @Override
    public Set<K> keySet() {
        var set = new HashSet<K>();
        for (K k : this) {
            set.add(k);
        }
        return set;
    }

    @Override
    public V remove(K key) {
        var hashcode = Math.abs(key.hashCode());
        hashcode %= capacity;
        var bucket = buckets[hashcode];
        if (bucket == null) {
            return null;
        }

        var iter = buckets[hashcode].iterator();
        while (iter.hasNext()) {
            var node = iter.next();
            if (node.key.equals(key)) {
                iter.remove();
                size--;
                return node.value;
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        var hashcode = Math.abs(key.hashCode());
        hashcode %= capacity;
        var bucket = buckets[hashcode];
        if (bucket == null) {
            return null;
        }
        var iter = buckets[hashcode].iterator();
        while (iter.hasNext()) {
            var node = iter.next();
            if (node.key.equals(key) && node.value.equals(value)) {
                iter.remove();
                size--;
                return node.value;
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }

    private void resize() {
        capacity *= 2;
        Collection<Node>[] buckets = createTable(capacity);
        for (var bucket : this.buckets) {
            if (bucket == null) {
                continue;
            }
            for (var ele : bucket) {
                int hashcode = Math.abs(ele.key.hashCode());
                hashcode %= capacity;
                if (buckets[hashcode] == null) {
                    buckets[hashcode] = createBucket();
                }
                buckets[hashcode].add(createNode(ele.key, ele.value));
            }
        }
        this.buckets = buckets;
    }
}
