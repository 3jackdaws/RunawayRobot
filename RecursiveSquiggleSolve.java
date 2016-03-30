package com.stretchy;

import java.util.ArrayDeque;

/**
 * Created by ian on 3/25/16.
 */
public class RecursiveSquiggleSolve implements ISolverVisitor
{
    private Gameboard Board;
    private char[] Terrain;
    ArrayDeque<Character> MovementQueue;
    private int xpos, ypos;
    private int full;
    @Override
    public void SolverAction(Gameboard gb) {
        Board = gb;
        Terrain = Board.getTerrain().toCharArray();
        MovementQueue = Board.getInstructionQueue().clone();
        Solve();
        Board.setInstructionQueue(MovementQueue.clone());
    }

    public void Solve()
    {
        System.out.println("Level: " + Board.getLevel());
        System.out.println("Calculating Sub-board.");
        System.out.println("Sub-board size: " + Board.getMaxInstructions() + " by " + Board.getMaxInstructions());
        RecursiveTryAll();
    }



    private void RecursiveTryAll()
    {
        int instr_num = Board.getMinInstructions();
        //long startTime = System.nanoTime();

        while(!FullBoardCheck())
        {
            MovementQueue.clear();
            xpos = 0;
            ypos = 0;
            System.out.println("Checking queue size: " + instr_num);
            try {
                BuildQueue(instr_num++);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            //ShowPath();
        }
        //long endTime = System.nanoTime();
        //double solveTime = (endTime - startTime);

        //System.out.println("Board level " + Board.getLevel() + " solved.");
        System.out.println(MovementQueue);
        //System.out.println("PATH: " + GetPathFormatted());
        //System.out.println("Solved in " + solveTime/10000 + " milliseconds.");
        //System.out.println("Solved in " + GuessNumber + " guesses.");
    }

    private void BuildQueue(int size) throws Exception {
        if(MovementQueue.size() == size) {
            if (FullBoardCheck())
            {
                throw new Exception("Solved Correctly");
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

    private void VisualSolverOutput(int x, int y)
    {

        System.out.print("\n\n");
        int sub_board_size = Board.getMaxInstructions();
        for (int row = 0; row < sub_board_size; row++)
        {
            for (int col = 0; col < sub_board_size; col++)
            {
                if(row == x && col == y)
                    System.out.print('@' + " ");
                else
                    System.out.print(Terrain[row * Board.getBoardX() + col] + " ");
            }
            System.out.print("\n");
        }
    }
}
