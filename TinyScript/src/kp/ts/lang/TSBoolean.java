/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.lang;

/**
 *
 * @author Asus
 */
public final class TSBoolean extends TSValue
{
    static final TSBoolean TRUE_INSTANCE = new TSBoolean(true);
    static final TSBoolean FALSE_INSTANCE = new TSBoolean(false);
    
    private final boolean bool;
    
    private TSBoolean(boolean bool) { this.bool = bool; }
    
    @Override
    public final TSDataType getTSDataType() { return TSDataType.BOOLEAN; }

    @Override
    public final int toInt() { return bool ? 1 : 0; }

    @Override
    public final long toLong() { return bool ? 1 : 0; }

    @Override
    public final float toFloat() { return bool ? 1 : 0; }

    @Override
    public final double toDouble() { return bool ? 1 : 0; }
    
    @Override
    public final String toString() { return bool ? "true" : "false"; }
    
    @Override
    public final TSBoolean castToBoolean() { return this; }

    @Override
    public final boolean equals(Object o) { return o instanceof TSValue && bool == ((TSValue) o).toBoolean(); }

    @Override
    public final int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + (this.bool ? 1 : 0);
        return hash;
    }
    
    
    @Override
    public final TSValue equals(TSValue value) { return bool == value.toBoolean() ? TRUE : FALSE; }
    @Override
    public final TSValue notEquals(TSValue value) { return bool != value.toBoolean() ? TRUE : FALSE; }
    @Override
    public final TSValue not() { return bool ? TRUE : FALSE; }
    
    @Override
    public final TSValue bitwiseAnd(TSValue value) { return bool & value.toBoolean() ? TRUE : FALSE; }
    @Override
    public final TSValue bitwiseOr(TSValue value) { return bool | value.toBoolean() ? TRUE : FALSE; }
    @Override
    public final TSValue bitwiseXor(TSValue value) { return bool ^ value.toBoolean() ? TRUE : FALSE; }
    
    @Override
    public final TSValue call(TSValue self) { return FALSE; }
    @Override
    public final TSValue call(TSValue self, TSValue arg0) { return arg0.toBoolean() ? TRUE : FALSE; }
    
    @Override
    public final TSValue createNewInstance() { return FALSE; }
    @Override
    public final TSValue createNewInstance(TSValue arg0) { return arg0.toBoolean() ? TRUE : FALSE; }
    
}
