package com.stretchy;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ian on 5/15/16.
 */
public class VisitorAcceptorThreadwrapper extends Thread implements ISolverVisitorAcceptor {
    private Thread _t;
    private String _threadName;
    private Gameboard _gameBoard;
    private ISolverVisitor _visitor;
    private boolean _reverse;
    private OutputHandler _outputHandler;
    private volatile AtomicBoolean _fullStop;

    public VisitorAcceptorThreadwrapper(Gameboard gb, OutputHandler out, Boolean stopWorkController)
    {
        _gameBoard = gb;
        _reverse = false;
        _outputHandler = out;
        _fullStop = new AtomicBoolean(stopWorkController);
    }

    public VisitorAcceptorThreadwrapper(Gameboard gb, OutputHandler out, Boolean stopWorkController, boolean reverse)
    {
        _gameBoard = gb;
        _reverse = reverse;
        _outputHandler = out;
        _fullStop = new AtomicBoolean(stopWorkController);
    }
    @Override
    public void AcceptVisitor(ISolverVisitor visitor) {
        _visitor = visitor;
    }

    @Override
    public void run(){
        _visitor.SolverAction(_gameBoard, _outputHandler);
        while(!_fullStop.get()){
            int testSize = 0;
            if(_reverse) testSize = _outputHandler.getNextQueue(OutputHandler.Direction.LARGEST);
            else testSize = _outputHandler.getNextQueue(OutputHandler.Direction.SMALLEST);
            if(testSize == -1) return;
            _outputHandler.updateStatus(testSize, "[" + _threadName + "] - Working");
            try {
                _visitor.Solve(testSize);
                _outputHandler.updateStatus(testSize, "[" + _threadName + "] - Cleared");
            }catch (BoardSolvedException e) {
                //e.printStackTrace();
                _outputHandler.updateStatus(testSize, "[" + _threadName + "] - Solved");
                return;
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }



    }

    protected void Solve()
    {

    }
}
