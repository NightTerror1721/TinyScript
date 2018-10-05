/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.lang;

import kp.ts.utils.Def;

/**
 *
 * @author Asus
 */
public abstract class TSFunction extends TSValue
{

    @Override
    public final TSDataType getTSDataType() { return TSDataType.FUNCTION; }

    @Override
    public final int toInt() { return 1; }

    @Override
    public final long toLong() { return 1; }

    @Override
    public final float toFloat() { return 1; }

    @Override
    public final double toDouble() { return 1; }

    @Override
    public final boolean equals(Object o)
    {
        return o == this;
    }

    @Override
    public final int hashCode() { return superHashCode(); }
    
    @Override
    public final TSFunction castToFunction() { return this; }
    
    @Override public abstract TSValue call(TSValue self);
    @Override public abstract TSValue call(TSValue self, TSValue arg0);
    @Override public abstract TSValue call(TSValue self, TSValue arg0, TSValue arg1);
    @Override public abstract TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2);
    @Override public abstract TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3);
    @Override public abstract TSValue call(TSValue self, TSVarargs args);
    
    @Override
    public final TSValue getProperty(String name)
    {
        switch(name)
        {
            default: return UNDEFINED;
            case "call": return CALL;
            case "apply": return APPLY;
        }
    }
    
    private static final TSValue CALL = Def.vamethod((self, args) -> self.call(self, subVarargs(args, 1)));
    private static final TSValue APPLY = Def.method((self, arg0, arg1) -> self.call(arg0, varargsOf(arg1.toArray())));
}
