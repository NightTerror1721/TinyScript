/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.lang;

import kp.ts.utils.Def;

/**
 *
 * @author Asus
 */
public abstract class TSNumber extends TSValue
{
    private TSNumber() {}
    
    @Override
    public final TSDataType getTSDataType() { return TSDataType.NUMBER; }
    
    @Override
    public final int hashCode()
    {
        int hash = 7;
        hash = 29 * hash + Float.floatToRawIntBits(toFloat());
        return hash;
    }
    
    @Override
    public final TSNumber castToNumber() { return this; }
    
    @Override
    public final TSValue length() { return new Int32(1); }
    
    @Override
    public final TSValue getProperty(String name)
    {
        switch(name)
        {
            default: return UNDEFINED;
            case "isFinite": return IS_FINITE;
            case "isInteger": return IS_INTEGER;
            case "isDecimal": return IS_DECIMAL;
            case "isNaN": return IS_NAN;
            case "int": case "int32": case "integer": return new Int32(toInt());
            case "long": case "int64": return new Int64(toLong());
            case "float": case "float32": return new Float32(toFloat());
            case "double": case "float64": return new Float64(toDouble());
        }
    }
    
    @Override
    public final TSValue call(TSValue self)
    {
        return ZERO;
    }
    
    @Override
    public final TSValue call(TSValue self, TSValue arg0)
    {
        return new Float64(arg0.toDouble());
    }
    
    
    @Override
    public final TSValue createNewInstance() { return new Int32(0); }
    
    @Override
    public final TSValue createNewInstance(TSValue arg0) { return new Float64(arg0.toDouble()); }
    
    
    
    private static final TSValue IS_FINITE = Def.method((self) -> {
        return self.isFloat()
                ? Float.isFinite(self.toFloat()) ? TRUE : FALSE
                : self.isLongFloat()
                ? Double.isFinite(self.toDouble()) ? TRUE : FALSE
                : FALSE;
    });
    private static final TSValue IS_INTEGER = Def.method(self -> self.isDecimal() ? FALSE : TRUE);
    private static final TSValue IS_DECIMAL = Def.method(self -> self.isDecimal() ? TRUE : FALSE);
    private static final TSValue IS_NAN = Def.method((self) -> {
        return self.isFloat()
                ? Float.isNaN(self.toFloat()) ? TRUE : FALSE
                : self.isLongFloat()
                ? Double.isNaN(self.toDouble()) ? TRUE : FALSE
                : FALSE;
    });
    
    
    
    
    public static final class Int32 extends TSNumber
    {
        private final int value;
        
        public Int32(int value) { this.value = value; }
        
        @Override public final int toInt() { return this.value; }
        @Override public final long toLong() { return this.value; }
        @Override public final float toFloat() { return this.value; }
        @Override public final double toDouble() { return this.value; }
        @Override public final boolean toBoolean() { return this.value != 0; }
        @Override public final String toString() { return Integer.toString(value); }
        @Override public final boolean equals(Object o) { return o instanceof TSValue && this.value == ((TSValue) o).toDouble(); }
        @Override public final boolean isInteger() { return true; }
        
