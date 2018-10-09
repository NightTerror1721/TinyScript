/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.utils;

import java.util.HashMap;
import java.util.Map;
import kp.ts.lang.TSObject;
import kp.ts.lang.TSObject.Property;
import kp.ts.lang.TSValue;

/**
 *
 * @author Asus
 */
public class ProtoObject extends HashMap<String, Property>
{
    public ProtoObject() { super(); }
    public ProtoObject(int initialCapacity) { super(initialCapacity); }
    public ProtoObject(int initialCapacity, float loadFactor) { super(initialCapacity, loadFactor); }

    public final void put(String name, TSValue value) { put(name, new Property(value)); }
    public final void put(String name, TSValue value, boolean isFrozen) { put(name, new Property(value, isFrozen)); }

    public final TSObject build(boolean frozen) { return new TSObject(this, frozen); }
    
    public static final TSObject createFrom(Map<String, TSValue> map, boolean isFrozen)
    {
        ProtoObject obj = new ProtoObject();
        for(Map.Entry<String, TSValue> e : map.entrySet())
            obj.put(e.getKey(), e.getValue());
        return obj.build(isFrozen);
    }
}
