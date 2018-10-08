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
import java.util.stream.Collectors;
import kp.ts.exception.TSRuntimeException;
import kp.ts.utils.SpecialProperty;

/**
 *
 * @author Asus
 */
public final class TSObject extends TSValue
{
    private final TSValue parent;
    private final Map<String, Property> properties;
    private boolean frozen;
    
    public TSObject(TSValue parent, Map<String, Property> properties, boolean frozen)
    {
        if(properties == null)
            throw new NullPointerException();
        this.parent = parent;
        this.properties = properties;
        this.frozen = frozen;
    }
    public TSObject(TSValue parent, Map<String, Property> properties)
    {
        if(properties == null)
            throw new NullPointerException();
        this.parent = parent;
        this.properties = properties;
    }
    public TSObject(Map<String, Property> properties, boolean frozen)
    {
        if(properties == null)
            throw new NullPointerException();
        this.parent = null;
        this.properties = properties;
        this.frozen = frozen;
    }
    public TSObject(Map<String, Property> properties)
    {
        if(properties == null)
            throw new NullPointerException();
        this.parent = null;
        this.properties = properties;
    }
    public TSObject(TSValue parent) { this.parent = parent; this.properties = new HashMap<>(); }
    public TSObject() { this.parent = null; this.properties = new HashMap<>(); }
    
    public final void setFrozen(boolean flag) { this.frozen = flag; }
    public final boolean isFrozen() { return frozen; }
    
    @Override
    public final TSDataType getTSDataType() { return TSDataType.OBJECT; }
    
    @Override
    public final TSObject castToObject() { return this; }
    
    
    @Override
    public final TSValue getProperty(String name)
    {
        Property prop = properties.get(name);
        if(prop != null)
            return prop.value;
        return parent != null ? parent.getProperty(name) : UNDEFINED;
    }
    
    @Override
    public final TSValue setProperty(String name, TSValue value)
    {
        if(frozen)
            throw new TSRuntimeException("Cannot change frozen object");
        Property prop = properties.get(name);
        if(prop == null)
        {
            properties.put(name, prop = new Property(value));
            return prop.value;
        }
        else
        {
            if(prop.frozen)
                throw new TSRuntimeException("Cannot change frozen object property");
            return prop.value = value == null ? UNDEFINED : value;
        }
    }

