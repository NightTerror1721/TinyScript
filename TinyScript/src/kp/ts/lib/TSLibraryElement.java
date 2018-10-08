/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.lib;

import kp.ts.TSGlobals;
import kp.ts.lang.TSValue;
import kp.ts.lang.TSVarargs;

/**
 *
 * @author Asus
 */
public abstract class TSLibraryElement
{
    final String name;
    TSLibrary lib;
    
    public TSLibraryElement(String name)
    {
        if(name == null)
            throw new NullPointerException();
        if(name.isEmpty())
            throw new IllegalArgumentException("Library name cannot be empty");
        this.name = name;
    }
    
    public final TSLibrary getLibrary() { return lib; }
    public final String getElementName() { return name; }
    public final boolean isLibrary() { return this instanceof TSLibrary; }
    
    public abstract TSValue toTSValue();
    
    public abstract TSLibraryElement getProperty(String name);
    
    public abstract TSValue call(TSGlobals globals, TSLibraryElement self);
    public abstract TSValue call(TSGlobals globals, TSLibraryElement self, TSValue arg0);
    public abstract TSValue call(TSGlobals globals, TSLibraryElement self, TSValue arg0, TSValue arg1);
    public abstract TSValue call(TSGlobals globals, TSLibraryElement self, TSValue arg0, TSValue arg1, TSValue arg2);
    public abstract TSValue call(TSGlobals globals, TSLibraryElement self, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3);
    public abstract TSValue call(TSGlobals globals, TSLibraryElement self, TSVarargs args);
    
    public final TSValue staticCall(TSGlobals globals) { return call(globals, null); }
    public final  TSValue staticCall(TSGlobals globals, TSValue arg0) { return call(globals, null, arg0); }
    public final  TSValue staticCall(TSGlobals globals, TSValue arg0, TSValue arg1) { return call(globals, null, arg0, arg1); }
    public final  TSValue staticCall(TSGlobals globals, TSValue arg0, TSValue arg1, TSValue arg2) { return call(globals, null, arg0, arg1, arg2); }
    public final  TSValue staticCall(TSGlobals globals, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3) { return call(globals, null, arg0, arg1, arg2, arg3); }
    public final  TSValue staticCall(TSGlobals globals, TSVarargs args) { return call(globals, null, args); }
    
    public final  TSValue invoke(TSGlobals globals, String property) { return getProperty(property).call(globals, this); }
    public final  TSValue invoke(TSGlobals globals, String property, TSValue arg0) { return getProperty(property).call(globals, this, arg0); }
    public final  TSValue invoke(TSGlobals globals, String property, TSValue arg0, TSValue arg1) { return getProperty(property).call(globals, this, arg0, arg1); }
    public final  TSValue invoke(TSGlobals globals, String property, TSValue arg0, TSValue arg1, TSValue arg2) { return getProperty(property).call(globals, this, arg0, arg1, arg2); }
    public final  TSValue invoke(TSGlobals globals, String property, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3) { return getProperty(property).call(globals, this, arg0, arg1, arg2, arg3); }
    public final  TSValue invoke(TSGlobals globals, String property, TSVarargs args) { return getProperty(property).call(globals, this, args); }
    
    public abstract TSValue createNewInstance(TSGlobals globals);
    public TSValue createNewInstance(TSGlobals globals, TSValue arg0) { return createNewInstance(globals); }
    public TSValue createNewInstance(TSGlobals globals, TSValue arg0, TSValue arg1) { return createNewInstance(globals, arg0); }
    public TSValue createNewInstance(TSGlobals globals, TSValue arg0, TSValue arg1, TSValue arg2) { return createNewInstance(globals, arg0, arg1); }
    public TSValue createNewInstance(TSGlobals globals, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3) { return createNewInstance(globals, arg0, arg1, arg2); }
    public TSValue createNewInstance(TSGlobals globals, TSVarargs args) { return createNewInstance(globals, args.arg0(), args.arg(1), args.arg(2), args.arg(3)); }
}
