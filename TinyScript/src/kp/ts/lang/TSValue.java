/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.lang;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kp.ts.exception.TSCastException;
import kp.ts.exception.TSUnsupportedOperationException;
import kp.ts.lang.TSObject.Property;
import kp.ts.lang.core.TSMap;

/**
 *
 * @author Asus
 */
public abstract class TSValue extends TSVarargs
{
    TSValue() {}
    
    public abstract TSDataType getTSDataType();
    
    public final boolean isUndefined() { return getTSDataType() == TSDataType.UNDEFINED; }
    public final boolean isNumber() { return getTSDataType() == TSDataType.NUMBER; }
    public final boolean isBoolean() { return getTSDataType() == TSDataType.BOOLEAN; }
    public final boolean isString() { return getTSDataType() == TSDataType.STRING; }
    public final boolean isArray() { return getTSDataType() == TSDataType.ARRAY; }
    public final boolean isObject() { return getTSDataType() == TSDataType.OBJECT; }
    public final boolean isFunction() { return getTSDataType() == TSDataType.FUNCTION; }
    public final boolean isIterator() { return getTSDataType() == TSDataType.ITERATOR; }
    public final boolean isNative() { return getTSDataType() == TSDataType.NATIVE; }
    
    public boolean isInteger() { return false; }
    public boolean isLongInteger() { return false; }
    public boolean isFloat() { return false; }
    public boolean isLongFloat() { return false; }
    public boolean isDecimal() { return false; }
    
    
    public abstract int toInt();
    public abstract long toLong();
    public abstract float toFloat();
    public abstract double toDouble();
    public boolean toBoolean() { return true; }
    @Override public String toString() { return getTSDataType().getTypeName() + "::" + Integer.toHexString(super.hashCode()); }
    public TSValue[] toArray() { return new TSValue[] { this }; }
    public List<TSValue> toList() { return Arrays.asList(toArray()); }
    public Map<TSValue, TSValue> toMap()
    {
        HashMap<TSValue, TSValue> map = new HashMap<>();
        map.put(new TSString("scalar"), this);
        return map;
    }
    public TSObject toObject()
    {
        HashMap<String, Property> obj = new HashMap<>();
        obj.put("scalar", new Property(this));
        return new TSObject(obj);
    }
    
    
    public TSNumber castToNumber() { throw new TSCastException(this, TSDataType.NUMBER); }
    public TSBoolean castToBoolean() { throw new TSCastException(this, TSDataType.BOOLEAN); }
    public TSString castToString() { throw new TSCastException(this, TSDataType.STRING); }
    public TSArray castToArray() { throw new TSCastException(this, TSDataType.ARRAY); }
    public TSObject castToObject() { throw new TSCastException(this, TSDataType.OBJECT); }
    public TSFunction castToFunction() { throw new TSCastException(this, TSDataType.FUNCTION); }
    public TSIterator castToIterator() { throw new TSCastException(this, TSDataType.ITERATOR); }
    public <T extends TSNative> T castToNative() { throw new TSCastException(this, TSDataType.NATIVE); }
    
    
    /* Common operators */
    public TSValue equals(TSValue value) { return this == value ? TRUE : FALSE; }
    public TSValue notEquals(TSValue value) { return this != value ? TRUE : FALSE; }
    public TSValue greaterThan(TSValue value) { throw new TSUnsupportedOperationException(this, "greaterThan"); }
    public TSValue smallerThan(TSValue value) { throw new TSUnsupportedOperationException(this, "smallerThan"); }
    public TSValue greaterOrEqualsThan(TSValue value) { throw new TSUnsupportedOperationException(this, "greaterOrEqualsThan"); }
    public TSValue smallerOrEqualsThan(TSValue value) { throw new TSUnsupportedOperationException(this, "smallerOrEqualsThan"); }
    public TSValue not() { return toBoolean() ? TRUE : FALSE; }
    public TSValue length() { throw new TSUnsupportedOperationException(this, "length"); }
    public final TSValue typedEquals(TSValue value) { return getTSDataType() == value.getTSDataType() ? equals(value) : FALSE; }
    public final TSValue typedNotEquals(TSValue value) { return getTSDataType() == value.getTSDataType() ? notEquals(value) : TRUE; }
    