    @Override
    public final int toInt()
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.CAST_NUMBER)) == UNDEFINED
                ? 0
                : prop.call(this).toInt();
    }

    @Override
    public final long toLong()
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.CAST_NUMBER)) == UNDEFINED
                ? 0
                : prop.call(this).toLong();
    }

    @Override
    public final float toFloat()
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.CAST_NUMBER)) == UNDEFINED
                ? 0
                : prop.call(this).toFloat();
    }

    @Override
    public final double toDouble()
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.CAST_NUMBER)) == UNDEFINED
                ? 0
                : prop.call(this).toDouble();
    }
    
    @Override
    public final boolean toBoolean()
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.CAST_BOOLEAN)) == UNDEFINED
                ? true
                : prop.call(this).toBoolean();
    }
    
    @Override
    public final String toString()
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.CAST_STRING)) == UNDEFINED
                ? super.toString()
                : prop.call(this).toString();
    }
    
    @Override
    public final TSValue[] toArray()
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.CAST_ARRAY)) == UNDEFINED
                ? super.toArray()
                : prop.call(this).toArray();
    }
    
    @Override
    public final List<TSValue> toList()
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.CAST_ARRAY)) == UNDEFINED
                ? Arrays.asList(super.toArray())
                : Arrays.asList(prop.call(this).toArray());
    }
    
    @Override
    public final Map<TSValue, TSValue> toMap()
    {
        TSValue prop = getProperty(SpecialProperty.CAST_OBJECT);
        if(prop == UNDEFINED)
            prop = this;
        else prop = prop.call(this);
        return prop.castToObject().properties.entrySet().stream().collect(Collectors.toMap(
                e -> new TSString(e.getKey()), e -> e.getValue().value));
    }
    
    @Override
    public final TSObject toObject()
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.CAST_OBJECT)) == UNDEFINED
                ? this
                : prop.call(this).castToObject();
    }

    @Override
    public final boolean equals(Object o) { return o instanceof TSValue && equals((TSValue) o).toBoolean(); }

    @Override
    public final int hashCode()
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.HASHCODE)) == UNDEFINED
                ? superHashCode()
                : prop.call(this).toInt();
    }
    
    
    @Override
    public final TSValue equals(TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_EQUALS)) == UNDEFINED
                ? this == value ? TRUE : FALSE
                : prop.call(this, value);
    }
    @Override
    public final TSValue notEquals(TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_NOT_EQUALS)) == UNDEFINED
                ? this != value ? TRUE : FALSE
                : prop.call(this, value);
    }
    @Override
    public final TSValue greaterThan(TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_GREATER_THAN)) == UNDEFINED
                ? super.greaterThan(value)
                : prop.call(this, value);
    }
    @Override
    public final TSValue smallerThan(TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_SMALLER_THAN)) == UNDEFINED
                ? super.smallerThan(value)
                : prop.call(this, value);
    }
    @Override
    public final TSValue greaterOrEqualsThan(TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_GREATER_EQUALS_THAN)) == UNDEFINED
                ? super.greaterOrEqualsThan(value)
                : prop.call(this, value);
    }
    @Override
    public final TSValue smallerOrEqualsThan(TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_GREATER_EQUALS_THAN)) == UNDEFINED
                ? super.smallerOrEqualsThan(value)
                : prop.call(this, value);
    }
    @Override
    public final TSValue not()
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_NOT)) == UNDEFINED
                ? toBoolean() ? TRUE : FALSE
                : prop.call(this);
    }
    @Override
    public final TSValue length()
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_LENGTH)) == UNDEFINED
                ? super.length()
                : prop.call(this);
    }
    
    @Override
    public final TSValue plus(TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_PLUS)) == UNDEFINED
                ? super.plus(value)
                : prop.call(this, value);
    }
    @Override
    public final TSValue minus(TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_MINUS)) == UNDEFINED
                ? super.minus(value)
                : prop.call(this, value);
    }
    @Override
    public final TSValue multiply(TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_MULTIPLY)) == UNDEFINED
                ? super.multiply(value)
                : prop.call(this, value);
    }
    @Override
    public final TSValue divide(TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_DIVIDE)) == UNDEFINED
                ? super.divide(value)
                : prop.call(this, value);
    }
    @Override
    public final TSValue remainder(TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_REMAINDER)) == UNDEFINED
                ? super.remainder(value)
                : prop.call(this, value);
    }
    @Override
    public final TSValue increase()
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_INCREASE)) == UNDEFINED
                ? super.increase()
                : prop.call(this);
    }
    @Override
    public final TSValue decrease()
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_DECREASE)) == UNDEFINED
                ? super.decrease()
                : prop.call(this);
    }
    @Override
    public final TSValue negative()
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_NEGATIVE)) == UNDEFINED
                ? super.negative()
                : prop.call(this);
    }
    
    @Override
    public final TSValue bitwiseShiftLeft(TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_BITWISE_SHIFT_LEFT)) == UNDEFINED
                ? super.bitwiseShiftLeft(value)
                : prop.call(this, value);
    }
    @Override
    public final TSValue bitwiseShiftRight(TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_BITWISE_SHIFT_RIGHT)) == UNDEFINED
                ? super.bitwiseShiftRight(value)
                : prop.call(this, value);
    }
    @Override
    public final TSValue bitwiseAnd(TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_BITWISE_AND)) == UNDEFINED
                ? super.bitwiseAnd(value)
                : prop.call(this, value);
    }
    @Override
    public final TSValue bitwiseOr(TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_BITWISE_OR)) == UNDEFINED
                ? super.bitwiseOr(value)
                : prop.call(this, value);
    }
    @Override
    public final TSValue bitwiseXor(TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_BITWISE_XOR)) == UNDEFINED
                ? super.bitwiseXor(value)
                : prop.call(this, value);
    }
    @Override
    public final TSValue bitwiseNot()
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_BITWISE_NOT)) == UNDEFINED
                ? super.bitwiseNot()
                : prop.call(this);
    }
    
    @Override
    public final TSValue get(TSValue index)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_GET)) == UNDEFINED
                ? super.get(index)
                : prop.call(this, index);
    }
    @Override
    public final TSValue set(TSValue index, TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_GET)) == UNDEFINED
                ? super.set(index, value)
                : prop.call(this, index, value);
    }
    @Override
    public final TSValue get(int index)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_GET)) == UNDEFINED
                ? super.get(index)
                : prop.call(this, new TSNumber.Int32(index));
    }
    @Override
    public final TSValue set(int index, TSValue value)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_GET)) == UNDEFINED
                ? super.set(index, value)
                : prop.call(this, new TSNumber.Int32(index), value);
    }
    
    @Override
    public final TSValue call(TSValue self)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_BITWISE_XOR)) == UNDEFINED
                ? super.call(self)
                : prop.call(this, self);
    }
    @Override
    public final TSValue call(TSValue self, TSValue arg0)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_BITWISE_XOR)) == UNDEFINED
                ? super.call(self, arg0)
                : prop.call(this, self, arg0);
    }
    @Override
    public final TSValue call(TSValue self, TSValue arg0, TSValue arg1)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_BITWISE_XOR)) == UNDEFINED
                ? super.call(self, arg0, arg1)
                : prop.call(this, self, arg0, arg1);
    }
    @Override
    public final TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_BITWISE_XOR)) == UNDEFINED
                ? super.call(self, arg0, arg1, arg2)
                : prop.call(this, self, arg0, arg1, arg2);
    }
    @Override
    public final TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_BITWISE_XOR)) == UNDEFINED
                ? super.call(self, arg0, arg1, arg2, arg3)
                : prop.call(this, varargsOf(self, arg0, arg1, arg2, arg3));
    }
    @Override
    public final TSValue call(TSValue self, TSVarargs args)
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.OP_BITWISE_XOR)) == UNDEFINED
                ? super.call(self, args)
                : prop.call(this, varargsOf(self, args));
    }
    
    @Override
    public final TSValue createNewInstance()
    {
        TSObject object = new TSObject(this, new HashMap<>());
        TSValue prop = getProperty(SpecialProperty.CONSTRUCTOR);
        if(prop != UNDEFINED)
            prop.call(object);
        return object;
    }
    @Override
    public final TSValue createNewInstance(TSValue arg0)
    {
        TSObject object = new TSObject(this, new HashMap<>());
        TSValue prop = getProperty(SpecialProperty.CONSTRUCTOR);
        if(prop != UNDEFINED)
            prop.call(object, arg0);
        return object;
    }
    @Override
    public final TSValue createNewInstance(TSValue arg0, TSValue arg1)
    {
        TSObject object = new TSObject(this, new HashMap<>());
        TSValue prop = getProperty(SpecialProperty.CONSTRUCTOR);
        if(prop != UNDEFINED)
            prop.call(object, arg0, arg1);
        return object;
    }
    @Override
    public final TSValue createNewInstance(TSValue arg0, TSValue arg1, TSValue arg2)
    {
        TSObject object = new TSObject(this, new HashMap<>());
        TSValue prop = getProperty(SpecialProperty.CONSTRUCTOR);
        if(prop != UNDEFINED)
            prop.call(object, arg0, arg1, arg2);
        return object;
    }
    @Override
    public final TSValue createNewInstance(TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3)
    {
        TSObject object = new TSObject(this, new HashMap<>());
        TSValue prop = getProperty(SpecialProperty.CONSTRUCTOR);
        if(prop != UNDEFINED)
            prop.call(object, arg0, arg1, arg2, arg3);
        return object;
    }
    @Override
    public final TSValue createNewInstance(TSVarargs args)
    {
        TSObject object = new TSObject(this, new HashMap<>());
        TSValue prop = getProperty(SpecialProperty.CONSTRUCTOR);
        if(prop != UNDEFINED)
            prop.call(object, args);
        return object;
    }
    
    @Override
    public final TSIterator iterator()
    {
        TSValue prop;
        return (prop = getProperty(SpecialProperty.CAST_ITERATOR)) == UNDEFINED
                ? super.iterator()
                : prop.call(this).castToIterator();
    }
    
    
    
    
    
    public static final class Property
    {
        private TSValue value;
        private boolean frozen;
        
        public Property(TSValue value, boolean frozen)
        {
            this.value = value == null ? UNDEFINED : value;
            this.frozen = frozen;
        }
        
        public Property(TSValue value)
        {
            this.value = value == null ? UNDEFINED : value;
        }
        
        public final void setPropertyValue(TSValue value)
        {
            this.value = value == null ? UNDEFINED : value;
        }
        public final TSValue getPropertyValue() { return value; }
        
        public final void setFrozen(boolean flag) { this.frozen = flag; }
        public final boolean isFrozen() { return frozen; }
    }
}
