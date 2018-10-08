/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.lib;

/**
 *
 * @author Asus
 */
public interface TSLibrary
{
    String getLibraryName();
    TSLibraryElement getLibraryElement(String name);
    boolean hasLibraryElement(String name);
    
    static void linkElementToLibrary(TSLibraryElement e, TSLibrary lib)
    {
        if(e.lib != null)
            throw new IllegalArgumentException("Element is already linked with other library.");
        e.lib = lib;
    }
    
    static void unlinkElementToLibrary(TSLibraryElement e)
    {
        if(e.lib == null)
            throw new IllegalArgumentException("Element not linked with any library.");
        e.lib = null;
    }
}
