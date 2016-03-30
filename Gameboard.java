package com.stretchy;

import java.util.ArrayDeque;

/**
 * Created by ian on 3/19/16.
 * Purely a container class for holding gameboard information
 */
public class Gameboard
{
    private String Terrain;
    private int MaxInstructions;
    private int MinInstructions;
    private int BoardX;
    private int BoardY;
    private int Level;
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
        InstructionQueue = instructionQueue;
    }
}