    /* Math operators */
    public TSValue plus(TSValue value) { throw new TSUnsupportedOperationException(this, "plus"); }
    public TSValue minus(TSValue value) { throw new TSUnsupportedOperationException(this, "minus"); }
    public TSValue multiply(TSValue value) { throw new TSUnsupportedOperationException(this, "multiply"); }
    public TSValue divide(TSValue value) { throw new TSUnsupportedOperationException(this, "divide"); }
    public TSValue remainder(TSValue value) { throw new TSUnsupportedOperationException(this, "remainder"); }
    public TSValue increase() { throw new TSUnsupportedOperationException(this, "increase"); }
    public TSValue decrease() { throw new TSUnsupportedOperationException(this, "decrease"); }
    public TSValue negative() { throw new TSUnsupportedOperationException(this, "negative"); }
    
    /* Bitwise operators */
    public TSValue bitwiseShiftLeft(TSValue value) { throw new TSUnsupportedOperationException(this, "bitwiseShiftLeft"); }
    public TSValue bitwiseShiftRight(TSValue value) { throw new TSUnsupportedOperationException(this, "bitwiseShiftRight"); }
    public TSValue bitwiseAnd(TSValue value) { throw new TSUnsupportedOperationException(this, "bitwiseAnd"); }
    public TSValue bitwiseOr(TSValue value) { throw new TSUnsupportedOperationException(this, "bitwiseOr"); }
    public TSValue bitwiseXor(TSValue value) { throw new TSUnsupportedOperationException(this, "bitwiseXor"); }
    public TSValue bitwiseNot() { throw new TSUnsupportedOperationException(this, "bitwiseNot"); }
    
    /* Array operators */
    public TSValue get(TSValue index) { throw new TSUnsupportedOperationException(this, "get"); }
    public TSValue set(TSValue index, TSValue value) { throw new TSUnsupportedOperationException(this, "set"); }
    public TSValue get(int index) { return get(new TSNumber.Int32(index)); }
    public TSValue set(int index, TSValue value) { return set(new TSNumber.Int32(index), value); }
    
    /* Object operators */
    public TSValue getProperty(String name) { throw new TSUnsupportedOperationException(this, "getProperty"); }
    public TSValue setProperty(String name, TSValue value) { throw new TSUnsupportedOperationException(this, "setProperty"); }
    
