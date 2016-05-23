package com.stretchy;

import com.stretchy.interfaces.Displayable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by ian on 5/16/16.
 */
public class SolverThreadCoordinator implements Displayable{
    Gameboard _board;
    ArrayList<SolverThread> _threads;
    LinkedList<Integer> _unclaimedQueues;
    ArrayList<String> _descriptions;
    protected OutputHandler _handler;
    protected boolean _threadStop = false;
    int _listSize;
    int _min;
    public SolverThreadCoordinator(Gameboard board)
    {
        _board = board;
        _min = _board.getMinInstructions();
        _listSize = _board.getMaxInstructions() - _min;

        _threads = new ArrayList<>();
        _unclaimedQueues = new LinkedList<>();
        _descriptions = new ArrayList<>(_listSize);
        for (int i = 0; i< _listSize; i++)
        {
            _unclaimedQueues.add(i+_min);
            _descriptions.add("No Action");
        }

    }

    public void registerThreads(Collection<SolverThread> threads)
    {
        _threads.addAll(threads);
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
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<_listSize; i++)
        {
            sb.append("Queue size " + (i+_min) + ": " + _descriptions.get(i) + "\n");
        }
        if(_threadStop)
            sb.append(_board.GetPathFormatted() + "\n");
        return sb.toString();
    }

    public int getNextInQueue(String dir)
    {
        synchronized (this){
            if(_unclaimedQueues.size() == 0) return 0;
            else if(dir == "Front")
            {
                return _unclaimedQueues.removeFirst();
            }
            else
            {
                return _unclaimedQueues.removeLast();
            }
        }

    }

    public void registerWork(String update, int problem_number)
    {
        _descriptions.set(problem_number - _min, update);
        _handler.WriteOutput();
    }

    public boolean threadStop()
    {
        return  _threadStop;
    }

    public void stopAllThreads()
    {
        _threadStop = true;
        _board.Submit();
    }

    public void registerOutputHandler(OutputHandler out)
    {
        _handler = out;
    }
}
