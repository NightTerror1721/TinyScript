/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.lang;

/**
 *
 * @author Asus
 */
public enum TSDataType
{
    UNDEFINED,
    
    NUMBER,
    BOOLEAN,
    STRING,
    
    ARRAY,
    OBJECT,
    
    FUNCTION,
    ITERATOR,
    
    NATIVE;
    
    
    private final String typename = name().toLowerCase();
    
    public final String getTypeName() { return typename; }
}
