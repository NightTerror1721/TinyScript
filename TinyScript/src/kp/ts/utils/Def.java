/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.utils;

import kp.ts.lang.TSFunction;
import kp.ts.lang.TSValue;
import kp.ts.lang.TSVarargs;
import kp.ts.utils.MethodDefs.FourArgsClosure;
import kp.ts.utils.MethodDefs.FourArgsJavaClosure;
import kp.ts.utils.MethodDefs.FourArgsJavaMethod;
import kp.ts.utils.MethodDefs.OneArgClosure;
import kp.ts.utils.MethodDefs.OneArgJavaClosure;
import kp.ts.utils.MethodDefs.OneArgJavaMethod;
import kp.ts.utils.MethodDefs.ThreeArgsClosure;
import kp.ts.utils.MethodDefs.ThreeArgsJavaClosure;
import kp.ts.utils.MethodDefs.ThreeArgsJavaMethod;
import kp.ts.utils.MethodDefs.TwoArgsClosure;
import kp.ts.utils.MethodDefs.TwoArgsJavaClosure;
import kp.ts.utils.MethodDefs.TwoArgsJavaMethod;
import kp.ts.utils.MethodDefs.VarargsClosure;
import kp.ts.utils.MethodDefs.VarargsJavaClosure;
import kp.ts.utils.MethodDefs.VarargsJavaMethod;
import kp.ts.utils.MethodDefs.VoidFourArgsJavaClosure;
import kp.ts.utils.MethodDefs.VoidFourArgsJavaMethod;
import kp.ts.utils.MethodDefs.VoidOneArgJavaClosure;
import kp.ts.utils.MethodDefs.VoidOneArgJavaMethod;
import kp.ts.utils.MethodDefs.VoidThreeArgsJavaClosure;
import kp.ts.utils.MethodDefs.VoidThreeArgsJavaMethod;
import kp.ts.utils.MethodDefs.VoidTwoArgsJavaClosure;
import kp.ts.utils.MethodDefs.VoidTwoArgsJavaMethod;
import kp.ts.utils.MethodDefs.VoidVarargsJavaClosure;
import kp.ts.utils.MethodDefs.VoidVarargsJavaMethod;
import kp.ts.utils.MethodDefs.VoidZeroArgsJavaClosure;
import kp.ts.utils.MethodDefs.VoidZeroArgsJavaMethod;
import kp.ts.utils.MethodDefs.ZeroArgsClosure;
import kp.ts.utils.MethodDefs.ZeroArgsJavaClosure;
import kp.ts.utils.MethodDefs.ZeroArgsJavaMethod;



/**
 *
 * @author Asus
 */
public final class Def
{
    private Def() {}
    
    public static final TSFunction closure(final ZeroArgsJavaClosure closure)
    {
        return new ZeroArgsClosure() { @Override public TSValue call(TSValue self) { return closure.call(); } };
    }
    public static final TSFunction vclosure(final VoidZeroArgsJavaClosure closure)
    {
        return new ZeroArgsClosure() { @Override public TSValue call(TSValue self) { closure.call(); return UNDEFINED; } };
    }
    public static final <T extends TSValue> TSFunction method(final ZeroArgsJavaMethod<T> method)
    {
        return new ZeroArgsClosure() { @Override public TSValue call(TSValue self) { return method.invoke((T) self); } };
    }
    public static final <T extends TSValue> TSFunction vmethod(final VoidZeroArgsJavaMethod<T> method)
    {
        return new ZeroArgsClosure() { @Override public TSValue call(TSValue self) { method.invoke((T) self); return UNDEFINED; } };
    }
    
    public static final TSFunction closure(final OneArgJavaClosure closure)
    {
        return new OneArgClosure() { @Override public TSValue call(TSValue self, TSValue arg0) { return closure.call(arg0); } };
    }
    public static final TSFunction vclosure(final VoidOneArgJavaClosure closure)
    {
        return new OneArgClosure() { @Override public TSValue call(TSValue self, TSValue arg0) { closure.call(arg0); return UNDEFINED; } };
    }
    public static final <T extends TSValue> TSFunction method(final OneArgJavaMethod<T> method)
    {
        return new OneArgClosure() { @Override public TSValue call(TSValue self, TSValue arg0) { return method.invoke((T) self, arg0); } };
    }
    public static final <T extends TSValue> TSFunction vmethod(final VoidOneArgJavaMethod<T> method)
    {
        return new OneArgClosure() { @Override public TSValue call(TSValue self, TSValue arg0) { method.invoke((T) self, arg0); return UNDEFINED; } };
    }
    
