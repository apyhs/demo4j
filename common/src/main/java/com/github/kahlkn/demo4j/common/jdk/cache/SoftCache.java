package com.github.kahlkn.demo4j.common.jdk.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.*;

public class SoftCache extends AbstractMap implements Map {
    private Map hash;
    private ReferenceQueue queue = new ReferenceQueue();
    private Set entrySet = null;

    private void processQueue() {
        SoftCache.ValueCell var1;
        while((var1 = (SoftCache.ValueCell)this.queue.poll()) != null) {
            if (var1.isValid()) {
                this.hash.remove(var1.key);
            } else {
                SoftCache.ValueCell.dropped--;
            }
        }

    }

    public SoftCache(int var1, float var2) {
        this.hash = new HashMap(var1, var2);
    }

    public SoftCache(int var1) {
        this.hash = new HashMap(var1);
    }

    public SoftCache() {
        this.hash = new HashMap();
    }

    @Override
    public int size() {
        return this.entrySet().size();
    }

    @Override
    public boolean isEmpty() {
        return this.entrySet().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return SoftCache.ValueCell.strip(this.hash.get(key), false) != null;
    }

    protected Object fill(Object var1) {
        return null;
    }

    @Override
    public Object get(Object var1) {
        this.processQueue();
        Object var2 = this.hash.get(var1);
        if (var2 == null) {
            var2 = this.fill(var1);
            if (var2 != null) {
                this.hash.put(var1, SoftCache.ValueCell.create(var1, var2, this.queue));
                return var2;
            }
        }

        return SoftCache.ValueCell.strip(var2, false);
    }

    @Override
    public Object put(Object var1, Object var2) {
        this.processQueue();
        SoftCache.ValueCell var3 = SoftCache.ValueCell.create(var1, var2, this.queue);
        return SoftCache.ValueCell.strip(this.hash.put(var1, var3), true);
    }

    @Override
    public Object remove(Object var1) {
        this.processQueue();
        return SoftCache.ValueCell.strip(this.hash.remove(var1), true);
    }

    @Override
    public void clear() {
        this.processQueue();
        this.hash.clear();
    }

    private static boolean valEquals(Object var0, Object var1) {
        return var0 == null ? var1 == null : var0.equals(var1);
    }

    @Override
    public Set entrySet() {
        if (this.entrySet == null) {
            this.entrySet = new SoftCache.EntrySet();
        }

        return this.entrySet;
    }

    private class Entry implements java.util.Map.Entry {
        private java.util.Map.Entry ent;
        private Object value;

        Entry(java.util.Map.Entry entry, Object var3) {
            this.ent = entry;
            this.value = var3;
        }

        @Override
        public Object getKey() {
            return this.ent.getKey();
        }

        @Override
        public Object getValue() {
            return this.value;
        }

        @Override
        public Object setValue(Object var1) {
            return this.ent.setValue(SoftCache.ValueCell.create(this.ent.getKey(), var1, SoftCache.this.queue));
        }

        @Override
        public boolean equals(Object var1) {
            if (!(var1 instanceof java.util.Map.Entry)) {
                return false;
            } else {
                java.util.Map.Entry var2 = (java.util.Map.Entry)var1;
                return SoftCache.valEquals(this.ent.getKey(), var2.getKey()) && SoftCache.valEquals(this.value, var2.getValue());
            }
        }

        @Override
        public int hashCode() {
            Object var1;
            return ((var1 = this.getKey()) == null ? 0 : var1.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
        }
    }

    private class EntrySet extends AbstractSet {
        Set hashEntries;

        private EntrySet() {
            this.hashEntries = SoftCache.this.hash.entrySet();
        }

        @Override
        public Iterator iterator() {
            return new Iterator() {
                Iterator hashIterator;
                SoftCache.Entry next;

                {
                    this.hashIterator = SoftCache.EntrySet.this.hashEntries.iterator();
                    this.next = null;
                }

                @Override
                public boolean hasNext() {
                    while(true) {
                        if (this.hashIterator.hasNext()) {
                            java.util.Map.Entry var1 = (java.util.Map.Entry)this.hashIterator.next();
                            SoftCache.ValueCell var2 = (SoftCache.ValueCell)var1.getValue();
                            Object var3 = null;
                            if (var2 != null && (var3 = var2.get()) == null) {
                                continue;
                            }

                            this.next = SoftCache.this.new Entry(var1, var3);
                            return true;
                        }

                        return false;
                    }
                }

                @Override
                public Object next() {
                    if (this.next == null && !this.hasNext()) {
                        throw new NoSuchElementException();
                    } else {
                        SoftCache.Entry nextEntry = this.next;
                        this.next = null;
                        return nextEntry;
                    }
                }

                @Override
                public void remove() {
                    this.hashIterator.remove();
                }

            };
        }

        @Override
        public boolean isEmpty() {
            return !this.iterator().hasNext();
        }

        @Override
        public int size() {
            int var1 = 0;
            Iterator var2 = this.iterator();

            while(var2.hasNext()) {
                ++var1;
                var2.next();
            }

            return var1;
        }

        @Override
        public boolean remove(Object var1) {
            SoftCache.this.processQueue();
            return var1 instanceof SoftCache.Entry ? this.hashEntries.remove(((SoftCache.Entry)var1).ent) : false;
        }
    }

    private static class ValueCell extends SoftReference {
        private static Object INVALID_KEY = new Object();
        private static int dropped = 0;
        private Object key;

        private ValueCell(Object key, Object referent, ReferenceQueue queue) {
            super(referent, queue);
            this.key = key;
        }

        private static SoftCache.ValueCell create(Object key, Object referent, ReferenceQueue queue) {
            return referent == null ? null : new SoftCache.ValueCell(key, referent, queue);
        }

        private static Object strip(Object val, boolean doDrop) {
            if (val == null) {
                return null;
            } else {
                SoftCache.ValueCell valCell = (SoftCache.ValueCell) val;
                Object realVal = valCell.get();
                if (doDrop) {
                    valCell.drop();
                }

                return realVal;
            }
        }

        private boolean isValid() {
            return this.key != INVALID_KEY;
        }

        private void drop() {
            super.clear();
            this.key = INVALID_KEY;
            ++dropped;
        }

    }

}
