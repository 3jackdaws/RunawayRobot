package com.stretchy;

import com.stretchy.interfaces.Displayable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by ian on 5/16/16.
 */
public class SolverThreadCoordinator implements Displayable{
    Gameboard _board;
    ArrayList<SolverThread> _threads;
    ArrayList<Integer> unclaimedQueues;
    ArrayList<String> 
    protected String _displayableString;
    public SolverThreadCoordinator(Gameboard board)
    {
        _board = board;

    }

    public void registerThreads(Collection<SolverThread> threads)
    {
        _threads.addAll(threads);
    }

    protected boolean pollThreads()
    {
        boolean needsUpdating = false;
        StringBuilder sb = new StringBuilder();
        for (SolverThread t : _threads)
        {
            sb.append("Thread ")
        }
    }

    public void start()
    {
        for (SolverThread s : _threads)
        {
            s.start();
        }

        for (SolverThread s : _threads)
        {
            try {
                s.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String display() {
        pollThreads();
        return null;
    }
}
