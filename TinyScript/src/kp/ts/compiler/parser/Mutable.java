/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.compiler.parser;

import java.util.Objects;
import kp.ts.compiler.exception.CompilerError;
import kp.ts.lang.TSValue;
import kp.ts.utils.ProtoObject;

/**
 *
 * @author Asus
 */
public final class Mutable extends Statement
{
    private final MutableEntry[] entries;
    private final boolean isArray;
    private final boolean isLiteral;
    
    private Mutable(MutableEntry[] entries, boolean isArray, boolean isLiteral)
    {
        this.entries = Objects.requireNonNull(entries);
        this.isArray = isArray;
        this.isLiteral = isLiteral;
    }
    
    public final boolean isArray() { return isArray; }
    public final boolean isObject() { return !isArray; }
    
    public final int getEntryCount() { return entries.length; }
    public final MutableEntry getEntry(int index) { return entries[index]; }
    
    @Override
    public final CodeFragmentType getFragmentType() { return CodeFragmentType.MUTABLE; }
    
    public final boolean isMutableLiteral() { return isLiteral; }
    
    public final Literal generateLiteral()
    {
        if(!isLiteral)
            throw new IllegalStateException();
        if(isArray)
        {
            TSValue[] array = new TSValue[entries.length];
            for(int i=0;i<array.length;i++)
                array[i] = ((Literal) entries[i].value).getTSValue();
            return Literal.valueOf(array);
        }
        else
        {
            ProtoObject obj = new ProtoObject();
            for(MutableEntry e : entries)
                obj.put(e.key, ((Literal) e.value).getTSValue());
            return Literal.valueOf(obj);
        }
    }
    
    
    
    public static final Mutable array(CodeFragmentList frags) throws CompilerError
    {
        CodeFragmentList[] parts = frags.split(Stopchar.COMMA);
        if(parts.length == 1 && parts[0].isEmpty())
            return new Mutable(new MutableEntry[0], true, true);
        
        boolean literal = true;
        MutableEntry[] entries = new MutableEntry[parts.length];
        for(int i=0;i<parts.length;i++)
        {
            entries[i] = new MutableEntry(null, parts[i]);
            if(literal && !entries[i].isLiteral())
                literal = false;
        }
        
        return new Mutable(entries, true, literal);
    }
    
    public static final Mutable array(CodeFragmentList[] frags) throws CompilerError
    {
        boolean literal = true;
        MutableEntry[] entries = new MutableEntry[frags.length];
        for(int i=0;i<frags.length;i++)
        {
            entries[i] = new MutableEntry(null, frags[i]);
            if(literal && !entries[i].isLiteral())
                literal = false;
        }
        
        return new Mutable(entries, true, literal);
    }
    
    public static final Mutable object(CodeFragmentList frags) throws CompilerError
    {
        CodeFragmentList[] parts = frags.split(Stopchar.COMMA);
        if(parts.length == 1 && parts[0].isEmpty())
            return new Mutable(new MutableEntry[0], false, true);
        
        boolean literal = true;
        MutableEntry[] entries = new MutableEntry[parts.length];
        for(int i=0;i<parts.length;i++)
        {
            CodeFragmentList[] part = parts[i].split(Stopchar.TWO_POINTS);
            if(part.length != 2)
                throw new CompilerError("Malformed object literal.");
            entries[i] = new MutableEntry(part[0], part[1]);
            if(literal && !entries[i].isLiteral())
                literal = false;
        }
        
        return new Mutable(entries, false, literal);
    }

    @Override
    public final String toString()
    {
        StringBuilder sb = new StringBuilder();
        if(isArray)
        {
            if(entries.length > 0)
            {
                sb.append("[");
                for(MutableEntry e : entries)
                    sb.append(e.value).append(", ");
                sb.delete(sb.length() - 2, sb.length()).append("]");
            }
            else sb.append("[]");
        }
        else
        {
             if(entries.length > 0)
            {
                sb.append("{");
                for(MutableEntry e : entries)
                    sb.append('\t').append(e.key).append(": ").append(e.value).append('\n');
                sb.append("}");
            }
            else sb.append("{}");
        }
        return sb.toString();
    }
    
    
    
    
    public static final class MutableEntry
    {
        private final String key;
        private final Statement value;
        
        private MutableEntry(CodeFragmentList key, CodeFragmentList value) throws CompilerError
        {
            if(key == null)
                this.key = null;
            else
            {
                Statement s = StatementParser.parse(key);
                switch(s.getFragmentType())
                {
                    case IDENTIFIER: this.key = s.toString(); break;
                    case LITERAL:
                        if(((Literal) s).getLiteralType() == Literal.LiteralType.STRING)
                        {
                            this.key = ((Literal) s).getTSValue().toString();
                            break;
                        }
                    default: throw new CompilerError("Expected valid identifier or string literal in object property name. But found: " + s);
                }
            }
            this.value = StatementParser.parse(value);
        }
        
        public final String getKey() { return key; }
        public final Statement getValue() { return value; }
        
        public final boolean isLiteral() { return value.isLiteral(); }
    }
}
