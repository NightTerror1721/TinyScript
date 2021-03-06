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
public final class Command extends CodeFragment
{
    private final CommandId id;
    
    private Command(CommandId id)
    {
        if(id == null)
            throw new NullPointerException();
        this.id = id;
    }
    
    public final CommandId getCommandId() { return id; }
    
    @Override
    public final CodeFragmentType getFragmentType() { return CodeFragmentType.COMMAND; }

    @Override
    public final boolean isValidOperand() { return false; }
    
    
    public static final Command
            DEF = new Command(CommandId.DEF),
            GLOBAL = new Command(CommandId.GLOBAL),
            INCLUDE = new Command(CommandId.INCLUDE),
            IMPORT = new Command(CommandId.IMPORT),
            CONST = new Command(CommandId.CONST),
            IF = new Command(CommandId.IF),
            ELSE = new Command(CommandId.ELSE),
            FOR = new Command(CommandId.FOR),
            WHILE = new Command(CommandId.WHILE),
            BREAK = new Command(CommandId.BREAK),
            CONTINUE = new Command(CommandId.CONTINUE),
            RETURN = new Command(CommandId.RETURN);

    @Override
    public final String toString() { return id.toString(); }
    
}
