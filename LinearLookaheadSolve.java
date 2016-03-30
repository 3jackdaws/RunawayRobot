package com.stretchy;

import javafx.util.Pair;

import java.util.ArrayDeque;
import java.util.LinkedList;

/**
 * Created by ian on 3/25/16.
 */
public class LinearLookaheadSolve implements ISolverVisitor {
    private Gameboard Board;
    private char[] Terrain;
    private boolean Solved;
    private LinkedList<Pair<Integer, Integer>> exitPoints;
    ArrayDeque<Character> MovementQueue;

    @Override
    public void SolverAction(Gameboard gb) {
        Board = gb;
        Terrain = Board.getTerrain().toCharArray();
        MovementQueue = Board.getInstructionQueue();
        Solved = false;
//        for (int i = Board.getMinInstructions(); !Solved && i<Board.getMaxInstructions(); i++)
//        {
//            Solve(i);
//        }
        FindExitPoints(Board.getBoardX(), Board.getBoardY());


    }

    private void FindExitPoints(int x, int y)
    {
        exitPoints = new LinkedList<>();
        for (int row = y-1, col = 0; row >= 0; )
        {
            if(Terrain[row * Board.getBoardX() + col] == '.')
            {
                exitPoints.add(new Pair<>(row, col));
                //row = row - col;
            }
            if(col == x-1)
                row--;
            else
                col++;
        }
    }

    private void CalculateLine(Pair<Integer, Integer> exitpoint)
    {
        int num_down;
        int num_right;

    }
}
