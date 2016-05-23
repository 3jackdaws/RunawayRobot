package com.stretchy;

import com.stretchy.interfaces.*;

/**
 * Created by Ian Murphy on 5/16/2016.
 */
public class SolverThread extends Thread implements ISolvableAcceptor,Displayable {
    protected ISolvable _algorithm;
    protected Gameboard _gameBoard;
    protected SolverThreadCoordinator _coordinator;

    protected String _displayableString = "Inactive";
    protected boolean _displayableUpdated;
    protected String _workDirection;


    public SolverThread(String threadName, String behavior)
    {
        super(threadName);
        _workDirection = behavior;
    }
    @Override
    public void run() {
        _algorithm.AcceptCoordinator(_coordinator);
        int queueSize = 0;
        while(!_coordinator.threadStop()){
            queueSize = _coordinator.getNextInQueue(_workDirection);
            _coordinator.registerWork("Working - [Not Profiled]", queueSize);
            _algorithm.setqSize(queueSize);
            if(_algorithm.Solve()) {
                _coordinator.registerWork("Solved - Thread [" + this.getName() + "]", queueSize);
                _coordinator.stopAllThreads();
            }
            else
                _coordinator.registerWork(C.YELLOW + "Completed - Thread [" + this.getName() + "]" + C.DEFAULT, queueSize);
        }


    }



    public void setGameboard(Gameboard _gameBoard) {
        this._gameBoard = _gameBoard;
    }

    public void set_coordinator(SolverThreadCoordinator _coordinator) {
        this._coordinator = _coordinator;
    }

    @Override
    public void AcceptSolvable(ISolvable solvable) {
        _algorithm = solvable;
    }

    @Override
    public String display() {
        if(_displayableUpdated){
            _displayableUpdated = false;
            return _displayableString;
        }
        return null;
    }
}
