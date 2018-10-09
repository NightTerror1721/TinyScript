/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.lang.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import kp.ts.lang.TSIterator;
import kp.ts.lang.TSNative;
import kp.ts.lang.TSNumber;
import kp.ts.lang.TSObject;
import kp.ts.lang.TSValue;
import static kp.ts.lang.TSValue.FALSE;
import static kp.ts.lang.TSValue.TRUE;
import kp.ts.utils.Def;
import kp.ts.utils.ProtoObject;

/**
 *
 * @author Asus
 */
public class TSMap extends TSNative implements Map<TSValue, TSValue>
{
    private final Map<TSValue, TSValue> map;
    
    public TSMap(Map<TSValue, TSValue> map) { this.map = Objects.requireNonNull(map); }
    
    public TSMap() { this.map = new HashMap<>(); }
    
    @Override
    public int size() { return map.size(); }

    @Override
    public boolean isEmpty() { return map.isEmpty(); }

    @Override
    public boolean containsKey(Object key) { return map.containsKey(key); }

    @Override
    public boolean containsValue(Object value) { return map.containsValue(value); }

    @Override
    public TSValue get(Object key) { return map.get(key); }

    @Override
    public TSValue put(TSValue key, TSValue value) { return map.put(key, value); }

    @Override
    public TSValue remove(Object key) { return map.remove(key); }

    @Override
    public void putAll(Map<? extends TSValue, ? extends TSValue> m) { map.putAll(m); }

    @Override
    public void clear() { map.clear(); }

    @Override
    public Set<TSValue> keySet() { return map.keySet(); }

    @Override
    public Collection<TSValue> values() { return map.values(); }

    @Override
    public Set<Entry<TSValue, TSValue>> entrySet() { return map.entrySet(); }
    
    @Override
    public boolean equals(Object o) { return o instanceof TSValue && map.equals(((TSValue) o).toMap()); }
    
    @Override
    public int hashCode() { return map.hashCode(); }
    
    
    
    @Override
    public final boolean toBoolean() { return !map.isEmpty(); }
    @Override
    public final String toString() { return map.toString(); }
    @Override
    public final TSValue[] toArray() { return map.entrySet().stream().map(TSMapEntry::new).toArray(TSValue[]::new); }
    @Override
    public final List<TSValue> toList() { return map.entrySet().stream().map(TSMapEntry::new).collect(Collectors.toList()); }
    @Override
    public final Map<TSValue, TSValue> toMap() { return this; }
    @Override
    public final TSObject toObject()
    {
        ProtoObject obj = new ProtoObject();
        for(Map.Entry<TSValue, TSValue> e : map.entrySet())
            obj.put(e.getKey().toString(), e.getValue());
        return obj.build(false);
    }
    
    @Override
    public final TSValue equals(TSValue value) { return map.equals(value.toMap()) ? TRUE : FALSE; }
    @Override
    public final TSValue notEquals(TSValue value) { return map.equals(value.toMap()) ? FALSE : TRUE; }
    @Override
    public final TSValue not() { return map.isEmpty() ? TRUE : FALSE; }
    @Override
    public final TSValue length() { return new TSNumber.Int32(map.size()); }
    
    @Override
    public final TSValue plus(TSValue value)
    {
        HashMap<TSValue, TSValue> copy = new HashMap<>(map);
        copy.putAll(value.toMap());
        return new TSMap(copy);
    }
    
    @Override
    public final TSValue get(TSValue index)
    {
        TSValue value;
        return (value = map.get(index)) == null ? UNDEFINED : value;
    }
    @Override
    public final TSValue set(TSValue index, TSValue value)
    {
        TSValue res;
        return (res = map.put(index, value)) == null ? UNDEFINED : res;
    }
    @Override
    public final TSValue get(int index)
    {
        TSValue value;
        return (value = map.get(new TSNumber.Int32(index))) == null ? UNDEFINED : value;
    }
    @Override
    public final TSValue set(int index, TSValue value)
    {
        TSValue res;
        return (res = map.put(new TSNumber.Int32(index), value)) == null ? UNDEFINED : res;
    }
    
