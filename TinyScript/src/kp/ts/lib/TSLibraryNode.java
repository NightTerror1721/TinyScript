/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.lib;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import kp.ts.TSGlobals;
import kp.ts.lang.TSValue;
import kp.ts.lang.TSVarargs;

/**
 *
 * @author Asus
 */
public class TSLibraryNode extends TSLibraryElement implements TSLibrary
{
    protected final Map<String, TSLibraryElement> elements;
    
    public TSLibraryNode(String name, Map<String, TSLibraryElement> elements)
    {
        super(name);
        this.elements = Objects.requireNonNull(elements);
    }
    public TSLibraryNode(String name) { this(name, new HashMap<>()); }
    
    @Override
    public final TSValue toTSValue() { throw new UnsupportedOperationException(); }

    @Override
    public TSLibraryElement getProperty(String name) { return elements.get(name); }

    @Override
    public final TSValue call(TSGlobals globals, TSLibraryElement self) { throw new UnsupportedOperationException(); }

    @Override
    public final  TSValue call(TSGlobals globals, TSLibraryElement self, TSValue arg0) { throw new UnsupportedOperationException(); }

    @Override
    public final  TSValue call(TSGlobals globals, TSLibraryElement self, TSValue arg0, TSValue arg1) { throw new UnsupportedOperationException(); }

    @Override
    public final  TSValue call(TSGlobals globals, TSLibraryElement self, TSValue arg0, TSValue arg1, TSValue arg2) { throw new UnsupportedOperationException(); }

    @Override
    public final  TSValue call(TSGlobals globals, TSLibraryElement self, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3) { throw new UnsupportedOperationException(); }

    @Override
    public final  TSValue call(TSGlobals globals, TSLibraryElement self, TSVarargs args) { throw new UnsupportedOperationException(); }

    @Override
    public final  TSValue createNewInstance(TSGlobals globals) { throw new UnsupportedOperationException(); }

    @Override
    public final String getLibraryName() { return name; }

    @Override
    public TSLibraryElement getLibraryElement(String name) { return elements.get(name); }

    @Override
    public boolean hasLibraryElement(String name) { return elements.containsKey(name); }
    
}
