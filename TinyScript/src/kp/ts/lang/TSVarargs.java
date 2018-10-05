/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.lang;

import java.util.AbstractList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 * @author Asus
 */
public abstract class TSVarargs
{
    public abstract int nargs();
    public abstract TSValue arg0();
    public abstract TSValue arg(int index);
    
    
    
    /* Varargs Operations */
    /*public static final TSValue varargsAsTSArray(TSVarargs args, int start)
    {
        if(start >= args.nargs())
            return new TSArray();
        return new TSArray(new VarargsList(args,start));
    }*/
    
    public static final List<TSValue> varargsAsList(TSVarargs args, int start)
    {
        if(start >= args.nargs())
            return Collections.EMPTY_LIST;
        return new VarargsList(args,start);
    }
    
    public static final TSVarargs subVarargs(TSVarargs args, int start)
    {
        if(start >= args.nargs())
            return EMPTY;
        return new SubVarargs(args,start);
    }
    
    public static final Iterable<TSValue> iterableVarargs(TSVarargs varargs)
    {
        return () -> new Iterator<TSValue>()
        {
            private int it = 0;
            private final int len = varargs.nargs();
            @Override
            public final boolean hasNext()
            {
                return it < len;
            }

            @Override
            public final TSValue next()
            {
                return varargs.arg(it++);
            }
        };
    }
    
    public static final Iterator<TSValue> varargsIterator(TSVarargs varargs)
    {
        return iterableVarargs(varargs).iterator();
    }
    
    public static final Stream<TSValue> varargsStream(TSVarargs varargs)
    {
        return StreamSupport.stream(Spliterators.spliterator(
                varargsIterator(varargs),varargs.nargs(),Spliterator.ORDERED),false);
    }
    
    
    
    /* Varargs Of */
    public static final TSVarargs varargsOf() { return EMPTY; }
    
    public static final TSVarargs varargsOf(TSVarargs args0) { return args0; }
    
    public static final TSVarargs varargsOf(TSVarargs args0, TSVarargs args1)
    {
        return new TailVarargs(args0,args1);
    }
    
    public static final TSVarargs varargsOf(TSValue arg0, TSVarargs rest)
    {
        return new PairVarargs(arg0,rest);
    }
    
    public static final TSVarargs varargsOf(TSValue arg0, TSValue arg1,
            TSVarargs rest)
    {
        return new ArrayVarargs(arg0,arg1,rest);
    }
    
    public static final TSVarargs varargsOf(TSValue arg0, TSValue arg1, TSValue arg2,
            TSVarargs rest)
    {
        return new ArrayVarargs(new TSValue[] { arg0, arg1, arg2 }, rest);
    }
    
    public static final TSVarargs varargsOf(TSValue arg0, TSValue arg1, TSValue arg2,
            TSValue arg3, TSVarargs rest)
    {
        return new ArrayVarargs(new TSValue[] { arg0, arg1, arg2, arg3 }, rest);
    }
    
    public static final TSVarargs varargsOf(TSValue[] args, TSVarargs rest)
    {
        return new ArrayVarargs(args,rest);
    }
    
    public static final TSVarargs varargsOf(TSValue... args)
    {
        return new LiteralArrayVarargs(args);
    }
    
    
    /* Default Values in functions */
    public static final TSVarargs defaultFunctionVarargs(int min, TSVarargs args, TSVarargs defs)
    {
        if(args.nargs() >= min + defs.nargs())
            return args;
        return new PSDefaultVarargs(min,args,defs);
    }
    
    
    
    /* Class definition */
    private static final class EmptyVarargs extends TSVarargs
    {
        @Override
        public final int nargs() { return 0; }

        @Override
        public final TSValue arg0() { return TSValue.UNDEFINED; }

        @Override
        public final TSValue arg(int index) { return TSValue.UNDEFINED; }
    }
    public static final TSVarargs EMPTY = new EmptyVarargs();
    
    static final class SubVarargs extends TSVarargs
    {
        private final TSVarargs args;
        private final int start;
        
        private SubVarargs(TSVarargs args, int start)
        {
            this.args = args;
            this.start = start;
        }

        @Override
        public final TSValue arg(int index) { return args.arg(index + start); }

