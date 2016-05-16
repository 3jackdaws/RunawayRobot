package com.stretchy.interfaces;

import com.stretchy.Gameboard;
import com.stretchy.OutputHandler;
import com.stretchy.SolverThread;
import com.stretchy.SolverThreadCoordinator;

import java.util.Collection;

/**
 * Created by ian on 5/16/16.
 */
public class SolverThreadFactory{

    protected ISolvable _solver;
    protected OutputHandler _handler;
    protected Gameboard _board;
    protected SolverThreadCoordinator _coordinator;

    public SolverThreadFactory()
    {

    }

    public void addAlgorithm(ISolvable solver)
    {
        _solver = solver;
    }

    public void addOutputHandler(OutputHandler handler){
        _handler = handler;
    }

    public void addGameboard(Gameboard board)
    {
        _board = board;
    }

    public void addCoordinator(SolverThreadCoordinator coordinator)
    {
        _coordinator = coordinator;
    }

    public Collection<SolverThread> createThreads(int num_threads)
    {
        SolverThread [] threads = new SolverThread[num_threads];
        for (int i = 0; i<num_threads; i++)
        {
            threads[i] = new SolverThread(String.valueOf(i));
            threads[i].AcceptSolvable(_solver);
            threads[i].setGameboard(_board);

        }
    }
}
