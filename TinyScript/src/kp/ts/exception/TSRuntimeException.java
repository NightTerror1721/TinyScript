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
public class TSRuntimeException extends RuntimeException
{
    public TSRuntimeException() {}
    public TSRuntimeException(String msg) { super(msg); }
    public TSRuntimeException(Throwable th) { super(th); }
    public TSRuntimeException(String msg, Throwable th) { super(msg,th); }
}