        @Override
        public final TSValue arg0() { return args.arg(start); }

        @Override
        public final int nargs() { return args.nargs() - start; }
    }
    
    static final class PairVarargs extends TSVarargs
    {
        private final TSValue arg0;
        private final TSVarargs rest;
        
        public PairVarargs(TSValue arg0, TSVarargs rest)
        {
            this.arg0 = arg0;
            this.rest = rest;
        }

        @Override
        public final TSValue arg(int index)
        {
            return index == 0 ? arg0 : rest.arg(index - 1);
        }

        @Override
        public final TSValue arg0()
        {
            return arg0;
        }

        @Override
        public final int nargs()
        {
            return rest.nargs() + 1;
        }
    }
    
    static final class ArrayVarargs extends TSVarargs
    {
        private final TSValue[] args;
        private final TSVarargs rest;
        
        public ArrayVarargs(TSValue[] args, TSVarargs rest)
        {
            this.args = args;
            this.rest = rest;
        }
        public ArrayVarargs(TSValue arg0, TSValue arg1, TSVarargs rest)
        {
            this.args = new TSValue[] { arg0, arg1 };
            this.rest = rest;
        }
        public ArrayVarargs(TSValue arg0, TSValue arg1, TSValue arg2, TSVarargs rest)
        {
            this.args = new TSValue[] { arg0, arg1, arg2 };
            this.rest = rest;
        }

        @Override
        public final TSValue arg(int index)
        {
            switch(index)
            {
                case 0: return arg0();
                default:
                    return index < args.length ? args[index] : rest.arg(index - args.length);
            }
        }

        @Override
        public final TSValue arg0()
        {
            return args.length == 0 ? rest.arg0() : args[0];
        }

        @Override
        public final int nargs()
        {
            return args.length + rest.nargs();
        }
    }
    
    static final class LiteralArrayVarargs extends TSVarargs
    {
        private final TSValue[] args;
        
        public LiteralArrayVarargs(TSValue[] args)
        {
            this.args = args;
        }

        @Override
        public final TSValue arg(int index)
        {
            return index < args.length ? args[index] : TSValue.UNDEFINED;
        }

        @Override
        public final TSValue arg0()
        {
            return args.length == 0 ? TSValue.UNDEFINED : args[0];
        }

        @Override
        public final int nargs()
        {
            return args.length;
        }
    }
    
    static final class TailVarargs extends TSVarargs
    {
        private final TSVarargs head, tail;
        
        private TailVarargs(TSVarargs head, TSVarargs tail)
        {
            this.head = head;
            this.tail = tail;
        }
        
        @Override
        public final TSValue arg(int index)
        {
            return index < head.nargs() ? head.arg(index) : tail.arg(index - head.nargs());
        }

        @Override
        public final TSValue arg0()
        {
            return head.nargs() > 0 ? head.arg0() : tail.arg0();
        }

        @Override
        public final int nargs()
        {
            return head.nargs() + tail.nargs();
        }
        
    }
    
    private static final class VarargsList extends AbstractList<TSValue>
    {
        private final TSVarargs args;
        private final int start;
        
        private VarargsList(TSVarargs args, int start)
        {
            if(start > args.nargs())
                throw new IllegalStateException();
            this.args = args;
            this.start = start;
        }

        @Override
        public final TSValue get(int index)
        {
            return args.arg(index + start);
        }

        @Override
        public final int size()
        {
            return args.nargs() - start;
        }
    }
    
    private static final class PSDefaultVarargs extends TSVarargs
    {
        private final int min;
        private final TSVarargs base, defs;
        
        private PSDefaultVarargs(int min, TSVarargs base, TSVarargs defs)
        {
            this.min = min;
            this.base = base;
            this.defs = defs;
        }
        
        @Override
        public final TSValue arg(int index)
        {
            return index < base.nargs()
                    ? base.arg(index)
                    : index < min
                        ? TSValue.UNDEFINED
                        : defs.arg(index - min);
        }

        @Override
        public final TSValue arg0()
        {
            return arg(0);
        }

        @Override
        public final int nargs()
        {
            return min + defs.nargs();
        }
    }
}
