/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.utils;

/**
 *
 * @author Asus
 */
public interface SpecialProperty
{
    String CAST_NUMBER = "(number)";
    String CAST_BOOLEAN = "(boolean)";
    String CAST_STRING = "(string)";
    String CAST_ARRAY = "(array)";
    String CAST_ITERATOR = "(iterator)";
    
    String OP_EQUALS = "==";
    String OP_NOT_EQUALS = "!=";
    String OP_GREATER_THAN = ">";
    String OP_SMALLER_THAN = "<";
    String OP_GREATER_EQUALS_THAN = ">=";
    String OP_SMALLER_EQUALS_THAN = "<=";
    String OP_NOT = "!";
    String OP_LENGTH = "length";
    
    String OP_PLUS = "+";
    String OP_MINUS = "-";
    String OP_MULTIPLY = "*";
    String OP_DIVIDE = "/";
    String OP_REMAINDER = "%";
    String OP_INCREASE = "++";
    String OP_DECREASE = "--";
    String OP_NEGATIVE = "-()";
    
    String OP_BITWISE_SHIFT_LEFT = "<<";
    String OP_BITWISE_SHIFT_RIGHT = ">>";
    String OP_BITWISE_AND = "&";
    String OP_BITWISE_OR = "|";
    String OP_BITWISE_XOR = "^";
    String OP_BITWISE_NOT = "~";
    
    String OP_GET = "[]";
    String OP_SET = "[]=";
    
    String OP_CALL = "()";
    
    String CONSTRUCTOR = "constructor";
    String HASHCODE = "hashCode";
}
