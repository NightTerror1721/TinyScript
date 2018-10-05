/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.lang;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Asus
 */
final class TSUndefined extends TSValue
{
    static final TSUndefined INSTANCE = new TSUndefined();
    
    private TSUndefined() { if(INSTANCE != null) throw new Error("Two instances of UNDEFINED value not supported."); }
    
    @Override
    public TSDataType getTSDataType() { return TSDataType.UNDEFINED; }

    @Override
    public final int toInt() { return 0; }

    @Override
    public final long toLong() { return 0; }

    @Override
    public final float toFloat() { return 0; }

    @Override
    public final double toDouble() { return 0; }
    
    @Override
    public final boolean toBoolean() { return false; }
    
    @Override
    public final String toString() { return "undefined"; }

    @Override
    public TSValue[] toArray() { return new TSValue[0]; }

    @Override
    public Map<TSValue, TSValue> toMap() { return new HashMap<>(); }
    
    @Override
    public final TSValue not() { return TRUE; }

    @Override
    public final boolean equals(Object o) { return o == INSTANCE;  }

    @Override
    public final int hashCode() { return 0; }
    
}