    @Override
    public final TSIterator createIterator() { return new TSMapIterator(); }
    
    @Override
    public final TSValue getProperty(String name)
    {
        switch(name)
        {
            default: return UNDEFINED;
            case "clear": return CLEAR;
            case "containsKey":
            case "hasKey": return CONTAINS_KEY;
            case "containsValue":
            case "hasValue": return CONTAINS_VALUE;
            case "entries": return ENTRIES;
            case "get": return GET;
            case "isEmpty": return IS_EMPTY;
            case "keys": return KEYS;
            case "put": return PUT;
            case "putAll": return PUT_ALL;
            case "remove": return REMOVE;
            case "size": return SIZE;
            case "values": return VALUES;
        }
    }
    
    
    
    
    private static final class TSMapEntry extends TSNative
    {
        private final Map.Entry<TSValue, TSValue> entry;
        
        private TSMapEntry(Map.Entry<TSValue, TSValue> entry) { this.entry = entry; }
        
        @Override
        public final TSValue get(TSValue index)
        {
            switch(index.toInt())
            {
                default: return UNDEFINED;
                case 0: return entry.getKey();
                case 1: return entry.getValue();
            }
        }
        
        @Override
        public final TSValue set(TSValue index, TSValue value)
        {
            switch(index.toInt())
            {
                default: return UNDEFINED;
                case 1: return entry.setValue(value);
            }
        }
        
        @Override
        public final TSValue get(int index)
        {
            switch(index)
            {
                default: return UNDEFINED;
                case 0: return entry.getKey();
                case 1: return entry.getValue();
            }
        }
        
        @Override
        public final TSValue set(int index, TSValue value)
        {
            switch(index)
            {
                default: return UNDEFINED;
                case 1: return entry.setValue(value);
            }
        }
        
        @Override
        public final TSValue getProperty(String name)
        {
            switch(name)
            {
                default: return UNDEFINED;
                case "key": return entry.getKey();
                case "value": return entry.getValue();
            }
        }
        
        @Override
        public final TSValue setProperty(String name, TSValue value)
        {
            switch(name)
            {
                default: return UNDEFINED;
                case "value": return entry.setValue(value);
            }
        }
    }
    
    private final class TSMapIterator extends TSIterator
    {
        private final Iterator<Map.Entry<TSValue, TSValue>> it = map.entrySet().iterator();
        @Override public final boolean hasNext() { return it.hasNext(); }
        @Override public final TSValue next() { return new TSMapEntry(it.next()); }
    }
    
    
    private static final TSValue SIZE = Def.<TSMap>method((self) -> new TSNumber.Int32(self.map.size()));
    
    private static final TSValue IS_EMPTY = Def.<TSMap>method((self) -> self.map.isEmpty() ? TRUE : FALSE);
    
    private static final TSValue CONTAINS_KEY = Def.<TSMap>method((self, arg0) -> self.map.containsKey(arg0)? TRUE : FALSE);
    
    private static final TSValue CONTAINS_VALUE = Def.<TSMap>method((self, arg0) -> self.map.containsValue(arg0) ? TRUE : FALSE);
    
    private static final TSValue GET = Def.<TSMap>method((self, arg0) -> self.map.get(arg0));
    
    private static final TSValue PUT = Def.<TSMap>method((self, arg0, arg1) -> self.map.put(arg0, arg1));
    
    private static final TSValue REMOVE = Def.<TSMap>method((self, arg0) -> self.map.remove(arg0));
    
    private static final TSValue PUT_ALL = Def.<TSMap>vmethod((self, arg0) -> self.map.putAll(arg0.toMap()));
    
    private static final TSValue CLEAR = Def.<TSMap>vmethod((self) -> self.map.clear());
    
    private static final TSValue KEYS = Def.<TSMap>method((self) -> new TSList(new ArrayList<>(self.map.keySet())));
    
    private static final TSValue VALUES = Def.<TSMap>method((self) -> new TSList(new ArrayList<>(self.map.values())));
    
    private static final TSValue ENTRIES = Def.<TSMap>method((self) -> new TSList(self.map.entrySet().stream().map(TSMapEntry::new).collect(Collectors.toList())));
}
