/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.lang;

import java.util.LinkedList;
import kp.ts.lang.core.TSCustomIterator;
import kp.ts.utils.Def;

/**
 *
 * @author Asus
 */
public abstract class TSIterator extends TSValue
{
    @Override
    public final TSDataType getTSDataType() { return TSDataType.ITERATOR; }

    @Override
    public final int toInt() { return toBoolean() ? 1 : 0; }

    @Override
    public final long toLong() { return toBoolean() ? 1 : 0; }

    @Override
    public final float toFloat() { return toBoolean() ? 1 : 0; }

    @Override
    public final double toDouble() { return toBoolean() ? 1 : 0; }
    
    @Override
    public final boolean toBoolean() { return hasNext(); }

    @Override
    public final boolean equals(Object o) { return this == o; }

    @Override
    public final int hashCode() { return superHashCode(); }
    
    @Override
    public final TSIterator castToIterator() { return this; }
    
    @Override
    public final TSValue increase() { return next(); }
    
    @Override
    public final TSValue not() { return hasNext() ? FALSE : TRUE; }
    
    @Override
    public final TSValue getPointerValue() { return super.getPointerValue(); }
    @Override
    public final TSValue setPointerValue(TSValue value) { return super.setPointerValue(value); }
    
    @Override
    public final TSValue getProperty(String name)
    {
        switch(name)
        {
            default: return UNDEFINED;
            case "hasNext": return HAS_NEXT;
            case "next": return NEXT;
            case "forEach": return FOR_EACH;
            case "pack": return PACK;
        }
    }
    
    @Override
    public final TSValue call(TSValue self, TSValue arg0) { return arg0.iterator(); }
    
    @Override public final TSIterator iterator() { return this; }
    @Override public abstract boolean hasNext();
    @Override public abstract TSValue next();
    
    @Override
    public final TSValue createNewInstance(TSValue arg0, TSValue arg1) { return new TSCustomIterator(arg0, arg1); }
    
    
    private static final TSValue HAS_NEXT = Def.method((self) -> self.hasNext() ? TRUE : FALSE);
    private static final TSValue NEXT = Def.method((self) -> self.next());
    private static final TSValue FOR_EACH = Def.vmethod((self, consumer) -> {
        while(self.hasNext())
            consumer.call(UNDEFINED, self.next());
    });
    private static final TSValue PACK = Def.method((self) -> {
        LinkedList<TSValue> list = new LinkedList<>();
        while(self.hasNext())
            list.add(self.next());
        return new TSArray(list.toArray(new TSValue[list.size()]));
    });
    
}
