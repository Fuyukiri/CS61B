package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class BSTNode {
        private K key;
        private V val;
        private BSTNode left;
        private BSTNode right;

        public BSTNode(K key, V val) {
            this.key = key;
            this.val = val;
        }

    }

    private BSTNode root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }

    private boolean containsKey(BSTNode node, K key) {
        if (node == null) {
            return false;
        }
        int compareVal = key.compareTo(node.key);
        if (compareVal == 0) {
            return true;
        } else if (compareVal < 0) {
            return containsKey(node.left, key);
        } else {
            return containsKey(node.right, key);
        }
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(BSTNode node, K key) {
        if (node == null) {
            return null;
        }
        int compareVal = key.compareTo(node.key);
        if (compareVal == 0) {
            return node.val;
        } else if (compareVal < 0) {
            return get(node.left, key);
        } else {
            return get(node.right, key);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private BSTNode put(BSTNode node, K key, V value) {
        if (node == null) {
            size++;
            return new BSTNode(key, value);
        }
        int compareVal = key.compareTo(node.key);
        if (compareVal == 0) {
            node.val = value;
        } else if (compareVal < 0) {
            node.left = put(node.left, key, value);
        } else {
            node.right = put(node.right, key, value);
        }
        return node;
    }


    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public void printInOrder() {

    }
}
