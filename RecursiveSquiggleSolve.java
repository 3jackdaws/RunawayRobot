package com.stretchy;

import com.stretchy.interfaces.ISolvable;
import com.stretchy.interfaces.ISolverVisitor;

import java.util.ArrayDeque;


/**
 * Created by ian on 3/25/16.
 */
public class RecursiveSquiggleSolve implements ISolverVisitor, ISolvable
{
    private Gameboard Board;
    private char[] Terrain;
    ArrayDeque<Character> MovementQueue;
    private int xpos, ypos;
    private int _num_checks = 0;
    private int qSize;
    private int _dim;
    private OutputHandler outputHandler;
    private SolverThreadCoordinator _coordinator;
    @Override
    public void SolverAction(Gameboard gb) {
        Board = gb;
        _dim = Board.getBoardX();
        Terrain = Board.getTerrain().toCharArray();

        MovementQueue = Board.getInstructionQueue().clone();
    }

    @Override
    public RecursiveSquiggleSolve clone() {
        RecursiveSquiggleSolve obj = new RecursiveSquiggleSolve();
        obj.SolverAction(this.Board);
        return obj;
    }

    private void BuildQueue(int size) throws BoardSolvedException {
        if(MovementQueue.size() == size) {
            _num_checks++;
            if (FullBoardCheck())
            {
                Board.setInstructionQueue(MovementQueue);
                Board.Lock();
                throw new BoardSolvedException();
            }
            //OutputSolutionPath();
            if(_num_checks % 1048576 == 0) {
                _profile();
                if (_coordinator.threadStop()) throw new BoardSolvedException();
            }
        }
        if(SingleMoveCheck() && MovementQueue.size() < size) {
            MovementQueue.add('D');
            ypos++;
            BuildQueue(size);
            MovementQueue.removeLast();
            ypos--;
            MovementQueue.add('R');
            xpos++;
            BuildQueue(size);
            MovementQueue.removeLast();
            xpos--;
        }
    }

    private boolean FullBoardCheck()
    {
        if(MovementQueue.isEmpty())
            return false;
        int posx = xpos, posy = ypos;
        ArrayDeque<Character> checkQueue;
        char move = 'q';
        xpos = 0;
        ypos = 0;
        while(xpos < Board.getBoardX() && ypos < Board.getBoardY()) {
            for (checkQueue = MovementQueue.clone(); checkQueue.size() > 0 && SingleMoveCheck(); )
            {
                move = checkQueue.pop();
                if(move == 'D')
                    ypos++;
                else
                    xpos++;
                if(xpos == Board.getBoardX() || ypos == Board.getBoardY())
                    break;
            }
            if(!SingleMoveCheck()) {
                xpos = posx;
                ypos = posy;
                return false;
            }
        }
        return true;
    }

    private boolean SingleMoveCheck()
    {
        if(ypos == _dim || xpos == _dim)
            return true;
        return Terrain[ypos * Board.getBoardX() + xpos] == '.';
    }

    @Override
    public boolean Solve() {
        MovementQueue = Board.getInstructionQueue().clone();
        xpos = 0;
        ypos = 0;
        try {
            BuildQueue(qSize);
        } catch (BoardSolvedException e) {
            return true;
        }
        return false;
    }

    public void setqSize(int qSize) {
        this.qSize = qSize;
    }

    @Override
    public void AcceptCoordinator(SolverThreadCoordinator coordinator) {
        _coordinator = coordinator;
    }

    public void OutputSolutionPath() {
        char[] pathTerrain = Board.getTerrain().toCharArray();
        ArrayDeque<Character> solutionPath = MovementQueue.clone();
        int row, col;
        char move;
        for (row = 0, col = 0; col < _dim || row < _dim; ) {
            if (solutionPath.size() == 0)
                solutionPath = MovementQueue.clone();
            move = solutionPath.pop();
            if (move == 'R')
                col++;
            else if (move == 'D')
                row++;
            if (pathTerrain[row * Board.getBoardX() + col] == 'X') break;
                pathTerrain[row * Board.getBoardX() + col] = '*';
        }

        char out;
        System.out.print("\n\n");
        int numchars = _dim * _dim;
        for (int i = 1; i < numchars + 1; i++) {
            out = pathTerrain[i - 1];
            if(out == 'X')
                System.out.print("\033[31;1m" + out + "\033[0m");
            else if(out == '#')
                System.out.print("\033[33m" + out + "\033[0m");
            else if(out == '*')
                System.out.print("\033[32m" + out + "\033[0m");
            else
                System.out.print(out);
            System.out.print(" ");
            if ((i % _dim) == 0)
                System.out.print("\n");
        }

    }
    private void _profile()
    {
        ArrayDeque<Character> queue = MovementQueue.clone();
        long currentProgress = 0;
        long max = 0;
        for(int i = 0; i<queue.size(); i++){
            currentProgress <<= 1;
            max <<=1;
            max+=1;
            if(queue.pop() == 'D')
                currentProgress += 0;
            else
                currentProgress += 1;
        }
        float per_mill = (float)(currentProgress*100)/max;
        _coordinator.registerWork("Working - [ " + C.GREEN + String.format("%.02f", per_mill) + "% " + C.DEFAULT +"]", qSize);
    }

}