    public static final TSFunction closure(final TwoArgsJavaClosure closure)
    {
        return new TwoArgsClosure()
        {
            @Override public TSValue call(TSValue self, TSValue arg0, TSValue arg1)
            {
                return closure.call(arg0, arg1);
            }
        };
    }
    public static final TSFunction vclosure(final VoidTwoArgsJavaClosure closure)
    {
        return new TwoArgsClosure()
        {
            @Override public TSValue call(TSValue self, TSValue arg0, TSValue arg1)
            {
                closure.call(arg0, arg1);
                return UNDEFINED;
            }
        };
    }
    public static final <T extends TSValue> TSFunction method(final TwoArgsJavaMethod<T> method)
    {
        return new TwoArgsClosure()
        {
            @Override public TSValue call(TSValue self, TSValue arg0, TSValue arg1)
            {
                return method.invoke((T) self, arg0, arg1);
            }
        };
    }
    public static final <T extends TSValue> TSFunction vmethod(final VoidTwoArgsJavaMethod<T> method)
    {
        return new TwoArgsClosure()
        {
            @Override public TSValue call(TSValue self, TSValue arg0, TSValue arg1)
            {
                method.invoke((T) self, arg0, arg1);
                return UNDEFINED;
            }
        };
    }
    
    public static final TSFunction closure(final ThreeArgsJavaClosure closure)
    {
        return new ThreeArgsClosure()
        {
            @Override public TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2)
            {
                return closure.call(arg0, arg1, arg2);
            }
        };
    }
    public static final TSFunction vclosure(final VoidThreeArgsJavaClosure closure)
    {
        return new ThreeArgsClosure()
        {
            @Override public TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2)
            {
                closure.call(arg0, arg1, arg2);
                return UNDEFINED;
            }
        };
    }
    public static final <T extends TSValue> TSFunction method(final ThreeArgsJavaMethod<T> method)
    {
        return new ThreeArgsClosure()
        {
            @Override public TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2)
            {
                return method.invoke((T) self, arg0, arg1, arg2);
            }
        };
    }
    public static final <T extends TSValue> TSFunction vmethod(final VoidThreeArgsJavaMethod<T> method)
    {
        return new ThreeArgsClosure()
        {
            @Override public TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2)
            {
                method.invoke((T) self, arg0, arg1, arg2);
                return UNDEFINED;
            }
        };
    }
    
    public static final TSFunction closure(final FourArgsJavaClosure closure)
    {
        return new FourArgsClosure()
        {
            @Override public TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3)
            {
                return closure.call(arg0, arg1, arg2, arg3);
            }
        };
    }
    public static final TSFunction vclosure(final VoidFourArgsJavaClosure closure)
    {
        return new FourArgsClosure()
        {
            @Override public TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3)
            {
                closure.call(arg0, arg1, arg2, arg3);
                return UNDEFINED;
            }
        };
    }
    public static final <T extends TSValue> TSFunction method(final FourArgsJavaMethod<T> method)
    {
        return new FourArgsClosure()
        {
            @Override public TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3)
            {
                return method.invoke((T) self, arg0, arg1, arg2, arg3);
            }
        };
    }
    public static final <T extends TSValue> TSFunction vmethod(final VoidFourArgsJavaMethod<T> method)
    {
        return new FourArgsClosure()
        {
            @Override public TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3)
            {
                method.invoke((T) self, arg0, arg1, arg2, arg3);
                return UNDEFINED;
            }
        };
    }
    
    public static final TSFunction vaclosure(final VarargsJavaClosure closure)
    {
        return new VarargsClosure()
        {
            @Override public TSValue call(TSValue self, TSVarargs args)
            {
                return closure.call(args);
            }
        };
    }
    public static final TSFunction vavclosure(final VoidVarargsJavaClosure closure)
    {
        return new VarargsClosure()
        {
            @Override public TSValue call(TSValue self, TSVarargs args)
            {
                closure.call(args);
                return UNDEFINED;
            }
        };
    }
    public static final <T extends TSValue> TSFunction vamethod(final VarargsJavaMethod<T> method)
    {
        return new VarargsClosure()
        {
            @Override public TSValue call(TSValue self, TSVarargs args)
            {
                return method.invoke((T) self, args);
            }
        };
    }
    public static final <T extends TSValue> TSFunction vavmethod(final VoidVarargsJavaMethod<T> method)
    {
        return new VarargsClosure()
        {
            @Override public TSValue call(TSValue self, TSVarargs args)
            {
                method.invoke((T) self, args);
                return UNDEFINED;
            }
        };
    }
}
