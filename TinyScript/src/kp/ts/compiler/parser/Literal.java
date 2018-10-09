/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.compiler.parser;

import java.util.Map;
import java.util.regex.Pattern;
import kp.ts.compiler.exception.CompilerError;
import kp.ts.compiler.utils.ValueType;
import kp.ts.lang.TSArray;
import kp.ts.lang.TSNumber;
import kp.ts.lang.TSString;
import kp.ts.lang.TSValue;
import kp.ts.utils.ProtoObject;

/**
 *
 * @author Asus
 */
public class Literal extends Statement
{
    public static final Literal UNDEFINED = new Literal(TSValue.UNDEFINED);
    public static final Literal ZERO = new Literal(TSValue.ZERO);
    public static final Literal ONE = new Literal(TSValue.ONE);
    public static final Literal MINUSONE = new Literal(TSValue.MINUSONE);
    public static final Literal TRUE = new Literal(TSValue.TRUE);
    public static final Literal FALSE = new Literal(TSValue.FALSE);
    
    private final TSValue value;
    private final Primitive primitive;
    private final LiteralType ltype;
    
    private Literal(TSValue value)
    {
        this.value = value;
        switch(value.getTSDataType())
        {
            case UNDEFINED:
                primitive = null;
                ltype = LiteralType.UNDEFINED;
                break;
            case NUMBER:
                switch(value.castToNumber().getNumberType())
                {
                    default:
                    case INT:
                        primitive = new LiteralInt(value.toInt());
                        ltype = LiteralType.INT32;
                        break;
                    case LONG:
                        primitive = new LiteralLong(value.toLong());
                        ltype = LiteralType.INT64;
                        break;
                    case FLOAT:
                        primitive = new LiteralDouble(value.toDouble());
                        ltype = LiteralType.FLOAT32;
                        break;
                    case DOUBLE:
                        primitive = new LiteralDouble(value.toDouble());
                        ltype = LiteralType.FLOAT64;
                        break;
                }
                break;
            case BOOLEAN:
                primitive = null;
                ltype = LiteralType.BOOL;
                break;
            case STRING:
                primitive = null;
                ltype = LiteralType.STRING;
                break;
            case ARRAY:
                primitive = null;
                ltype = LiteralType.ARRAY;
                break;
            case OBJECT:
                primitive = null;
                ltype = LiteralType.OBJECT;
                break;
            default:
                primitive = null;
                ltype = LiteralType.UNKNOWN;
                break;
        }
    }
    
    public final boolean hasPrimitive() { return primitive != null; }
    public final Primitive getPrimitive() { return primitive; }
    
    public final Literal fixDecimals()
    {
        if(value.isFloat())
        {
            double num = value.toDouble();
            if(num == ((double)((int) num)))
                return valueOf((int) num);
        }
        return this;
    }
    
    public final TSValue getTSValue() { return value; }
    
