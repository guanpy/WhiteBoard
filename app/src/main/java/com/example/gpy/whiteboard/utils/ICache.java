package com.example.gpy.whiteboard.utils;

public interface ICache<K, V> {

    /**
     * put the KV pair into cache
     * 
     * @param key
     * @param value
     * @return the previous value of the key
     */
    V put(K key, V value);

    /**
     * get the value by key
     * 
     * @param key
     * @return the value of the key
     */
    V get(K key);

    /**
     * remove the KV pair
     * 
     * @param key
     * @return the previous value of the key
     */
    V remove(K key);

    /**
     * clear the whole cache
     */
    void clear();

    /**
     * return whether beyond the interval
     * @param interval
     * @return
     */
    boolean isOutOfDate(K key, long interval);
}
