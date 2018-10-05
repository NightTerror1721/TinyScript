/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.lang;

import java.util.Objects;
import java.util.StringJoiner;
import kp.ts.utils.Def;

/**
 *
 * @author Asus
 */
public final class TSString extends TSValue
{
    private final String str;
    
    public TSString(String string) { this.str = string; }
    
    @Override
    public final TSDataType getTSDataType() { return TSDataType.STRING; }

    @Override
    public final int toInt() { return Integer.parseInt(str); }

    @Override
    public final long toLong() { return Long.parseLong(str); }

    @Override
    public final float toFloat() { return Float.parseFloat(str); }

    @Override
    public final double toDouble() { return Double.parseDouble(str); }
    
    @Override
    public final boolean toBoolean() { return !str.isEmpty(); }
    
    @Override
    public final String toString() { return str; }
    
    @Override
    public final TSString castToString() { return this; }

    @Override
    public final boolean equals(Object o) { return o instanceof TSValue && str.equals(o.toString()); }

    @Override
    public final int hashCode()
    {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.str);
        return hash;
    }
    
    
    @Override
    public final TSValue equals(TSValue value) { return str.equals(value.toString()) ? TRUE : FALSE; }
    @Override
    public final TSValue notEquals(TSValue value) { return str.equals(value.toString()) ? FALSE : TRUE; }
    @Override
    public final TSValue greaterThan(TSValue value) { return str.compareTo(value.toString()) > 0 ? TRUE : FALSE; }
    @Override
    public final TSValue smallerThan(TSValue value) { return str.compareTo(value.toString()) < 0 ? TRUE : FALSE; }
    @Override
    public final TSValue greaterOrEqualsThan(TSValue value) { return str.compareTo(value.toString()) >= 0 ? TRUE : FALSE; }
    @Override
    public final TSValue smallerOrEqualsThan(TSValue value) { return str.compareTo(value.toString()) <= 0 ? TRUE : FALSE; }
    @Override
    public final TSValue not() { return str.isEmpty() ? TRUE : FALSE; }
    @Override
    public final TSValue length() { return new TSNumber.Int32(str.length()); }
    
    @Override
    public final TSValue get(TSValue index) { return new TSNumber.Char(str.charAt(index.toInt())); }
    @Override
    public final TSValue get(int index) { return new TSNumber.Char(str.charAt(index)); }
    
    
    @Override
    public final TSValue getProperty(String name)
    {
        switch(name)
        {
            default: return UNDEFINED;
            case "concat": return CONCAT;
            case "endsWith": return ENDS_WITH;
            case "contains": return CONTAINS;
            case "indexOf": return INDEX_OF;
            case "lastIndexOf": return LAST_INDEX_OF;
            case "repeat": return REPEAT;
            case "substring": return SUBSTRING;
            case "startsWith": return STARTS_WITH;
            case "toLowerCase": return TO_LOWER_CASE;
            case "toUpperCase": return TO_UPPER_CASE;
            case "trim": return TRIM;
        }
    }
    
    @Override
    public final TSValue call(TSValue self) { return new TSString(""); }
    @Override
    public final TSValue call(TSValue self, TSValue arg0) { return new TSString(arg0.toString()); }
    
    @Override
    public final TSValue createNewInstance() { return new TSString(""); }
    @Override
    public final TSValue createNewInstance(TSValue arg0) { return new TSString(arg0.toString()); }
    
    
    private static final TSValue CONCAT = Def.method((self, args) -> {
        switch(args.nargs())
        {
            case 0:
                return self;
            case 1:
                return new TSString(self.toString() + args.arg0().toString());
            default: {
                StringJoiner joiner = new StringJoiner(args.arg0().toString());
                int len = args.nargs();
                for(int i=1;i<len;i++)
                    joiner.add(args.arg(i).toString());
                return new TSString(joiner.toString());
            }
        }
    });
    private static final TSValue ENDS_WITH = Def.method((self, arg0) -> self.toString().endsWith(arg0.toString()) ? TRUE : FALSE);
    private static final TSValue CONTAINS = Def.method((self, arg0) -> self.toString().contains(arg0.toString()) ? TRUE : FALSE);
    private static final TSValue INDEX_OF = Def.method((self, arg0) -> {
        if(self.isNumber())
            return new TSNumber.Int32(self.toString().indexOf(arg0.toInt()));
        return new TSNumber.Int32(self.toString().indexOf(arg0.toString()));
    });
    private static final TSValue LAST_INDEX_OF = Def.method((self, arg0) -> {
        if(self.isNumber())
            return new TSNumber.Int32(self.toString().lastIndexOf(arg0.toInt()));
        return new TSNumber.Int32(self.toString().lastIndexOf(arg0.toString()));
    });
    private static final TSValue REPEAT = Def.method((self, arg0) -> {
        int times = arg0.toInt();
        char[] base = self.toString().toCharArray();
        char[] res = new char[base.length * times];
        for(int i=0;i<times;i++)
            System.arraycopy(base,0,res,i * base.length,base.length);
        return new TSString(new String(res));
    });
    private static final TSValue SUBSTRING = Def.method((self, arg0, arg1) -> {
        if(arg1 == UNDEFINED)
            return new TSString(self.toString().substring(arg0.toInt()));
        return new TSString(self.toString().substring(arg0.toInt(), arg1.toInt()));
    });
    private static final TSValue STARTS_WITH = Def.method((self, arg0) -> self.toString().startsWith(arg0.toString()) ? TRUE : FALSE);
    private static final TSValue TO_LOWER_CASE = Def.method((self) -> new TSString(self.toString().toLowerCase()));
    private static final TSValue TO_UPPER_CASE = Def.method((self) -> new TSString(self.toString().toUpperCase()));
    private static final TSValue TRIM = Def.method((self) -> new TSString(self.toString().trim()));
}
