package com.stretchy;

/**
 * Created by ian on 3/25/16.
 */
public interface ISolverVisitor
{
    public void SolverAction(Gameboard gb, OutputHandler out);
    public void Solve(int queueSize) throws Exception;
}
