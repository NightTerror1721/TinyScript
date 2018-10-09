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
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import kp.ts.lang.TSIterator;
import kp.ts.lang.TSNative;
import kp.ts.lang.TSNumber;
import kp.ts.lang.TSObject;
import kp.ts.lang.TSValue;
import kp.ts.utils.Def;
import kp.ts.utils.ProtoObject;

/**
 *
 * @author Asus
 */
public class TSList extends TSNative implements List<TSValue>
{
    private final List<TSValue> list;
    
    public TSList(List<TSValue> list)
    {
        this.list = Objects.requireNonNull(list);
    }
    public TSList() { this.list = new LinkedList<>(); }
    
    @Override
    public int size() { return list.size(); }

    @Override
    public boolean isEmpty() { return list.isEmpty(); }

    @Override
    public boolean contains(Object o) { return list.contains(o); }

    @Override
    public Iterator<TSValue> iterator() { return list.iterator(); }

    @Override
    public <T> T[] toArray(T[] a) { return list.toArray(a); }

    @Override
    public boolean add(TSValue e) { return list.add(e); }

    @Override
    public boolean remove(Object o) { return list.remove(o); }

    @Override
    public boolean containsAll(Collection<?> c) { return list.containsAll(c); }

    @Override
    public boolean addAll(Collection<? extends TSValue> c) { return list.addAll(c); }

    @Override
    public boolean addAll(int index, Collection<? extends TSValue> c) { return list.addAll(index, c); }

    @Override
    public boolean removeAll(Collection<?> c) { return list.removeAll(c); }

    @Override
    public boolean retainAll(Collection<?> c) { return list.retainAll(c); }

    @Override
    public void clear() { list.clear(); }

    @Override
    public void add(int index, TSValue element) { list.add(index, element); }

    @Override
    public TSValue remove(int index) { return list.remove(index); }

    @Override
    public int indexOf(Object o) { return list.indexOf(o); }

    @Override
    public int lastIndexOf(Object o) { return list.lastIndexOf(o); }

    @Override
    public ListIterator<TSValue> listIterator() { return list.listIterator(); }

    @Override
    public ListIterator<TSValue> listIterator(int index) { return list.listIterator(index); }

    @Override
    public List<TSValue> subList(int fromIndex, int toIndex) { return list.subList(fromIndex, toIndex); }
    
    @Override
    public boolean equals(Object o) { return o instanceof TSValue && list.equals(((TSValue) o).toList()); }
    
    @Override
    public int hashCode() { return list.hashCode(); }
    
    
    
    
    
    @Override
    public final boolean toBoolean() { return !list.isEmpty(); }
    @Override
    public final String toString() { return list.toString(); }
    @Override
    public final TSValue[] toArray() { return list.toArray(new TSValue[list.size()]); }
    @Override
    public final List<TSValue> toList() { return this; }
    @Override
    public final Map<TSValue, TSValue> toMap()
    {
        HashMap<TSValue, TSValue> map = new HashMap<>();
        int count = 0;
        for(TSValue value : list)
            map.put(new TSNumber.Int32(count++), value);
        return map;
    }
    @Override
    public final TSObject toObject()
    {
        ProtoObject obj = new ProtoObject();
        int count = 0;
        for(TSValue value : list)
            obj.put(Integer.toString(count++), value);
        return obj.build(false);
    }
    
    @Override
    public final TSValue equals(TSValue value) { return list.equals(value.toList()) ? TRUE : FALSE; }
    @Override
    public final TSValue notEquals(TSValue value) { return list.equals(value.toList()) ? FALSE : TRUE; }
    @Override
    public final TSValue not() { return list.isEmpty() ? TRUE : FALSE; }
    @Override
    public final TSValue length() { return new TSNumber.Int32(list.size()); }
    
    @Override
    public final TSValue plus(TSValue value)
    {
        ArrayList<TSValue> copy = new ArrayList<>();
        copy.addAll(list);
        copy.addAll(value.toList());
        return new TSList(copy);
    }
    
    @Override
    public final TSValue bitwiseShiftLeft(TSValue value)
    {
        list.add(value);
        return this;
    }
    
