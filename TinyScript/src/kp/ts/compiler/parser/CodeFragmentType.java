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
public enum CodeFragmentType
{
    IDENTIFIER,
    SPECIAL_IDENTIFIER,
    LITERAL,
    MUTABLE,
    OPERATOR,
    OPERATION,
    STOPCHAR,
    ARGUMENTS,
    VARARGS,
    SCOPE,
    COMMAND_ARGUMENTS,
    COMMAND;
}
