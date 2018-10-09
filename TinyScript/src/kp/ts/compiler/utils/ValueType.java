/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.compiler.utils;

/**
 *
 * @author Asus
 */
public enum ValueType
{
    TS_VALUE(1, false, true),
    TS_LIB(1, false, false),
    INT(1, true, true),
    LONG(2, true, true),
    DOUBLE(2, true, true);
    
    private final int stackCost;
    private final boolean primitive;
    private final boolean varStorable;
    
    private ValueType(int stackCost, boolean primitive, boolean varStorable)
    {
        this.stackCost = stackCost;
        this.primitive = primitive;
        this.varStorable = varStorable;
    }
    
    public final int getStackCost() { return stackCost; }
    
    public final boolean isPrimitive() { return primitive; }
    
    public final boolean isValue() { return this == TS_VALUE; }
    public final boolean isLib() { return this == TS_LIB; }
    
    public final boolean isStorableInVariable() { return varStorable; }
}
