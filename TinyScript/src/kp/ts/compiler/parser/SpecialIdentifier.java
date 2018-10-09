/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.compiler.parser;

/**
 *
 * @author Asus
 */
public final class SpecialIdentifier extends Statement
{
    private final String name;
    
    private SpecialIdentifier(String name) { this.name = name; }
    
    @Override
    public CodeFragmentType getFragmentType() { return CodeFragmentType.SPECIAL_IDENTIFIER; }

    @Override
    public final String toString() { return name; }
    
    
    public static final SpecialIdentifier
            SELF = new SpecialIdentifier("self"),
            BASE = new SpecialIdentifier("base"),
            NUMBER = new SpecialIdentifier("number"),
            BOOLEAN = new SpecialIdentifier("boolean"),
            STRING = new SpecialIdentifier("string"),
            ARRAY = new SpecialIdentifier("array"),
            OBJECT = new SpecialIdentifier("object"),
            ITERATOR = new SpecialIdentifier("iterator");
}
