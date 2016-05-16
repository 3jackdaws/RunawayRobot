package com.stretchy;

import com.stretchy.interfaces.ISolverVisitor;
import com.stretchy.interfaces.ISolverVisitorAcceptor;

/**
 * Created by Ian Murphy on 5/16/2016.
 */
public class SolverThread extends Thread implements ISolverVisitorAcceptor {
    protected ISolverVisitor _algorithm;
    protected String _status;
    public SolverThread(String threadName)
    {
        super(threadName);
    }
    @Override
    public void run() {
        _algorithm.
    }

    @Override
    public void AcceptVisitor(ISolverVisitor visitor) {
        _algorithm = visitor;
    }

    public String get_status() {
        return _status;
    }
}
