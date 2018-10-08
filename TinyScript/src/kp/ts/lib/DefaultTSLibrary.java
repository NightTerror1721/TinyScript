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
public class DefaultTSLibrary extends AbstractTSLibrary
{
    public DefaultTSLibrary(String name)
    {
        super(name);
    }
    
    public final DefaultTSLibrary putLibraryElement(TSLibraryElement e)
    {
        TSLibrary.linkElementToLibrary(e, this);
        elements.put(e.name, e);
        return this;
    }
    
    public final TSLibraryElement removeLibraryElement(String name)
    {
        TSLibraryElement e = elements.remove(name);
        if(e != null)
            TSLibrary.unlinkElementToLibrary(e);
        return e;
    }
}
