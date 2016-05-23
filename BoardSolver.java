package com.stretchy;


import com.stretchy.interfaces.ISolverVisitor;
import com.stretchy.interfaces.ISolverVisitorAcceptor;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ian on 3/19/16.
 */
public class BoardSolver {
    Gameboard Board;
    private ArrayDeque<Character> MovementQueue;
    private char[] Terrain;
    private boolean Solved;

    private long SolveTime;
    private int GuessNumber;
    protected OutputHandler outputHandler;
    protected volatile AtomicBoolean stopWork;
    //VisitorAcceptorThreadwrapper [] threads;

    public BoardSolver(Gameboard gb) {
        Board = gb;
        Terrain = Board.getTerrain().toCharArray();
        MovementQueue = new ArrayDeque<>(Board.getMaxInstructions());
        stopWork = new AtomicBoolean(false);
    }

    public BoardSolver(BoardSolver cp) {
        Board = cp.Board;
    }

    public void setOutputHandler(OutputHandler oh) {
        outputHandler = oh;
    }

    private void ShowPath() {
        char[] sub_terrain = Terrain.clone();
        int robot_x = 0, robot_y = 0;
        ArrayDeque<Character> checkQueue = MovementQueue.clone();
        char move;
        while (!checkQueue.isEmpty()) {
            move = checkQueue.pop();
            if (move == 'D') {
                robot_y++;
            } else
                robot_x++;
            sub_terrain[robot_y * Board.getBoardX() + robot_x] = '#';
        }
        System.out.print("\n\n");
        System.out.println(MovementQueue);
        int sub_board_size = Board.getMaxInstructions();
        for (int row = 0; row < sub_board_size; row++) {
            for (int col = 0; col < sub_board_size; col++) {
                System.out.print(sub_terrain[row * Board.getBoardX() + col] + " ");
            }
            System.out.print("\n");
        }
    }

    public void DisplaySubBoard() {
        int subboardsize = Board.getMaxInstructions();
        for (int row = 0; row < subboardsize; row++) {
            for (int col = 0; col < subboardsize; col++) {
                System.out.print(Terrain[row * Board.getBoardX() + col] + " ");
            }
            System.out.print("\n");
        }
    }

    public void DispayBoard() {
        System.out.print("\n\n");
        int numchars = Board.getBoardX() * Board.getBoardY();
        for (int i = 1; i < numchars + 1; i++) {
            System.out.print(Board.getTerrain().charAt(i - 1));
            System.out.print(" ");
            if ((i % Board.getBoardX()) == 0)
                System.out.print("\n");
        }
    }

    public void OutputSolutionPath() {
        MovementQueue = Board.getInstructionQueue().clone();
        char[] pathTerrain = Terrain.clone();
        ArrayDeque<Character> solutionPath = MovementQueue.clone();
        int row, col;
        char move;
        for (row = 0, col = 0; col < Board.getBoardX() || row < Board.getBoardY(); ) {
            if (solutionPath.size() == 0)
                solutionPath = MovementQueue.clone();
            move = solutionPath.pop();
            if (move == 'R')
                col++;
            else if (move == 'D')
                row++;
            if (row < Board.getBoardY() && col < Board.getBoardX())
                pathTerrain[row * Board.getBoardX() + col] = '#';
        }
        for (row = 0; row < Board.getBoardY(); row++) {
            for (col = 0; col < Board.getBoardX(); col++) {
                System.out.print(pathTerrain[row * Board.getBoardX() + col] + " ");
            }
            System.out.print("\n");
        }
    }

    public String GetPathFormatted() {
        String build = "";
        for (char c : MovementQueue) {
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


}