        /* Common operators */
        @Override public final TSValue equals(TSValue value) { return this.value == value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue notEquals(TSValue value) { return this.value != value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue greaterThan(TSValue value) { return this.value > value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue smallerThan(TSValue value) { return this.value < value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue greaterOrEqualsThan(TSValue value) { return this.value >= value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue smallerOrEqualsThan(TSValue value) { return this.value <= value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue not() { return toBoolean() ? FALSE : TRUE; }
        
        /* Math operators */
        @Override public final TSValue plus(TSValue value) { return new Float64(this.value + value.toDouble()) ; }
        @Override public final TSValue minus(TSValue value) { return new Float64(this.value - value.toDouble()); }
        @Override public final TSValue multiply(TSValue value) { return new Float64(this.value * value.toDouble()); }
        @Override public final TSValue divide(TSValue value) { return new Float64(this.value / value.toDouble()); }
        @Override public final TSValue remainder(TSValue value) { return new Float64(this.value % value.toDouble()); }
        @Override public final TSValue increase() { return new Int32(this.value + 1); }
        @Override public final TSValue decrease() { return new Int32(this.value - 1); }
        @Override public final TSValue negative() { return new Int32(-this.value); }
        
        /* Bitwise operators */
        @Override public final TSValue bitwiseShiftLeft(TSValue value) { return new Int64(this.value << value.toLong()); }
        @Override public final TSValue bitwiseShiftRight(TSValue value) { return new Int64(this.value >>> value.toLong()); }
        @Override public final TSValue bitwiseAnd(TSValue value) { return new Int64(this.value & value.toLong()); }
        @Override public final TSValue bitwiseOr(TSValue value) { return new Int64(this.value | value.toLong()); }
        @Override public final TSValue bitwiseXor(TSValue value) { return new Int64(this.value ^ value.toLong()); }
        @Override public final TSValue bitwiseNot() { return new Int32(~this.value); }
    }
    
    public static final class Int64 extends TSNumber
    {
        private final long value;
        
        public Int64(long value) { this.value = value; }
        
        @Override public final int toInt() { return (int) this.value; }
        @Override public final long toLong() { return this.value; }
        @Override public final float toFloat() { return this.value; }
        @Override public final double toDouble() { return this.value; }
        @Override public final boolean toBoolean() { return this.value != 0; }
        @Override public final String toString() { return Long.toString(value); }
        @Override public final boolean equals(Object o) { return o instanceof TSValue && this.value == ((TSValue) o).toDouble(); }
        @Override public final boolean isLongInteger() { return true; }
        
        /* Common operators */
        @Override public final TSValue equals(TSValue value) { return this.value == value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue notEquals(TSValue value) { return this.value != value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue greaterThan(TSValue value) { return this.value > value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue smallerThan(TSValue value) { return this.value < value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue greaterOrEqualsThan(TSValue value) { return this.value >= value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue smallerOrEqualsThan(TSValue value) { return this.value <= value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue not() { return toBoolean() ? FALSE : TRUE; }
        
        /* Math operators */
        @Override public final TSValue plus(TSValue value) { return new Float64(this.value + value.toDouble()) ; }
        @Override public final TSValue minus(TSValue value) { return new Float64(this.value - value.toDouble()); }
        @Override public final TSValue multiply(TSValue value) { return new Float64(this.value * value.toDouble()); }
        @Override public final TSValue divide(TSValue value) { return new Float64(this.value / value.toDouble()); }
        @Override public final TSValue remainder(TSValue value) { return new Float64(this.value % value.toDouble()); }
        @Override public final TSValue increase() { return new Int64(this.value + 1); }
        @Override public final TSValue decrease() { return new Int64(this.value - 1); }
        @Override public final TSValue negative() { return new Int64(-this.value); }
        
        /* Bitwise operators */
        @Override public final TSValue bitwiseShiftLeft(TSValue value) { return new Int64(this.value << value.toLong()); }
        @Override public final TSValue bitwiseShiftRight(TSValue value) { return new Int64(this.value >>> value.toLong()); }
        @Override public final TSValue bitwiseAnd(TSValue value) { return new Int64(this.value & value.toLong()); }
        @Override public final TSValue bitwiseOr(TSValue value) { return new Int64(this.value | value.toLong()); }
        @Override public final TSValue bitwiseXor(TSValue value) { return new Int64(this.value ^ value.toLong()); }
        @Override public final TSValue bitwiseNot() { return new Int64(~this.value); }
    }
    
    public static final class Float32 extends TSNumber
    {
        private final float value;
        
        public Float32(float value) { this.value = value; }
        
        @Override public final int toInt() { return (int) this.value; }
        @Override public final long toLong() { return (long) this.value; }
        @Override public final float toFloat() { return this.value; }
        @Override public final double toDouble() { return this.value; }
        @Override public final boolean toBoolean() { return this.value != 0; }
        @Override public final String toString() { return Float.toString(value); }
        @Override public final boolean equals(Object o) { return o instanceof TSValue && this.value == ((TSValue) o).toDouble(); }
        @Override public final boolean isFloat() { return true; }
        @Override public final boolean isDecimal() { return true; }
        
        /* Common operators */
        @Override public final TSValue equals(TSValue value) { return this.value == value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue notEquals(TSValue value) { return this.value != value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue greaterThan(TSValue value) { return this.value > value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue smallerThan(TSValue value) { return this.value < value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue greaterOrEqualsThan(TSValue value) { return this.value >= value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue smallerOrEqualsThan(TSValue value) { return this.value <= value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue not() { return toBoolean() ? FALSE : TRUE; }
        
        /* Math operators */
        @Override public final TSValue plus(TSValue value) { return new Float64(this.value + value.toDouble()) ; }
        @Override public final TSValue minus(TSValue value) { return new Float64(this.value - value.toDouble()); }
        @Override public final TSValue multiply(TSValue value) { return new Float64(this.value * value.toDouble()); }
        @Override public final TSValue divide(TSValue value) { return new Float64(this.value / value.toDouble()); }
        @Override public final TSValue remainder(TSValue value) { return new Float64(this.value % value.toDouble()); }
        @Override public final TSValue increase() { return new Float32(this.value + 1); }
        @Override public final TSValue decrease() { return new Float32(this.value - 1); }
        @Override public final TSValue negative() { return new Float32(-this.value); }
        
        /* Bitwise operators */
        @Override public final TSValue bitwiseShiftLeft(TSValue value) { return new Int64((long) this.value << value.toLong()); }
        @Override public final TSValue bitwiseShiftRight(TSValue value) { return new Int64((long) this.value >>> value.toLong()); }
        @Override public final TSValue bitwiseAnd(TSValue value) { return new Int64((long) this.value & value.toLong()); }
        @Override public final TSValue bitwiseOr(TSValue value) { return new Int64((long) this.value | value.toLong()); }
        @Override public final TSValue bitwiseXor(TSValue value) { return new Int64((long) this.value ^ value.toLong()); }
        @Override public final TSValue bitwiseNot() { return new Int64(~((long) this.value)); }
    }
    
    public static final class Float64 extends TSNumber
    {
        private final double value;
        
        public Float64(double value) { this.value = value; }
        
        @Override public final int toInt() { return (int) this.value; }
        @Override public final long toLong() { return (long) this.value; }
        @Override public final float toFloat() { return (float) this.value; }
        @Override public final double toDouble() { return this.value; }
        @Override public final boolean toBoolean() { return this.value != 0; }
        @Override public final String toString() { return Double.toString(value); }
        @Override public final boolean equals(Object o) { return o instanceof TSValue && this.value == ((TSValue) o).toDouble(); }
        @Override public final boolean isLongFloat() { return true; }
        @Override public final boolean isDecimal() { return true; }
        
        /* Common operators */
        @Override public final TSValue equals(TSValue value) { return this.value == value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue notEquals(TSValue value) { return this.value != value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue greaterThan(TSValue value) { return this.value > value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue smallerThan(TSValue value) { return this.value < value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue greaterOrEqualsThan(TSValue value) { return this.value >= value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue smallerOrEqualsThan(TSValue value) { return this.value <= value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue not() { return toBoolean() ? FALSE : TRUE; }
        
        /* Math operators */
        @Override public final TSValue plus(TSValue value) { return new Float64(this.value + value.toDouble()) ; }
        @Override public final TSValue minus(TSValue value) { return new Float64(this.value - value.toDouble()); }
        @Override public final TSValue multiply(TSValue value) { return new Float64(this.value * value.toDouble()); }
        @Override public final TSValue divide(TSValue value) { return new Float64(this.value / value.toDouble()); }
        @Override public final TSValue remainder(TSValue value) { return new Float64(this.value % value.toDouble()); }
        @Override public final TSValue increase() { return new Float64(this.value + 1); }
        @Override public final TSValue decrease() { return new Float64(this.value - 1); }
        @Override public final TSValue negative() { return new Float64(-this.value); }
        
        /* Bitwise operators */
        @Override public final TSValue bitwiseShiftLeft(TSValue value) { return new Int64((long) this.value << value.toLong()); }
        @Override public final TSValue bitwiseShiftRight(TSValue value) { return new Int64((long) this.value >>> value.toLong()); }
        @Override public final TSValue bitwiseAnd(TSValue value) { return new Int64((long) this.value & value.toLong()); }
        @Override public final TSValue bitwiseOr(TSValue value) { return new Int64((long) this.value | value.toLong()); }
        @Override public final TSValue bitwiseXor(TSValue value) { return new Int64((long) this.value ^ value.toLong()); }
        @Override public final TSValue bitwiseNot() { return new Int64(~((long) this.value)); }
    }
    
    
    
    
    public static final class Char extends TSNumber
    {
        private final char value;
        
        public Char(char value) { this.value = value; }
        
        @Override public final int toInt() { return this.value; }
        @Override public final long toLong() { return this.value; }
        @Override public final float toFloat() { return this.value; }
        @Override public final double toDouble() { return this.value; }
        @Override public final boolean toBoolean() { return this.value != 0; }
        @Override public final String toString() { return Integer.toString(value); }
        @Override public final boolean equals(Object o) { return o instanceof TSValue && this.value == ((TSValue) o).toDouble(); }
        @Override public final boolean isInteger() { return true; }
        
        /* Common operators */
        @Override public final TSValue equals(TSValue value) { return this.value == value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue notEquals(TSValue value) { return this.value != value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue greaterThan(TSValue value) { return this.value > value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue smallerThan(TSValue value) { return this.value < value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue greaterOrEqualsThan(TSValue value) { return this.value >= value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue smallerOrEqualsThan(TSValue value) { return this.value <= value.toDouble() ? TRUE : FALSE; }
        @Override public final TSValue not() { return toBoolean() ? FALSE : TRUE; }
        
        /* Math operators */
        @Override public final TSValue plus(TSValue value) { return new Float64(this.value + value.toDouble()) ; }
        @Override public final TSValue minus(TSValue value) { return new Float64(this.value - value.toDouble()); }
        @Override public final TSValue multiply(TSValue value) { return new Float64(this.value * value.toDouble()); }
        @Override public final TSValue divide(TSValue value) { return new Float64(this.value / value.toDouble()); }
        @Override public final TSValue remainder(TSValue value) { return new Float64(this.value % value.toDouble()); }
        @Override public final TSValue increase() { return new Int32(this.value + 1); }
        @Override public final TSValue decrease() { return new Int32(this.value - 1); }
        @Override public final TSValue negative() { return new Int32(-this.value); }
        
        /* Bitwise operators */
        @Override public final TSValue bitwiseShiftLeft(TSValue value) { return new Int64(this.value << value.toLong()); }
        @Override public final TSValue bitwiseShiftRight(TSValue value) { return new Int64(this.value >>> value.toLong()); }
        @Override public final TSValue bitwiseAnd(TSValue value) { return new Int64(this.value & value.toLong()); }
        @Override public final TSValue bitwiseOr(TSValue value) { return new Int64(this.value | value.toLong()); }
        @Override public final TSValue bitwiseXor(TSValue value) { return new Int64(this.value ^ value.toLong()); }
        @Override public final TSValue bitwiseNot() { return new Int32(~this.value); }
    }
}