    public final Literal operatorUnaryPlus() { return this; }
    public final Literal operatorUnaryMinus()
    {
        switch(ltype)
        {
            case INT32: return valueOf(-value.toInt());
            case INT64: return valueOf(-value.toLong());
            case FLOAT32: return valueOf(-value.toFloat());
            case FLOAT64: return valueOf(-value.toDouble());
        }
        return this;
    }
    public final Literal operatorNot() { return valueOf(value.not()); }
    public final Literal operatorBitwiseNot()
    {
        switch(ltype)
        {
            case INT32: return valueOf(~value.toInt());
            case INT64:
            case FLOAT32:
            case FLOAT64: return valueOf(~value.toLong());
        }
        return this;
    }
    public final Literal operatorCastInt() { return valueOf(value.toInt()); }
    public final Literal operatorCastFloat() { return valueOf(value.toDouble()); }
    public final Literal operatorCastString() { return valueOf(value.toString()); }
    public final Literal operatorCastArray() { return valueOf(value.toArray()); }
    public final Literal operatorCastObject() { return valueOf(value.toObject()); }
    public final Literal operatorLength() { return valueOf(value.length()); }
    public final Literal operatorIsdef() { return valueOf(!value.isUndefined()); }
    public final Literal operatorIsundef() { return valueOf(value.isUndefined()); }
    public final Literal operatorTypeid() { return valueOf(value.getTSDataType().getTypeName()); }
    public final Literal operatorMultiply(Literal other)
    {
        switch(ltype)
        {
            case INT32: switch(other.ltype)
            {
                case INT32: return valueOf(value.toInt() * other.value.toInt());
                case INT64: return valueOf(value.toLong() * other.value.toLong());
                case FLOAT32: return valueOf(value.toFloat() * other.value.toFloat());
                case FLOAT64: return valueOf(value.toDouble() * other.value.toDouble());
            }
            case INT64: switch(other.ltype)
            {
                case INT32:
                case INT64: return valueOf(value.toLong() * other.value.toLong());
                case FLOAT32: return valueOf(value.toFloat() * other.value.toFloat());
                case FLOAT64: return valueOf(value.toDouble() * other.value.toDouble());
            }
            case FLOAT32: switch(other.ltype)
            {
                case INT32:
                case INT64:
                case FLOAT32: return valueOf(value.toFloat() * other.value.toFloat());
                case FLOAT64: return valueOf(value.toDouble() * other.value.toDouble());
            }
            case FLOAT64: switch(other.ltype)
            {
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toDouble() * other.value.toDouble());
            }
        }
        return valueOf(value.multiply(other.value));
    }
    public final Literal operatorDivision(Literal other)
    {
        switch(ltype)
        {
            case INT32: switch(other.ltype)
            {
                case INT32: return valueOf(value.toInt() / other.value.toInt());
                case INT64: return valueOf(value.toLong() / other.value.toLong());
                case FLOAT32: return valueOf(value.toFloat() / other.value.toFloat());
                case FLOAT64: return valueOf(value.toDouble() / other.value.toDouble());
            }
            case INT64: switch(other.ltype)
            {
                case INT32:
                case INT64: return valueOf(value.toLong() / other.value.toLong());
                case FLOAT32: return valueOf(value.toFloat() / other.value.toFloat());
                case FLOAT64: return valueOf(value.toDouble() / other.value.toDouble());
            }
            case FLOAT32: switch(other.ltype)
            {
                case INT32:
                case INT64:
                case FLOAT32: return valueOf(value.toFloat() / other.value.toFloat());
                case FLOAT64: return valueOf(value.toDouble() / other.value.toDouble());
            }
            case FLOAT64: switch(other.ltype)
            {
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toDouble() / other.value.toDouble());
            }
        }
        return valueOf(value.divide(other.value));
    }
    public final Literal operatorRemainder(Literal other)
    {
        switch(ltype)
        {
            case INT32: switch(other.ltype)
            {
                case INT32: return valueOf(value.toInt() % other.value.toInt());
                case INT64: return valueOf(value.toLong() % other.value.toLong());
                case FLOAT32: return valueOf(value.toFloat() % other.value.toFloat());
                case FLOAT64: return valueOf(value.toDouble() % other.value.toDouble());
            }
            case INT64: switch(other.ltype)
            {
                case INT32:
                case INT64: return valueOf(value.toLong() % other.value.toLong());
                case FLOAT32: return valueOf(value.toFloat() % other.value.toFloat());
                case FLOAT64: return valueOf(value.toDouble() % other.value.toDouble());
            }
            case FLOAT32: switch(other.ltype)
            {
                case INT32:
                case INT64:
                case FLOAT32: return valueOf(value.toFloat() % other.value.toFloat());
                case FLOAT64: return valueOf(value.toDouble() % other.value.toDouble());
            }
            case FLOAT64: switch(other.ltype)
            {
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toDouble() % other.value.toDouble());
            }
        }
        return valueOf(value.remainder(other.value));
    }
    public final Literal operatorPlus(Literal other)
    {
        switch(ltype)
        {
            case INT32: switch(other.ltype)
            {
                case INT32: return valueOf(value.toInt() + other.value.toInt());
                case INT64: return valueOf(value.toLong() + other.value.toLong());
                case FLOAT32: return valueOf(value.toFloat() + other.value.toFloat());
                case FLOAT64: return valueOf(value.toDouble() + other.value.toDouble());
            }
            case INT64: switch(other.ltype)
            {
                case INT32:
                case INT64: return valueOf(value.toLong() + other.value.toLong());
                case FLOAT32: return valueOf(value.toFloat() + other.value.toFloat());
                case FLOAT64: return valueOf(value.toDouble() + other.value.toDouble());
            }
            case FLOAT32: switch(other.ltype)
            {
                case INT32:
                case INT64:
                case FLOAT32: return valueOf(value.toFloat() + other.value.toFloat());
                case FLOAT64: return valueOf(value.toDouble() + other.value.toDouble());
            }
            case FLOAT64: switch(other.ltype)
            {
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toDouble() + other.value.toDouble());
            }
        }
        return valueOf(value.plus(other.value));
    }
    public final Literal operatorMinus(Literal other)
    {
        switch(ltype)
        {
            case INT32: switch(other.ltype)
            {
                case INT32: return valueOf(value.toInt() - other.value.toInt());
                case INT64: return valueOf(value.toLong() - other.value.toLong());
                case FLOAT32: return valueOf(value.toFloat() - other.value.toFloat());
                case FLOAT64: return valueOf(value.toDouble() - other.value.toDouble());
            }
            case INT64: switch(other.ltype)
            {
                case INT32:
                case INT64: return valueOf(value.toLong() - other.value.toLong());
                case FLOAT32: return valueOf(value.toFloat() - other.value.toFloat());
                case FLOAT64: return valueOf(value.toDouble() - other.value.toDouble());
            }
            case FLOAT32: switch(other.ltype)
            {
                case INT32:
                case INT64:
                case FLOAT32: return valueOf(value.toFloat() - other.value.toFloat());
                case FLOAT64: return valueOf(value.toDouble() - other.value.toDouble());
            }
            case FLOAT64: switch(other.ltype)
            {
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toDouble() - other.value.toDouble());
            }
        }
        return valueOf(value.minus(other.value));
    }
    public final Literal operatorBitwiseShiftLeft(Literal other)
    {
        switch(ltype)
        {
            case INT32: switch(other.ltype)
            {
                case INT32: return valueOf(value.toInt() << other.value.toInt());
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() << other.value.toLong());
            }
            case INT64: switch(other.ltype)
            {
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() << other.value.toLong());
            }
            case FLOAT32: switch(other.ltype)
            {
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() << other.value.toLong());
            }
            case FLOAT64: switch(other.ltype)
            {
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() << other.value.toLong());
            }
        }
        return valueOf(value.bitwiseShiftLeft(other.value));
    }
    public final Literal operatorBitwiseShiftRight(Literal other)
    {
        switch(ltype)
        {
            case INT32: switch(other.ltype)
            {
                case INT32: return valueOf(value.toInt() >> other.value.toInt());
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() >> other.value.toLong());
            }
            case INT64: switch(other.ltype)
            {
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() >> other.value.toLong());
            }
            case FLOAT32: switch(other.ltype)
            {
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() >> other.value.toLong());
            }
            case FLOAT64: switch(other.ltype)
            {
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() >> other.value.toLong());
            }
        }
        return valueOf(value.bitwiseShiftRight(other.value));
    }
    public final Literal operatorEquals(Literal other) { return valueOf(value.equals(other.value)); }
    public final Literal operatorNotEquals(Literal other) { return valueOf(value.notEquals(other.value)); }
    public final Literal operatorGreater(Literal other) { return valueOf(value.greaterThan(other.value)); }
    public final Literal operatorSmaller(Literal other) { return valueOf(value.smallerThan(other.value)); }
    public final Literal operatorGreaterEquals(Literal other) { return valueOf(value.greaterOrEqualsThan(other.value)); }
    public final Literal operatorSmallerEquals(Literal other) { return valueOf(value.smallerOrEqualsThan(other.value)); }
    public final Literal operatorTypedEquals(Literal other) { return valueOf(value.typedEquals(other.value)); }
    public final Literal operatorTypedNotEquals(Literal other) { return valueOf(value.typedNotEquals(other.value)); }
    public final Literal operatorBitwiseAnd(Literal other)
    {
        switch(ltype)
        {
            case INT32: switch(other.ltype)
            {
                case BOOL:
                case INT32: return valueOf(value.toInt() & other.value.toInt());
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() & other.value.toLong());
            }
            case INT64: switch(other.ltype)
            {
                case BOOL:
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() & other.value.toLong());
            }
            case FLOAT32: switch(other.ltype)
            {
                case BOOL:
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() & other.value.toLong());
            }
            case FLOAT64: switch(other.ltype)
            {
                case BOOL:
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() & other.value.toLong());
            }
            case BOOL: switch(other.ltype)
            {
                case BOOL:  return valueOf(value.toBoolean() & other.value.toBoolean());
                case INT32: return valueOf(value.toInt() & other.value.toInt());
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() & other.value.toLong());
            }
        }
        return valueOf(value.minus(other.value));
    }
    public final Literal operatorBitwiseXor(Literal other)
    {
        switch(ltype)
        {
            case INT32: switch(other.ltype)
            {
                case BOOL:
                case INT32: return valueOf(value.toInt() ^ other.value.toInt());
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() ^ other.value.toLong());
            }
            case INT64: switch(other.ltype)
            {
                case BOOL:
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() ^ other.value.toLong());
            }
            case FLOAT32: switch(other.ltype)
            {
                case BOOL:
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() ^ other.value.toLong());
            }
            case FLOAT64: switch(other.ltype)
            {
                case BOOL:
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() ^ other.value.toLong());
            }
            case BOOL: switch(other.ltype)
            {
                case BOOL:  return valueOf(value.toBoolean() ^ other.value.toBoolean());
                case INT32: return valueOf(value.toInt() ^ other.value.toInt());
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() ^ other.value.toLong());
            }
        }
        return valueOf(value.minus(other.value));
    }
    public final Literal operatorBitwiseOr(Literal other)
    {
        switch(ltype)
        {
            case INT32: switch(other.ltype)
            {
                case BOOL:
                case INT32: return valueOf(value.toInt() | other.value.toInt());
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() | other.value.toLong());
            }
            case INT64: switch(other.ltype)
            {
                case BOOL:
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() | other.value.toLong());
            }
            case FLOAT32: switch(other.ltype)
            {
                case BOOL:
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() | other.value.toLong());
            }
            case FLOAT64: switch(other.ltype)
            {
                case BOOL:
                case INT32:
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() | other.value.toLong());
            }
            case BOOL: switch(other.ltype)
            {
                case BOOL: return valueOf(value.toBoolean() | other.value.toBoolean());
                case INT32: return valueOf(value.toInt() ^ other.value.toInt());
                case INT64:
                case FLOAT32:
                case FLOAT64: return valueOf(value.toLong() | other.value.toLong());
            }
        }
        return valueOf(value.minus(other.value));
    }
    public final Literal operatorConcat(Literal other) { return valueOf(value.toString().concat(other.value.toString())); }
    
    public final LiteralType getLiteralType() { return ltype; }

    @Override
    public final CodeFragmentType getFragmentType() { return CodeFragmentType.LITERAL; }
    
    public static final Literal valueOf(int value) { return new Literal(new TSNumber.Int32(value)); }
    public static final Literal valueOf(long value) { return new Literal(new TSNumber.Int64(value)); }
    
    public static final Literal valueOf(float value) { return new Literal(new TSNumber.Float32(value)); }
    public static final Literal valueOf(double value) { return new Literal(new TSNumber.Float64(value)); }
    
    public static final Literal valueOf(boolean value) { return value ? TRUE : FALSE; }
    
    public static final Literal valueOf(char value) { return new Literal(new TSNumber.Char(value)); }
    public static final Literal valueOf(String value) { return new Literal(new TSString(value)); }
    
    public static final Literal valueOf(TSValue[] value)
    {
        TSArray array = new TSArray(value);
        array.setFrozen(true);
        return new Literal(array);
    }
    
    public static final Literal valueOf(Map<String, TSValue> value) { return new Literal(ProtoObject.createFrom(value, true)); }
    public static final Literal valueOf(ProtoObject value) { return new Literal(value.build(true)); }
    
    public static final Literal valueOf(TSValue value) { return new Literal(value); }
    
    
    private static final Pattern INTEGER_P = Pattern.compile("(0|0[xX])?[0-9]+[bBsSlL]?[uU]?");
    private static final Pattern FLOAT_P = Pattern.compile("[0-9]+(\\.[0-9]+)?[fFdD]?");
    private static final int BYTES_INT = 4;
    private static final int BYTES_LONG = 8;
    
    
    public static final Literal decodeNumber(String str) throws CompilerError
    {
        if(INTEGER_P.matcher(str).matches())
            return decodeInteger(str);
        if(FLOAT_P.matcher(str).matches())
            return decodeFloat(str);
        return null;
    }
    
    private static int base(String str)
    {
        if(str.length() <= 1)
            return 10;
        char c = str.charAt(0);
        if(c != '0')
            return 10;
        c = str.charAt(1);
        return c == 'x' || c == 'X' ? 16 : 8;
    }
    private static int integerLen(String str)
    {
        char c = str.charAt(str.length() - 1);
        switch(c)
        {
            case 'l': case 'L': return BYTES_LONG;
            default: return BYTES_INT;
        }
    }
    private static Literal decodeInteger(String str) throws CompilerError
    {
        int base = base(str);
        int bytes = integerLen(str);
        int start = base == 8 ? 1 : base == 16 ? 2 : 0;
        int end = str.length() - (bytes != BYTES_INT ? 1 : 0);
        str = str.substring(start, end);
        
        try
        {
            switch(bytes)
            {
                case BYTES_LONG: return valueOf(Long.parseLong(str, base));
                default: return valueOf(Integer.parseInt(str, base));
            }
        }
        catch(NumberFormatException ex)
        {
            throw new CompilerError("Invalid Integer literal: " + ex);
        }
    }
    
    private static Literal decodeFloat(String str)
    {
        return valueOf(Double.parseDouble(str));
    }

    @Override
    public final String toString() { return value.toString(); }
    
    
    public static abstract class Primitive
    {
        private Primitive() {}
        
        public abstract ValueType getType();
        
        public int toInt() { throw new IllegalStateException(); }
        public long toLong() { throw new IllegalStateException(); }
        public double toDouble() { throw new IllegalStateException(); }
    }
    
    public static final class LiteralInt extends Primitive
    {
        private final int pvalue;
        private LiteralInt(int pvalue) { this.pvalue = pvalue; }
        
        @Override public final ValueType getType() { return ValueType.INT; }
        @Override public int toInt() { return pvalue; }
    }
    
    public static final class LiteralLong extends Primitive
    {
        private final long pvalue;
        private LiteralLong(long pvalue) { this.pvalue = pvalue; }
        
        @Override public final ValueType getType() { return ValueType.LONG; }
        @Override public long toLong() { return pvalue; }
    }
    
    public static final class LiteralDouble extends Primitive
    {
        private final double pvalue;
        private LiteralDouble(double pvalue) { this.pvalue = pvalue; }
        
        @Override public final ValueType getType() { return ValueType.DOUBLE; }
        @Override public double toDouble() { return pvalue; }
    }
    
    public static enum LiteralType { INT32, INT64, FLOAT32, FLOAT64, BOOL, STRING, ARRAY, OBJECT, UNDEFINED, UNKNOWN; }
    
    /*public enum LiteralType
    {
        UNDEFINED,
        INTEGER,
        FLOAT,
        STRING,
        CONST_ARRAY,
        CONST_OBJECT;
        
        private static LiteralType decode(int type)
        {
            switch(type)
            {
                case ImmutableType.UNDEFINED: return UNDEFINED;
                case ImmutableType.INTEGER: return INTEGER;
                case ImmutableType.FLOAT: return FLOAT;
                case ImmutableType.STRING: return STRING;
                case ImmutableType.CONST_ARRAY: return CONST_ARRAY;
                case ImmutableType.CONST_OBJECT: return CONST_OBJECT;
                default: throw new IllegalStateException();
            }
        }
        
        public final DataType getDataType()
        {
            switch(this)
            {
                case UNDEFINED: return DataType.ANY;
                case INTEGER: return DataType.INTEGER;
                case FLOAT: return DataType.FLOAT;
                case STRING: return DataType.STRING;
                case CONST_ARRAY: return DataType.ARRAY;
                case CONST_OBJECT: return DataType.OBJECT;
                default: return DataType.ANY;
            }
        }
    }*/
}
