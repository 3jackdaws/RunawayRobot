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
    private int full;
    private int qSize;
    private OutputHandler outputHandler;
    @Override
    public void SolverAction(Gameboard gb) {
        Board = gb;

        Terrain = Board.getTerrain().toCharArray();
        MovementQueue = Board.getInstructionQueue().clone();
    }

    @Override
    public RecursiveSquiggleSolve clone() {
        RecursiveSquiggleSolve obj = new RecursiveSquiggleSolve();
        return obj;
    }

    private void BuildQueue(int size) throws BoardSolvedException {
        if(MovementQueue.size() == size) {
            if (FullBoardCheck())
            {
                Board.setInstructionQueue(MovementQueue);
                throw new BoardSolvedException();
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
        while(xpos < Board.getBoardX()-1 && ypos < Board.getBoardY()-1) {
            for (checkQueue = MovementQueue.clone(); checkQueue.size() > 0 && SingleMoveCheck(); )
            {
                move = checkQueue.pop();
                if(move == 'D')
                    ypos++;
                else
                    xpos++;
                if(xpos == Board.getBoardX()-1 || ypos == Board.getBoardY()-1)
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
        return Terrain[ypos * Board.getBoardX() + xpos] == '.';
    }

    @Override
    public boolean Solve() {
        MovementQueue.clear();
        xpos = 0;
        ypos = 0;
        try {
            BuildQueue(qSize);
        } catch (BoardSolvedException e) {
            return true;
        }
    }

    public void setqSize(int qSize) {
        this.qSize = qSize;
    }
}
