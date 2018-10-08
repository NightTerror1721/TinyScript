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
public class TSNative extends TSValue
{
    @Override
    public final TSDataType getTSDataType() { return TSDataType.NATIVE; }

    @Override
    public int toInt() { return 1; }

    @Override
    public long toLong() { return 1; }

    @Override
    public float toFloat() { return 1; }

    @Override
    public double toDouble() { return 1; }

    @Override
    public boolean equals(Object o) { return this == o; }

    @Override
    public int hashCode() { return superHashCode(); }
    
    @Override
    public final <T extends TSNative> T castToNative() { return (T) this; }
    
    
    @Override
    public final TSValue getPointerValue() { return super.getPointerValue(); }
    @Override
    public final TSValue setPointerValue(TSValue value) { return super.setPointerValue(value); }
}
