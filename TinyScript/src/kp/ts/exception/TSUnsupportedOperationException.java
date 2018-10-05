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
public class TSUnsupportedOperationException extends TSRuntimeException
{
    public TSUnsupportedOperationException(TSDataType selfType, String opName)
    {
        super(selfType.getTypeName() + " cannot support " + opName + " operation");
    }
    
    public TSUnsupportedOperationException(TSValue self, String opName)
    {
        this(self.getTSDataType(),opName);
    }
}
