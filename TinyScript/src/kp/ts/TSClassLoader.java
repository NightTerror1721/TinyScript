/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import kp.ts.lang.core.TSScript;

/**
 *
 * @author Asus
 */
public class TSClassLoader extends ClassLoader
{
    private final HashMap<String, ScriptCache> cache = new HashMap<>();
    private final TSClassLoader tsParent;
    
    public TSClassLoader(ClassLoader parent)
    {
        super(parent);
        this.tsParent = parent instanceof TSClassLoader ? (TSClassLoader) parent : null;
    }
    public TSClassLoader()
    {
        super();
        this.tsParent = null;
    }
    
    public final Class<? extends TSScript> findScriptClass(String scriptName) throws ClassNotFoundException
    {
        ScriptCache script = cache.get(scriptName);
        return script != null ? script.script : tsParent != null ? tsParent.findScriptClass(scriptName) : null;
    }
    
    
    /*private static File resolveFile(File root, File file)
    {
        file = FileSystems.getDefault().getPath(root.getPath(), file.getPath()).toFile();
        return file.exists() && file.isFile() ? file : null;
    }

    public final File getFile(String path)
    {
        File file = new File(path);
        if(file.isAbsolute())
        {
            if(file.exists() && file.isFile())
                return file;
            return null;
        }
        for(File root : roots)
        {
            File f = resolveFile(root, file);
            if(f != null)
                return f;
        }
        return null;
    }*/
    
    
    public final Class<? extends TSScript> createScript(String scriptName, byte[] data, int offset, int length)
    {
        if(cache.containsKey(scriptName))
            return updateScript0(scriptName, data, offset, length);
        return createScript0(scriptName, data, offset, length);
    }
    
    public final Class<? extends TSScript> updateScript(String scriptName, byte[] data, int offset, int length)
    {
        if(!cache.containsKey(scriptName))
            return createScript0(scriptName, data, offset, length);
        return updateScript0(scriptName, data, offset, length);
    }
    
    private Class<? extends TSScript> createScript0(String scriptName, byte[] data, int offset, int length)
    {
        ScriptCache sc;
        cache.put(scriptName, sc = new ScriptCache(scriptName));
        sc.create(data, offset, length);
        return sc.script;
    }
    
    private Class<? extends TSScript> updateScript0(String scriptName, byte[] data, int offset, int length)
    {
        ScriptCache sc = cache.get(scriptName);
        sc.update(data, offset, length);
        return sc.script;
    }
    
    public final void writeScript(String scriptName, File targetFile) throws IOException
    {
        ScriptCache sc = cache.get(scriptName);
        if(sc == null)
            throw new IllegalArgumentException("Script " + scriptName + " not found.");
        try(FileOutputStream fos = new FileOutputStream(targetFile))
        {
            fos.write(sc.bytecode);
        }
    }
    
    
    private final class ScriptCache
    {
        private final String name;
        private Class<? extends TSScript> script;
        private byte[] bytecode;
        private int counter;
        
        private ScriptCache(String name)
        {
            this.name = name;
        }
        
        private void create(byte[] data, int offset, int length)
        {
            script = (Class<? extends TSScript>) TSClassLoader.super.defineClass(name, data, offset, length);
            bytecode = data;
            counter = 0;
            
        }
        
        private void update(byte[] data, int offset, int length)
        {
            script = (Class<? extends TSScript>) TSClassLoader.super.defineClass(name + (++counter), data, offset, length);
            bytecode = data;
        }
    }
}
