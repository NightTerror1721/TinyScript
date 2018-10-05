/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.lang.core;

import kp.ts.lang.TSIterator;
import kp.ts.lang.TSValue;

/**
 *
 * @author Asus
 */
public class TSCustomIterator extends TSIterator
{
    protected final TSValue hasNextClosure;
    protected final TSValue nextClosure;
    
    public TSCustomIterator(TSValue hasNextClosure, TSValue nextClosure)
    {
        if(hasNextClosure == null)
            throw new NullPointerException();
        if(nextClosure == null)
            throw new NullPointerException();
        this.hasNextClosure = hasNextClosure;
        this.nextClosure = nextClosure;
    }
    
    @Override
    public final boolean hasNext() { return hasNextClosure.call(UNDEFINED).toBoolean(); }

    @Override
    public final TSValue next() { return nextClosure.call(UNDEFINED); }
    
}
