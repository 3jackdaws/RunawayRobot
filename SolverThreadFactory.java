package com.stretchy;

import com.stretchy.Gameboard;
import com.stretchy.OutputHandler;
import com.stretchy.SolverThread;
import com.stretchy.SolverThreadCoordinator;
import com.stretchy.interfaces.ISolvable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by ian on 5/16/16.
 */
public class SolverThreadFactory{

    protected ISolvable _solver;
    protected OutputHandler _handler;
    protected Gameboard _board;
    protected SolverThreadCoordinator _coordinator;
    protected String _direction;
    protected static int _num_created = 0;

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

    public void addDirection(String d)
    {
        _direction = d;
    }

    public Collection<SolverThread> createThreads(int num_threads)
    {
        SolverThread [] threads = new SolverThread[num_threads];
        for (int i = 0; i<num_threads; i++)
        {

            threads[i] = new SolverThread(String.valueOf(_num_created++), _direction);

            threads[i].AcceptSolvable(_solver.clone());

            threads[i].set_coordinator(_coordinator);
            threads[i].setGameboard(_board);
        }
        return Arrays.asList(threads);
    }
}
