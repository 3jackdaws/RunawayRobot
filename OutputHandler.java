package com.stretchy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ian on 5/15/16.
 */
public class OutputHandler {


    public enum Direction{
        SMALLEST,
        LARGEST
    }


    protected ArrayList<String> qDesc;
    protected LinkedList<Integer> qUnsov;
    protected int min;
    protected int max;
    protected Gameboard _board;
    protected boolean hasChanged;
    public OutputHandler(Gameboard board)
    {
        _board = board;
        min = _board.getMinInstructions();
        max = _board.getMaxInstructions();
        qDesc = new ArrayList<>(max-min);
        //qDesc.forEach(s->s="No Action");
        qUnsov = new LinkedList<>();
        for (int i = min; i<=max; i++)
        {
            qUnsov.add(i);
            qDesc.add("No Action");
        }
    }

    protected void WriteOutput()
    {
        for (int i = 0; i<50; i++)
            System.out.print("\n");
        System.out.println("Level: " + _board.getLevel());
        for (int i = 0; i<=max-min; i++)
        {
            System.out.println("Queue Size [ " + (i+min) +" ] - " + qDesc.get(i));
        }
        if(_board.getInstructionQueue().size() > 0 && qUnsov.size() == 0)
            System.out.println(_board.getInstructionQueue());
    }

    public int getNextQueue(Direction d)
    {
        synchronized (this){
            if(qUnsov.size() == 0) return -1;
            else if(d == Direction.LARGEST)
            {
                return qUnsov.removeLast();
            }
            else
            {
                return qUnsov.removeFirst();
            }
        }
    }

    public void updateStatus(int queueNumber, String status)
    {
        synchronized (this) {
            qDesc.set(queueNumber - min, status);
            hasChanged = true;
        }
    }

    public void StartOutput()
    {
        WriteOutput();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(hasChanged) {
                    WriteOutput();
                    hasChanged = false;
                }
            }
        }, 1000, 2000);
    }
}
