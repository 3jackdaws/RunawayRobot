package com.stretchy.interfaces;

import com.stretchy.SolverThreadCoordinator;

/**
 * Created by Ian Murphy on 5/16/2016.
 */
public interface ISolvable{
    public boolean Solve();
    public void setqSize(int qSize);
    public ISolvable clone();
    void AcceptCoordinator(SolverThreadCoordinator coordinator);
}
