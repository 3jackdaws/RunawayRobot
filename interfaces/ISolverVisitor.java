package com.stretchy.interfaces;

import com.stretchy.Gameboard;

/**
 * Created by ian on 3/25/16.
 */
public interface ISolverVisitor
{
    public void SolverAction(Gameboard gb);
    public ISolverVisitor clone();
}
