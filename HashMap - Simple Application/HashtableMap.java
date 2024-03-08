// --== CS400 Fall 2023 File Header Information ==--
// Name: <Zihan Xu>
// Email: <zxu536@wisc.edu>
// Group: <F38>
// TA: <Matt>
// Lecturer: <Gary>
// Notes to Grader: <I asked chagpt to write javadoc for me.>


import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;

/**
 * A hashtable-based implementation of the MapADT interface, mapping keys to values.
 *
 * @param <KeyType>   The type of keys maintained by this map.
 * @param <ValueType> The type of mapped values.
 */
public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

    private LinkedList<Pair>[] table;
    private int size;

    /**
     * Default constructor that initializes the hashtable with a default capacity.
     */
    public HashtableMap() {
        this(32);
    }

    /**
     * Constructor that initializes the hashtable with the given capacity.
     *
     * @param capacity The initial capacity of the hashtable.
     */
    @SuppressWarnings("unchecked")
    public HashtableMap(int capacity) {
        table = (LinkedList<Pair>[]) new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
        size = 0;
    }

    /**
     * Inner class representing a key-value pair.
     */
    protected class Pair {
        KeyType key;
        ValueType value;

        Pair(KeyType key, ValueType value) {
            this.key = key;
            this.value = value;
        }
    }


    /**
     * Inserts a key-value pair into the hashtable.
     *
     * @param key   The key to be inserted.
     * @param value The value to be associated with the key.
     * @throws IllegalArgumentException if a key already exists.
     * @throws NullPointerException     if the key is null.
     */
    public void put(KeyType key, ValueType value) throws IllegalArgumentException, NullPointerException {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }

        int index = Math.abs(key.hashCode()) % table.length;
        for (Pair pair : table[index]) {
            if (pair.key.equals(key)) {
                throw new IllegalArgumentException("Duplicate key");
            }
        }

        table[index].add(new Pair(key, value));
        size++;

        if ((1.0 * size) / table.length >= 0.75) {
            resize();
        }
    }

    /**
     * Checks if the hashtable contains the specified key.
     *
     * @param key The key to check for presence in the hashtable.
     * @return true if the hashtable contains the specified key, false otherwise.
     */
    @Override
    public boolean containsKey(KeyType key) {
        int index = Math.abs(key.hashCode()) % table.length;
        for (Pair pair : table[index]) {
            if (pair.key.equals(key)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Retrieves the value associated with the specified key.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value associated with the specified key.
     * @throws NoSuchElementException if the key does not exist in the hashtable.
     */
    @Override
    public ValueType get(KeyType key) throws NoSuchElementException {
        int index = Math.abs(key.hashCode()) % table.length;
        for (Pair pair : table[index]) {
            if (pair.key.equals(key)) {
                return pair.value;
            }
        }
        throw new NoSuchElementException("Key not found");
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key The key whose mapping is to be removed from the map.
     * @return The previous value associated with the key, or null if there was no mapping.
     * @throws NoSuchElementException if the key does not exist in the hashtable.
     */
    @Override
    public ValueType remove(KeyType key) throws NoSuchElementException {
        int index = Math.abs(key.hashCode()) % table.length;
        for (Pair pair : table[index]) {
            if (pair.key.equals(key)) {
                table[index].remove(pair);
                size--;
                return pair.value;
            }
        }
        throw new NoSuchElementException("Key not found");
    }

    /**
     * Returns the number of key-value pairs in this map.
     *
     * @return The number of key-value pairs in the map.
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Returns the capacity of the hashtable.
     *
     * @return The current capacity of the hashtable.
     */
    @Override
    public int getCapacity() {
        return table.length;
    }

    /**
     * Resizes the hashtable when the load factor threshold is exceeded.
     */
    private void resize() {
        LinkedList<Pair>[] oldTable = table;
        table = (LinkedList<Pair>[]) new LinkedList[table.length * 2];
        size = 0;
        for (int i = 0; i < table.length; i++) {
            table[i] = new LinkedList<>();
        }

        for (LinkedList<Pair> list : oldTable) {
            for (Pair pair : list) {
                put(pair.key, pair.value);
            }
        }
    }

    /**
     * Removes all mappings from this map.
     */
    @Override
    public void clear() {
        for (LinkedList<Pair> list : table) {
            list.clear();
        }
        size = 0;
    }


    /**
     * Tests the {@code put} and {@code get} methods for functionality.
     * Ensures that a value can be added and then retrieved successfully.
     */
    @Test
    public void testPutAndGet() {
        HashtableMap<String, Integer> testMap = new HashtableMap<>();
        testMap.put("key1", 10);
        Integer value = testMap.get("key1");
        assertEquals(10, value, "Put and get should retrieve the correct value.");
    }

    /**
     * Tests the {@code put} method for handling duplicate keys.
     * Ensures that an IllegalArgumentException is thrown when a duplicate key is inserted.
     */
    @Test
    public void testPutThrowsExceptionForDuplicateKey() {
        HashtableMap<String, Integer> testMap = new HashtableMap<>();
        testMap.put("key1", 10);
        assertThrows(IllegalArgumentException.class, () -> testMap.put("key1", 20),
                "Inserting a duplicate key should throw an IllegalArgumentException.");
    }

    /**
     * Tests the {@code remove} method for functionality and exception handling.
     * Verifies that the correct value is removed and that a NoSuchElementException
     * is thrown when trying to get a removed key.
     */
    @Test
    public void testRemove() {
        HashtableMap<String, Integer> testMap = new HashtableMap<>();
        testMap.put("key1", 10);
        int removedValue = testMap.remove("key1");
        assertEquals(10, removedValue, "Remove should return the correct value.");
        assertThrows(NoSuchElementException.class, () -> testMap.get("key1"),
                "Getting a removed key should throw a NoSuchElementException.");
    }

    /**
     * Tests the {@code remove} method for handling non-existent keys.
     * Ensures that a NoSuchElementException is thrown when attempting to remove
     * a key that does not exist in the map.
     */
    @Test
    public void testRemoveThrowsExceptionForNonexistentKey() {
        HashtableMap<String, Integer> testMap = new HashtableMap<>();
        assertThrows(NoSuchElementException.class, () -> testMap.remove("key2"),
                "Removing a nonexistent key should throw a NoSuchElementException.");
    }

    /**
     * Tests the {@code containsKey} method for both existing and non-existing keys.
     * Verifies that it correctly identifies the presence or absence of keys.
     */
    @Test
    public void testContainsKey() {
        HashtableMap<String, Integer> testMap = new HashtableMap<>();
        testMap.put("key1", 10);
        assertTrue(testMap.containsKey("key1"), "ContainsKey should return true for an existing key.");
        assertFalse(testMap.containsKey("key2"), "ContainsKey should return false for a nonexistent key.");
    }

    /**
     * Tests the {@code clear} and {@code getSize} methods.
     * Ensures that the map is correctly cleared and the size is reset to zero.
     */
    @Test
    public void testClearAndSize() {
        HashtableMap<String, Integer> testMap = new HashtableMap<>();
        testMap.put("key1", 10);
        testMap.put("key2", 20);
        testMap.clear();
        assertEquals(0, testMap.getSize(), "Clear should reset the size to zero.");
        assertFalse(testMap.containsKey("key1"), "After clear, containsKey should return false for 'key1'.");
        assertFalse(testMap.containsKey("key2"), "After clear, containsKey should return false for 'key2'.");
    }

    /**
     * Tests the {@code getCapacity} method.
     * Verifies that the initial capacity is set as expected.
     */
    @Test
    public void testGetCapacity() {
        HashtableMap<String, Integer> testMap = new HashtableMap<>();
        assertEquals(32, testMap.getCapacity(), "Initial capacity should be 32.");
    }

    /**
     * Tests the {@code put} method for handling null keys.
     * Ensures that a NullPointerException is thrown when attempting to insert a null key.
     */
    @Test
    public void testPutNullKeyThrowsException() {
        HashtableMap<String, Integer> testMap = new HashtableMap<>();
        assertThrows(NullPointerException.class, () -> testMap.put(null, 10),
                "Inserting a null key should throw a NullPointerException.");
    }


}
