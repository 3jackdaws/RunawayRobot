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

    protected int _problemSize;


    public SolverThread(String threadName)
    {
        super(threadName);
    }
    @Override
    public void run() {
        //_algorithm
        _displayableString = "Active - Solving";
        _displayableUpdated = true;
        _algorithm.setqSize(_problemSize);
        _algorithm.Solve();

    }

    public int getProblemSize() {
        return _problemSize;
    }

    public void setProblemSize(int _problemSize) {
        this._problemSize = _problemSize;
    }

    public void setGameboard(Gameboard _gameBoard) {
        this._gameBoard = _gameBoard;
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
