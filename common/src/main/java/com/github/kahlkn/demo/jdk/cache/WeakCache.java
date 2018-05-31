package com.github.kahlkn.demo.jdk.cache;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakCache<K, V> {
    private final Map<K, Reference<V>> map = new WeakHashMap<K, Reference<V>>();

    public WeakCache() {}

    public V get(K key) {
        Reference<V> ref = this.map.get(key);
        if (ref == null) {
            return null;
        } else {
            V val = ref.get();
            if (val == null) {
                this.map.remove(key);
            }

            return val;
        }
    }

    public void put(K key, V val) {
        if (val != null) {
            this.map.put(key, new WeakReference<V>(val));
        } else {
            this.map.remove(key);
        }

    }

    public void clear() {
        this.map.clear();
    }
}

