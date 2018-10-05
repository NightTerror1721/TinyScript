/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.utils;

import kp.ts.lang.TSFunction;
import kp.ts.lang.TSValue;
import kp.ts.lang.TSVarargs;

/**
 *
 * @author Asus
 */
final class MethodDefs
{
    private MethodDefs() {}
    
    public static abstract class ZeroArgsClosure extends TSFunction
    {
        @Override public abstract TSValue call(TSValue self);
        @Override public final TSValue call(TSValue self, TSValue arg0) { return call(self); }
        @Override public final TSValue call(TSValue self, TSValue arg0, TSValue arg1) { return call(self); }
        @Override public final TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2) { return call(self); }
        @Override public final TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3) { return call(self); }
        @Override public final TSValue call(TSValue self, TSVarargs args) { return call(self); }
    }
    
    public static abstract class OneArgClosure extends TSFunction
    {
        @Override public final TSValue call(TSValue self) { return call(self, UNDEFINED); }
        @Override public abstract TSValue call(TSValue self, TSValue arg0);
        @Override public final TSValue call(TSValue self, TSValue arg0, TSValue arg1) { return call(self, arg0); }
        @Override public final TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2) { return call(self, arg0); }
        @Override public final TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3) { return call(self, arg0); }
        @Override public final TSValue call(TSValue self, TSVarargs args) { return call(self, args.arg0()); }
    }
    
    public static abstract class TwoArgsClosure extends TSFunction
    {
        @Override public final TSValue call(TSValue self) { return call(self, UNDEFINED, UNDEFINED); }
        @Override public final TSValue call(TSValue self, TSValue arg0) { return call(self, arg0, UNDEFINED); }
        @Override public abstract TSValue call(TSValue self, TSValue arg0, TSValue arg1);
        @Override public final TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2) { return call(self, arg0, arg1); }
        @Override public final TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3) { return call(self, arg0, arg1); }
        @Override public final TSValue call(TSValue self, TSVarargs args) { return call(self, args.arg0(), args.arg(1)); }
    }
    
    public static abstract class ThreeArgsClosure extends TSFunction
    {
        @Override public final TSValue call(TSValue self) { return call(self, UNDEFINED, UNDEFINED, UNDEFINED); }
        @Override public final TSValue call(TSValue self, TSValue arg0) { return call(self, arg0, UNDEFINED, UNDEFINED); }
        @Override public final TSValue call(TSValue self, TSValue arg0, TSValue arg1) { return call(self, arg0, arg1, UNDEFINED); }
        @Override public abstract TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2);
        @Override public final TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3) { return call(self, arg0, arg1, arg2); }
        @Override public final TSValue call(TSValue self, TSVarargs args) { return call(self, args.arg0(), args.arg(1), args.arg(2)); }
    }
    
    public static abstract class FourArgsClosure extends TSFunction
    {
        @Override public final TSValue call(TSValue self) { return call(self, UNDEFINED, UNDEFINED, UNDEFINED, UNDEFINED); }
        @Override public final TSValue call(TSValue self, TSValue arg0) { return call(self, arg0, UNDEFINED, UNDEFINED, UNDEFINED); }
        @Override public final TSValue call(TSValue self, TSValue arg0, TSValue arg1) { return call(self, arg0, UNDEFINED, UNDEFINED); }
        @Override public final TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2) { return call(self, arg0, arg1, arg2, UNDEFINED); }
        @Override public abstract TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3);
        @Override public final TSValue call(TSValue self, TSVarargs args) { return call(self, args.arg0(), args.arg(1), args.arg(2), args.arg(3)); }
    }
    
    public static abstract class VarargsClosure extends TSFunction
    {
        @Override public final TSValue call(TSValue self) { return call(self, EMPTY); }
        @Override public final TSValue call(TSValue self, TSValue arg0) { return call(self, (TSVarargs) arg0); }
        @Override public final TSValue call(TSValue self, TSValue arg0, TSValue arg1) { return call(self, varargsOf(arg0, arg1)); }
        @Override public final TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2) { return call(self, varargsOf(arg0, arg1, arg2)); }
        @Override public final TSValue call(TSValue self, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3) { return call(self, varargsOf(arg0, arg1, arg2, arg3)); }
        @Override public abstract TSValue call(TSValue self, TSVarargs args);
    }
    
    @FunctionalInterface
    public static interface ZeroArgsJavaClosure { TSValue call(); }
    @FunctionalInterface
    public static interface VoidZeroArgsJavaClosure { void call(); }
    @FunctionalInterface
    public static interface ZeroArgsJavaMethod<T extends TSValue> { TSValue invoke(T self); }
    @FunctionalInterface
    public static interface VoidZeroArgsJavaMethod<T extends TSValue> { void invoke(T self); }
    
    @FunctionalInterface
    public static interface OneArgJavaClosure { TSValue call(TSValue arg0); }
    @FunctionalInterface
    public static interface VoidOneArgJavaClosure { void call(TSValue arg0); }
    @FunctionalInterface
    public static interface OneArgJavaMethod<T extends TSValue> { TSValue invoke(T self, TSValue arg0); }
    @FunctionalInterface
    public static interface VoidOneArgJavaMethod<T extends TSValue> { void invoke(T self, TSValue arg0); }
    
    @FunctionalInterface
    public static interface TwoArgsJavaClosure { TSValue call(TSValue arg0, TSValue arg1); }
    @FunctionalInterface
    public static interface VoidTwoArgsJavaClosure { void call(TSValue arg0, TSValue arg1); }
    @FunctionalInterface
    public static interface TwoArgsJavaMethod<T extends TSValue> { TSValue invoke(T self, TSValue arg0, TSValue arg1); }
    @FunctionalInterface
    public static interface VoidTwoArgsJavaMethod<T extends TSValue> { void invoke(T self, TSValue arg0, TSValue arg1); }
    
    @FunctionalInterface
    public static interface ThreeArgsJavaClosure { TSValue call(TSValue arg0, TSValue arg1, TSValue arg2); }
    @FunctionalInterface
    public static interface VoidThreeArgsJavaClosure { void call(TSValue arg0, TSValue arg1, TSValue arg2); }
    @FunctionalInterface
    public static interface ThreeArgsJavaMethod<T extends TSValue> { TSValue invoke(T self, TSValue arg0, TSValue arg1, TSValue arg2); }
    @FunctionalInterface
    public static interface VoidThreeArgsJavaMethod<T extends TSValue> { void invoke(T self, TSValue arg0, TSValue arg1, TSValue arg2); }
    
    @FunctionalInterface
    public static interface FourArgsJavaClosure { TSValue call(TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3); }
    @FunctionalInterface
    public static interface VoidFourArgsJavaClosure { void call(TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3); }
    @FunctionalInterface
    public static interface FourArgsJavaMethod<T extends TSValue> { TSValue invoke(T self, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3); }
    @FunctionalInterface
    public static interface VoidFourArgsJavaMethod<T extends TSValue> { void invoke(T self, TSValue arg0, TSValue arg1, TSValue arg2, TSValue arg3); }
    
    @FunctionalInterface
    public static interface VarargsJavaClosure { TSValue call(TSVarargs args); }
    @FunctionalInterface
    public static interface VoidVarargsJavaClosure { void call(TSVarargs args); }
    @FunctionalInterface
    public static interface VarargsJavaMethod<T extends TSValue> { TSValue invoke(T self, TSVarargs args); }
    @FunctionalInterface
    public static interface VoidVarargsJavaMethod<T extends TSValue> { void invoke(T self, TSVarargs args); }
}
