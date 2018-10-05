/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.exception;

/**
 *
 * @author Asus
 */
public class TSException extends Exception
{
    public TSException() {}
    public TSException(String msg) { super(msg); }
    public TSException(Throwable th) { super(th); }
    public TSException(String msg, Throwable th) { super(msg,th); }
}
