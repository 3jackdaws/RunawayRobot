package com.stretchy;

/**
 * Created by Ian Murphy on 5/16/2016.
 */
public abstract class IOutputHandlerAcceptor {
    protected OutputHandler _outputHandler;
    public void RegisterHandler(OutputHandler handler)
    {
        _outputHandler = handler;
    }
}