    /* Function operators */
    public TSValue call(TSValue self) { throw new TSUnsupportedOperationException(this, "call"); }
    public TSValue call(TSValue self, TSValue arg0) { throw new TSUnsupportedOperationException(this, "call"); }
    public TSValue call(TSValue self, TSValue arg0, TSValue arg1) { throw new TSUnsupportedOperationException(this, "call"); }
    public TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2) { throw new TSUnsupportedOperationException(this, "call"); }
    public TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3) { throw new TSUnsupportedOperationException(this, "call"); }
    public TSValue call(TSValue self, TSVarargs args) { throw new TSUnsupportedOperationException(this, "call"); }
    
    public final TSValue staticCall() { return call(UNDEFINED); }
    public final TSValue staticCall(TSValue arg0) { return call(UNDEFINED, arg0); }
    public final TSValue staticCall(TSValue arg0, TSValue arg1) { return call(UNDEFINED, arg0, arg1); }
    public final TSValue staticCall(TSValue arg0, TSValue arg1, TSValue arg2) { return call(UNDEFINED, arg0, arg1, arg2); }
    public final TSValue staticCall(TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3) { return call(UNDEFINED, arg0, arg1, arg2, arg3); }
    public final TSValue staticCall(TSVarargs args) { return call(UNDEFINED, args); }
    
    public final TSValue invokeCall(String property) { return getProperty(property).call(this); }
    public final TSValue invokeCall(String property, TSValue arg0) { return getProperty(property).call(this, arg0); }
    public final TSValue invokeCall(String property, TSValue arg0, TSValue arg1) { return getProperty(property).call(this, arg0, arg1); }
    public final TSValue invokeCall(String property, TSValue arg0, TSValue arg1, TSValue arg2) { return getProperty(property).call(this, arg0, arg1, arg2); }
    public final TSValue invokeCall(String property, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3) { return getProperty(property).call(this, arg0, arg1, arg2, arg3); }
    public final TSValue invokeCall(String property, TSVarargs args) { return getProperty(property).call(this, args); }
    
    /* New Operator */
    public TSValue createNewInstance() { throw new TSUnsupportedOperationException(this, "createNewInstance"); }
    public TSValue createNewInstance(TSValue arg0) { return createNewInstance(); }
    public TSValue createNewInstance(TSValue arg0, TSValue arg1) { return createNewInstance(arg0); }
    public TSValue createNewInstance(TSValue arg0, TSValue arg1, TSValue arg2) { return createNewInstance(arg0, arg1); }
    public TSValue createNewInstance(TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3) { return createNewInstance(arg0, arg1, arg2); }
    public TSValue createNewInstance(TSVarargs args) { return createNewInstance(args.arg0(), args.arg(1), args.arg(2), args.arg(3)); }
    
    /* Iterator operators */
    public TSIterator createIterator() { throw new TSUnsupportedOperationException(this, "iterator"); }
    public boolean hasNext() { throw new TSUnsupportedOperationException(this, "hasNext"); }
    public TSValue next() { throw new TSUnsupportedOperationException(this, "next"); }
    
    /* Pointer Operators */
    public TSValue getPointerValue() { throw new TSUnsupportedOperationException(this, "getPointerValue"); }
    public TSValue setPointerValue(TSValue value) { throw new TSUnsupportedOperationException(this, "getPointerValue"); }
    
    
    /* Java Functions */
    @Override public abstract boolean equals(Object o);
    @Override public abstract int hashCode();
    
    protected final int superHashCode() { return super.hashCode(); }
    
    

    
    
    
    @Override
    public final int nargs() { return 1; }

    @Override
    public final TSValue arg0() { return this; }

    @Override
    public final TSValue arg(int index) { return index == 0 ? this : UNDEFINED; }
    
    
    public static final TSValue UNDEFINED = TSUndefined.INSTANCE;
    public static final TSValue TRUE = TSBoolean.TRUE_INSTANCE;
    public static final TSValue FALSE = TSBoolean.FALSE_INSTANCE;
    public static final TSValue MINUSONE = new TSNumber.Int32(-1);
    public static final TSValue ZERO = new TSNumber.Int32(0);
    public static final TSValue ONE = new TSNumber.Int32(1);
    
    
    
    public static final TSValue valueOf() { return UNDEFINED; }
    
    public static final TSValue valueOf(byte value) { return new TSNumber.Int32(value); }
    public static final TSValue valueOf(short value) { return new TSNumber.Int32(value); }
    public static final TSValue valueOf(int value) { return new TSNumber.Int32(value); }
    public static final TSValue valueOf(long value) { return new TSNumber.Int64(value); }
    public static final TSValue valueOf(float value) { return new TSNumber.Float32(value); }
    public static final TSValue valueOf(double value) { return new TSNumber.Float64(value); }
    public static final TSValue valueOf(char value) { return new TSNumber.Char(value); }
    public static final TSValue valueOf(boolean value) { return value ? TRUE : FALSE; }
    
    public static final TSValue valueOf(Byte value) { return new TSNumber.Int32(value); }
    public static final TSValue valueOf(Short value) { return new TSNumber.Int32(value); }
    public static final TSValue valueOf(Integer value) { return new TSNumber.Int32(value); }
    public static final TSValue valueOf(Long value) { return new TSNumber.Int64(value); }
    public static final TSValue valueOf(Float value) { return new TSNumber.Float32(value); }
    public static final TSValue valueOf(Double value) { return new TSNumber.Float64(value); }
    public static final TSValue valueOf(Character value) { return new TSNumber.Char(value); }
    public static final TSValue valueOf(Boolean value) { return value ? TRUE : FALSE; }
    
    public static final TSValue valueOf(String value) { return new TSString(value); }
    
    public static final TSValue valueOf(TSValue[] value) { return new TSArray(value); }
    public static final TSValue valueOf(byte[] value)
    {
        TSValue[] array = new TSValue[value.length];
        for(int i=0;i<value.length;i++)
            array[i] = new TSNumber.Int32(value[i]);
        return valueOf(array);
    }
    public static final TSValue valueOf(short[] value)
    {
        TSValue[] array = new TSValue[value.length];
        for(int i=0;i<value.length;i++)
            array[i] = new TSNumber.Int32(value[i]);
        return valueOf(array);
    }
    public static final TSValue valueOf(int[] value)
    {
        return valueOf(Arrays.stream(value).mapToObj(TSNumber.Int32::new).toArray(TSValue[]::new));
    }
    public static final TSValue valueOf(long[] value)
    {
        return valueOf(Arrays.stream(value).mapToObj(TSNumber.Int64::new).toArray(TSValue[]::new));
    }
    public static final TSValue valueOf(float[] value)
    {
        TSValue[] array = new TSValue[value.length];
        for(int i=0;i<value.length;i++)
            array[i] = new TSNumber.Float32(value[i]);
        return valueOf(array);
    }
    public static final TSValue valueOf(double[] value)
    {
        return valueOf(Arrays.stream(value).mapToObj(TSNumber.Float64::new).toArray(TSValue[]::new));
    }
    public static final TSValue valueOf(char[] value)
    {
        TSValue[] array = new TSValue[value.length];
        for(int i=0;i<value.length;i++)
            array[i] = new TSNumber.Char(value[i]);
        return valueOf(array);
    }
    public static final TSValue valueOf(boolean[] value)
    {
        TSValue[] array = new TSValue[value.length];
        for(int i=0;i<value.length;i++)
            array[i] = value[i] ? TRUE : FALSE;
        return valueOf(array);
    }
    
    public static final TSValue valueOf(Byte[] value)
    {
        return valueOf(Arrays.stream(value).map(TSNumber.Int32::new).toArray(TSValue[]::new));
    }
    public static final TSValue valueOf(Short[] value)
    {
        return valueOf(Arrays.stream(value).map(TSNumber.Int32::new).toArray(TSValue[]::new));
    }
    public static final TSValue valueOf(Integer[] value)
    {
        return valueOf(Arrays.stream(value).map(TSNumber.Int32::new).toArray(TSValue[]::new));
    }
    public static final TSValue valueOf(Long[] value)
    {
        return valueOf(Arrays.stream(value).map(TSNumber.Int64::new).toArray(TSValue[]::new));
    }
    public static final TSValue valueOf(Float[] value)
    {
        return valueOf(Arrays.stream(value).map(TSNumber.Float32::new).toArray(TSValue[]::new));
    }
    public static final TSValue valueOf(Double[] value)
    {
        return valueOf(Arrays.stream(value).map(TSNumber.Float64::new).toArray(TSValue[]::new));
    }
    public static final TSValue valueOf(Character[] value)
    {
        return valueOf(Arrays.stream(value).map(TSNumber.Char::new).toArray(TSValue[]::new));
    }
    public static final TSValue valueOf(Boolean[] value)
    {
        return valueOf(Arrays.stream(value).map(TSValue::valueOf).toArray(TSValue[]::new));
    }
    
    public static final TSValue valueOf(String[] value)
    {
        return valueOf(Arrays.stream(value).map(TSString::new).toArray(TSValue[]::new));
    }
    
    public static final TSValue valueOf(TSValue[][] value)
    {
        return valueOf(Arrays.stream(value).map(TSArray::new).toArray(TSValue[]::new));
    }
    
    public static final TSValue valueOf(Collection<TSValue> value)
    {
        return valueOf(value.toArray(new TSValue[value.size()]));
    }
    
    public static final TSValue valueOf(Map<TSValue, TSValue> value) { return new TSMap(value); }
    
    public static final TSValue valueOf(final Iterator<TSValue> value)
    {
        return new TSIterator()
        {
            @Override public final boolean hasNext() { return value.hasNext(); }
            @Override public final TSValue next() { return value.next(); }
        };
    }
}
