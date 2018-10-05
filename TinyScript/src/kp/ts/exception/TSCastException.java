/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ts.exception;

import kp.ts.lang.TSDataType;
import kp.ts.lang.TSValue;

/**
 *
 * @author Asus
 */
public class TSCastException extends TSRuntimeException
{
    public TSCastException(TSDataType selfType, TSDataType targetType)
    {
        super("Cannot cast " + selfType.getTypeName() + " to " + targetType.getTypeName());
    }
    
    public TSCastException(TSValue self, TSDataType targetType)
    {
        this(self.getTSDataType(), targetType);
    }
}