    @Override
    public final TSValue get(TSValue index)
    {
        TSValue value;
        return (value = list.get(index.toInt())) == null ? UNDEFINED : value;
    }
    @Override
    public final TSValue set(TSValue index, TSValue value)
    {
        TSValue res;
        return (res = list.set(index.toInt(), value)) == null ? UNDEFINED : res;
    }
    @Override
    public final TSValue get(int index)
    {
        TSValue value;
        return (value = list.get(index)) == null ? UNDEFINED : value;
    }
    @Override
    public final TSValue set(int index, TSValue value)
    {
        TSValue res;
        return (res = list.set(index, value)) == null ? UNDEFINED : res;
    }
    
    @Override
    public final TSIterator createIterator() { return new TSListIterator(); }
    
    @Override
    public final TSValue getProperty(String name)
    {
        switch(name)
        {
            default: return UNDEFINED;
            case "add": return ADD;
            case "addAll": return ADD_ALL;
            case "clear": return CLEAR;
            case "contains": return CONTAINS;
            case "containsAll": return CONTAINS_ALL;
            case "get": return GET;
            case "indexOf": return INDEX_OF;
            case "isEmpty": return IS_EMPTY;
            case "lastIndexOf": return LAST_INDEX_OF;
            case "remove": return REMOVE;
            case "removeIndex": return REMOVE_INDEX;
            case "removeAll": return REMOVE_ALL;
            case "set": return SET;
            case "size": return SIZE;
            case "sort": return SORT;
            case "subList": return SUB_LIST;
        }
    }
    
    
    private static final TSValue SIZE = Def.<TSList>method((self) -> new TSNumber.Int32(self.list.size()));
    
    private static final TSValue IS_EMPTY = Def.<TSList>method((self) -> self.list.isEmpty() ? TRUE : FALSE);
    
    private static final TSValue CONTAINS = Def.<TSList>method((self, arg0) -> self.list.contains(arg0) ? TRUE : FALSE);
    
    private static final TSValue ADD = Def.<TSList>method((self, arg0, arg1) -> {
        if(arg1 == UNDEFINED)
            return self.list.add(arg0) ? TRUE : FALSE;
        self.list.add(arg0.toInt(), arg1);
        return TRUE;
    });
    
    private static final TSValue REMOVE = Def.<TSList>method((self, arg0) -> self.list.remove(arg0) ? TRUE : FALSE);
    
    private static final TSValue CONTAINS_ALL = Def.<TSList>method((self, arg0) -> self.list.containsAll(arg0.toList())? TRUE : FALSE);
    
    private static final TSValue ADD_ALL = Def.<TSList>method((self, arg0, arg1) -> {
        if(arg1 == UNDEFINED)
            return self.list.addAll(arg0.toList()) ? TRUE : FALSE;
        return self.list.addAll(arg0.toInt(), arg1.toList()) ? TRUE : FALSE;
    });
    
    private static final TSValue REMOVE_ALL = Def.<TSList>method((self, arg0) -> self.list.removeAll(arg0.toList()) ? TRUE : FALSE);
    
    private static final TSValue SORT = Def.<TSList>vmethod((self, arg0) -> {
        if(arg0 == UNDEFINED)
            self.list.sort((v0, v1) -> v0.equals(v1).toBoolean() ? 0 : v0.smallerThan(v1).toBoolean() ? -1 : 1);
        else self.list.sort((v0, v1) -> arg0.call(UNDEFINED, v0, v1).toInt());
    });
    
    private static final TSValue CLEAR = Def.<TSList>vmethod((self) -> self.list.clear());
    
    private static final TSValue GET = Def.<TSList>method((self, arg0) -> self.list.get(arg0.toInt()));
    
    private static final TSValue SET = Def.<TSList>method((self, arg0, arg1) -> self.list.set(arg0.toInt(), arg1));
    
    private static final TSValue REMOVE_INDEX = Def.<TSList>method((self, arg0) -> self.list.remove(arg0.toInt()));
    
    private static final TSValue INDEX_OF = Def.<TSList>method((self, arg0) -> new TSNumber.Int32(self.list.indexOf(arg0)));
    
    private static final TSValue LAST_INDEX_OF = Def.<TSList>method((self, arg0) -> new TSNumber.Int32(self.list.lastIndexOf(arg0)));
    
    private static final TSValue SUB_LIST = Def.<TSList>method((self, arg0, arg1) -> new TSList(self.list.subList(arg0.toInt(), arg1.toInt())));
    
    
    private final class TSListIterator extends TSIterator
    {
        private final Iterator<TSValue> it = list.iterator();
        @Override public final boolean hasNext() { return it.hasNext(); }
        @Override public final TSValue next() { return it.next(); }
    }
}
