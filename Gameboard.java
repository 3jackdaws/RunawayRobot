package com.stretchy;

import com.stretchy.interfaces.ISolverVisitor;
import com.stretchy.interfaces.ISolverVisitorAcceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayDeque;

/**
 * Created by ian on 3/19/16.
 * Purely a container class for holding gameboard information
 */
public class Gameboard implements ISolverVisitorAcceptor
{
    private String Terrain;
    private int MaxInstructions;
    private int MinInstructions;
    private int BoardX;
    private int BoardY;
    private int Level;
    private boolean _lock;
    private ArrayDeque<Character> InstructionQueue;
    public Gameboard()
    {

    }

    public Gameboard(String terrain, int bX, int bY, int maxI, int minI, int level)
    {
        Terrain = terrain;
        BoardX = bX;
        BoardY = bY;
        MaxInstructions = maxI;
        MinInstructions = minI;
        Level = level;
        InstructionQueue = new ArrayDeque<>(MaxInstructions);
    }

    @Override
    protected Gameboard clone(){
        return new Gameboard(this.Terrain, BoardX, BoardY, MaxInstructions, MinInstructions, Level);
    }

    public int getMaxInstructions() {
        return MaxInstructions;
    }

    public int getMinInstructions() {
        return MinInstructions;
    }

    public int getBoardX() {
        return BoardX;
    }

    public int getBoardY() {
        return BoardY;
    }

    public String getTerrain()
    {
        return Terrain;
    }

    public int getLevel() {
        return Level;
    }

    public void setTerrain(String terrain) {
        Terrain = terrain;
    }

    public ArrayDeque<Character> getInstructionQueue() {
        return InstructionQueue;
    }

    public void setInstructionQueue(ArrayDeque<Character> instructionQueue) {
        if(!_lock)
            InstructionQueue = instructionQueue;
    }

    public boolean bombAt(int x, int y)
    {
        return Terrain.toCharArray()[y * BoardX + x] == 'X';
    }
    public void setCharAt(int x, int y, char c){
        char [] t = Terrain.toCharArray();
        t[y * BoardX + x] = c;
        Terrain = String.valueOf(t);
    }

    public void DispayBoard() {
        System.out.print("\n\n");
        int numchars = BoardX * BoardY;
        for (int i = 1; i < numchars + 1; i++) {
            System.out.print(Terrain.charAt(i - 1));
            System.out.print(" ");
            if ((i % BoardX) == 0)
                System.out.print("\n");
        }
    }

    @Override
    public void AcceptVisitor(ISolverVisitor visitor) {
        visitor.SolverAction(this);
    }

    public String GetPathFormatted() {
        String build = "";
        for (char c : InstructionQueue) {
            build = build.concat(String.valueOf(c));
        }
        return build;
    }

    public void Submit() {
        System.out.println("Submitting path data.");
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        try {
            url = new URL("http://www.hacker.org/runaway/index.php?name=stretchyvizsla&password=xdp-XAM-EPK-Y2J" + "&path=" + GetPathFormatted());
            is = url.openStream();  // throws an IOException
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void Lock()
    {
        _lock = true;
    }
}
