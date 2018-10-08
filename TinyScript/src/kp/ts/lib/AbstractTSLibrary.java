/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.lib;

import java.util.HashMap;

/**
 *
 * @author Asus
 */
public class AbstractTSLibrary implements TSLibrary
{
    protected final String name;
    protected final HashMap<String, TSLibraryElement> elements = new HashMap<>();
    
    public AbstractTSLibrary(String name)
    {
        if(name == null)
            throw new NullPointerException();
        if(name.isEmpty())
            throw new IllegalArgumentException("Library name cannot be empty");
        this.name = name;
    }
    
    @Override
    public String getLibraryName() { return name; }

    @Override
    public TSLibraryElement getLibraryElement(String name)
    {
        return elements.getOrDefault(name, null);
    }

    @Override
    public boolean hasLibraryElement(String name) { return elements.containsKey(name); }
}
