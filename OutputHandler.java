package com.stretchy;

import com.stretchy.interfaces.Displayable;

import java.io.IOException;
import java.util.*;

/**
 * Created by ian on 5/15/16.
 */
public class OutputHandler {

    LinkedList<Displayable> _displayObjects = new LinkedList<>();

    public OutputHandler()
    {

    }

    public void registerDisplayObject(Displayable d)
    {
        _displayObjects.add(d);
    }

    public void registerDisplayObjects(Collection<Displayable> c)
    {
        _displayObjects.addAll(c);
    }

    protected void WriteOutput()
    {
        clear();
        for( Displayable d : _displayObjects)
        {
            System.out.println(d.display());
        }
    }

    public void StartOutput()
    {
        WriteOutput();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                WriteOutput();

            }
        }, 1000, 2000);
    }

    private void clear()
    {
        String newlines = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
        System.out.print(newlines);
    }
}
