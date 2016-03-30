package com.stretchy;

/**
 * Created by ian on 3/25/16.
 */


import javafx.util.Pair;

import java.util.ArrayDeque;
import java.util.LinkedList;

/**
 * Created by ian on 3/25/16.
 */
public class SubsectionTendrilSolve implements ISolverVisitor
{
    private Gameboard Board;
    private char[] Terrain;
    private ArrayDeque<Character> MovementQueue;
    private LinkedList<Pair<Integer, Integer>> exitPoints;
    private int xpos, ypos;

    @Override
    public void SolverAction(Gameboard gb) {
        Board = gb;
        Terrain = Board.getTerrain().toCharArray();
        MovementQueue = Board.getInstructionQueue().clone();
        xpos = 0;
        ypos = 0;
        Board.setInstructionQueue(MovementQueue.clone());
        Solve();
    }

    public void Solve()
    {
        FindExitPoints(Board.getMaxInstructions());
        SubsectionTendril();
    }



    private void SubsectionTendril()
    {
        while(!FullBoardCheck())
        {
            MovementQueue.clear();
            try {
                for (Pair<Integer, Integer> exit : exitPoints)
                {
                    BuildQueue(exit.getKey(), exit.getValue());
                }

            } catch (Exception e) {

            }
            //ShowPath();
        }
        //long endTime = System.nanoTime();
        //double solveTime = (endTime - startTime);

        //System.out.println("Board level " + Board.getLevel() + " solved.");
        System.out.println(MovementQueue);
    }

    private void BuildQueue(int d, int r) throws Exception {
        if(xpos == r || ypos == d) {
            System.out.println("Checked [" + d + "][" + r + "]" );
            if (FullBoardCheck())
            {
                throw new Exception("Solved Correctly");
            }
        }
        else if(SingleMoveCheck()) {
            MovementQueue.add('D');
            ypos++;
            BuildQueue(d,r);
            MovementQueue.removeLast();
            ypos--;
            MovementQueue.add('R');
            xpos++;
            BuildQueue(d,r);
            MovementQueue.removeLast();
            ypos--;
        }


    }

    private boolean FullBoardCheck()
    {
        ArrayDeque<Character> check = MovementQueue.clone();
        for (int i = Board.getMinInstructions(); i< Board.getMaxInstructions(); i++)
        {
            QueueCheck(check);
            if(check.size() > 0)
                check.removeLast();
        }
        return false;
    }

    private boolean QueueCheck(ArrayDeque<Character> q)
    {
        if(q.isEmpty())
        return false;
        int posx = xpos, posy = ypos;
        ArrayDeque<Character> checkQueue;
        char move = 'q';
        xpos = 0;
        ypos = 0;
        while(xpos < Board.getBoardX()-1 && ypos < Board.getBoardY()-1) {
            for (checkQueue = q.clone(); checkQueue.size() > 0 && SingleMoveCheck(); )
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

    private void FindExitPoints(int inst_num)
    {
        exitPoints = new LinkedList<>();
        for (int row = inst_num-1, col = 0; row >= 0; )
        {
            if(Terrain[row * Board.getBoardX() + col] == '.')
            {
                exitPoints.add(new Pair<>(row, col));
                //row = row - col;
            }
            if(col == inst_num-1)
                row--;
            else
                col++;
        }
    }
}

