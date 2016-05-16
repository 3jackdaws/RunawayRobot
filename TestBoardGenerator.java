package com.stretchy;

import java.io.InputStream;
import java.util.ArrayDeque;

/**
 * Created by ian on 3/25/16.
 */
public class TestBoardGenerator {
    private ArrayDeque<Character> InstructionQueue;
    private char[] terrain;
    int boardY, boardX, queueSize;
    public TestBoardGenerator(int rows, int cols, int queuesize)
    {
        boardX = cols;
        boardY = rows;
        queueSize = queuesize;
        InstructionQueue = new ArrayDeque<>(queuesize);
        for (int i = 0; i< queuesize; i++)
        {
            if((int)(Math.random() * 2) == 0)
                InstructionQueue.add('D');
            else
                InstructionQueue.add('R');
        }
        String base = "";
        for (int i = 0; i < rows*cols; i++)
        {
            if((int)(Math.random() * 3) == 0)
                base = base.concat("X");
            else
                base = base.concat(".");
        }
        terrain = base.toCharArray();
        ArrayDeque<Character> testQueue = InstructionQueue.clone();
        terrain[0] = '.';
        for (int row = 0, col = 0; row < rows && col < cols; )
        {
            if(testQueue.size() == 0)
                testQueue = InstructionQueue.clone();
            char move = testQueue.pop();
            if(move == 'D')
                row++;
            else
                col++;
            if(row < rows && col < cols)
                terrain[row * cols + col] = '.';
        }

    }

    public Gameboard getBoard()
    {
        return new Gameboard(String.valueOf(terrain), boardX, boardY, InstructionQueue.size()+3, 2, 1);
    }
}
