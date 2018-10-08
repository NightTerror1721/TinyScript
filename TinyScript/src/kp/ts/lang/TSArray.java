/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.lang;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import kp.ts.exception.TSRuntimeException;
import kp.ts.utils.Def;
import kp.ts.utils.ProtoObject;

/**
 *
 * @author Asus
 */
public final class TSArray extends TSValue
{
    private final TSValue[] array;
    private boolean frozen;
    
    public TSArray(TSValue... array) { this.array = array; }
    
    public final void setFrozen(boolean flag) { this.frozen = flag; }
    public final boolean isFrozen() { return frozen; }
    
    @Override
    public final TSDataType getTSDataType() { return TSDataType.ARRAY; }

    @Override
    public final int toInt() { return array.length > 0 ? 1 : 0; }

    @Override
    public final long toLong() { return array.length > 0 ? 1 : 0; }

    @Override
    public final float toFloat() { return array.length > 0 ? 1 : 0; }

    @Override
    public final double toDouble() { return array.length > 0 ? 1 : 0; }
    
    @Override
    public final boolean toBoolean() { return array.length != 0; }
    @Override
    public final String toString() { return Arrays.toString(array); }
    @Override
    public final TSValue[] toArray() { return array; }
    @Override
    public final List<TSValue> toList() { return Arrays.asList(array); }
    @Override
    public final Map<TSValue, TSValue> toMap()
    {
        HashMap<TSValue, TSValue> map = new HashMap<>();
        for(int i=0;i<array.length;i++)
            map.put(new TSNumber.Int32(i), array[i]);
        return map;
    }
    @Override
    public final TSObject toObject()
    {
        ProtoObject obj = new ProtoObject();
        for(int i=0;i<array.length;i++)
            obj.put(Integer.toString(i), array[i]);
        return obj.build(false);
    }
    
    @Override
    public final TSArray castToArray() { return this; }

    @Override
    public final boolean equals(Object o) { return o instanceof TSValue && Arrays.equals(array, ((TSValue) o).toArray()); }

    @Override
    public final int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + Arrays.deepHashCode(this.array);
        return hash;
    }
    
    
    
    @Override
    public final TSValue equals(TSValue value) { return Arrays.equals(array, value.toArray()) ? TRUE : FALSE; }
    @Override
    public final TSValue notEquals(TSValue value) { return Arrays.equals(array, value.toArray()) ? FALSE : TRUE; }
    @Override
    public final TSValue not() { return array.length == 0 ? TRUE : FALSE; }
    @Override
    public final TSValue length() { return new TSNumber.Int32(array.length); }
    
    @Override
    public final TSValue plus(TSValue value)
    {
        TSValue[] other = value.toArray();
        TSValue[] narray = new TSValue[array.length + other.length];
        System.arraycopy(array, 0, narray, 0, array.length);
        System.arraycopy(other, 0, narray, array.length, other.length);
        return new TSArray(narray);
    }
    
    @Override
    public final TSValue get(TSValue index)
    {
        TSValue value;
        return (value = array[index.toInt()]) == null ? UNDEFINED : value;
    }
    @Override
    public final TSValue get(int index)
    {
        TSValue value;
        return (value = array[index]) == null ? UNDEFINED : value;
    }
    @Override
    public final TSValue set(TSValue index, TSValue value)
    {
        if(frozen)
            throw new RuntimeException("Cannot modify frozen array");
        return array[index.toInt()] = value;
    }
    @Override
    public final TSValue set(int index, TSValue value)
    {
        if(frozen)
            throw new RuntimeException("Cannot modify frozen array");
        return array[index] = value;
    }
    
    @Override
    public final TSValue getProperty(String name)
    {
        switch(name)
        {
            default: return UNDEFINED;
            case "concat": return CONCAT;
            case "sort": return SORT;
            case "sortedCopy": return SORTED_COPY;
            case "subarray": return SUB_ARRAY;
        }
    }
    
    @Override
    public final TSValue call(TSValue self)
    {
        TSValue[] copy = new TSValue[array.length];
        System.arraycopy(array, 0, copy, 0, copy.length);
        return new TSArray(copy);
    }
    @Override
    public final TSValue call(TSValue self, TSValue arg0) { return new TSArray(arg0.toArray()); }
    
    @Override
    public final TSIterator iterator() { return new ArrayIterator(); }
    
    @Override
    public final TSValue createNewInstance()
    {
        TSValue[] copy = new TSValue[array.length];
        System.arraycopy(array, 0, copy, 0, copy.length);
        return new TSArray(copy);
    }
    @Override
    public final TSValue createNewInstance(TSValue arg0) { return new TSArray(new TSValue[arg0.toInt()]); }
    
    
    private final class ArrayIterator extends TSIterator
    {
        private int it = 0;
        
        @Override
        public final boolean hasNext() { return it < array.length; }

        @Override
        public final TSValue next()
        {
            TSValue value;
            return (value = array[it++]) == null ? UNDEFINED : value;
        }
    }
    
    
    private static final TSValue CONCAT = Def.method((self, arg0, arg1, arg2) -> {
        TSValue[] array = self.toArray();
        String del = arg0 == UNDEFINED ? "" : arg0.toString();
        int from = arg1 == UNDEFINED ? 0 : arg1.toInt();
        int to = arg2 == UNDEFINED ? array.length : arg2.toInt();
        if(to <= from)
            throw new TSRuntimeException("'to' value cannot small than 'from'");
        from = from < 0 ? 0 : from;
        to = to > array.length ? array.length : to;
        StringJoiner sj = new StringJoiner(del);
        int count = 0;
        for(TSValue val : array)
        {
            if(count++ < from)
                continue;
            if(count > to)
                break;
            sj.add(val.toString());
        }
        return new TSString(sj.toString());
    });
    
    private static final TSValue SORT = Def.vmethod((self, arg0) -> {
        if(arg0 == UNDEFINED)
        {
            Arrays.sort(self.toArray(), (v0, v1) -> {
                return v0.equals(v1).toBoolean() ? 0 :
                        v0.smallerThan(v1).toBoolean() ? -1 : 1;
            });
        }
        else Arrays.sort(self.toArray(), (v0, v1) -> arg0.call(UNDEFINED, v0, v1).toInt());
    });
    
    private static final TSValue SUB_ARRAY = Def.method((self, arg0, arg1) -> {
        if(arg1 == UNDEFINED)
            return new TSArray(Arrays.copyOf(self.toArray(), arg0.toInt()));
        return new TSArray(Arrays.copyOfRange(self.toArray(), arg0.toInt(), arg1.toInt()));
    });
    
    private static final TSValue SORTED_COPY = Def.method((self, arg0) -> {
        TSValue[] old = self.toArray();
        TSValue[] array = new TSValue[old.length];
        System.arraycopy(old, 0, array, 0, old.length);
        if(arg0 == UNDEFINED)
        {
            Arrays.sort(self.toArray(), (v0, v1) -> {
                return v0.equals(v1).toBoolean() ? 0 :
                        v0.smallerThan(v1).toBoolean() ? -1 : 1;
            });
        }
        else Arrays.sort(self.toArray(), (v0, v1) -> arg0.call(UNDEFINED, v0, v1).toInt());
        return new TSArray(array);
    });
}
