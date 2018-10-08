/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts;

import kp.ts.lang.TSValue;

/**
 *
 * @author Asus
 */
public abstract class TSGlobals
{
    private final TSGlobals parent;
    
    protected TSGlobals(TSGlobals parent) { this.parent = parent; }
    
    public final TSValue getGlobalValue(String name)
    {
        TSValue value;
        return (value = innerGetGlobalValue(name)) == null
                ? parent == null ? TSValue.UNDEFINED : parent.getGlobalValue(name)
                : value;
    }
    public final TSValue setGlobalValue(String name, TSValue value)
    {
        if(value == TSValue.UNDEFINED || value == null)
            return removeGlobalValue(name);
        return innerSetGlobalValue(name, value);
    }
    
    public final TSClassLoader getClassLoader() { return getClassLoader0(); }
    TSClassLoader getClassLoader0() { return parent == null ? new TSClassLoader(getClass().getClassLoader()) : parent.getClassLoader0(); }
    
    public abstract boolean hasGlobalValue();
    protected abstract TSValue innerGetGlobalValue(String name);
    protected abstract TSValue innerSetGlobalValue(String name, TSValue value);
    public abstract TSValue removeGlobalValue(String name);
}
