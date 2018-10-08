/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.lib;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Asus
 */
public final class TSLibraryRepository implements Iterable<TSLibrary>
{
    private final HashMap<String, TSLibrary> libs = new HashMap<>();
    
    public final void registerLibrary(TSLibrary lib)
    {
        if(libs.containsKey(lib.getLibraryName()))
            throw new IllegalArgumentException("Library " + lib.getLibraryName() + " has already exists");
        libs.put(lib.getLibraryName(), lib);
    }
    
    public final TSLibrary getLibrary(String name)
    {
        return libs.getOrDefault(name, null);
    }
    
    public final boolean hasLibrary(String name) { return libs.containsKey(name); }
    
    public final TSLibrary removeLibrary(String name)
    {
        return libs.remove(name);
    }

    @Override
    public final Iterator<TSLibrary> iterator() { return libs.values().iterator(); }
}
