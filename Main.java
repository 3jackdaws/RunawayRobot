package com.stretchy;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {

    public static void main(String[] args)
    {
        int last_level = 0;
        for (;;) {
            WebpageParser robot = new WebpageParser("http://www.hacker.org/runaway/index.php?name=stretchyvizsla&password=xdp-XAM-EPK-Y2J");
            System.out.println("Board size: " + robot.GetBoard().getBoardX() + " by " + robot.GetBoard().getBoardY());
            System.out.println("Min: " + robot.GetBoard().getMinInstructions() + " Max: " + robot.GetBoard().getMaxInstructions());
            //Gameboard manual = new Gameboard(".XXX..XXX.XXX..X", 4,4,3,3,1);

            if(last_level == robot.GetBoard().getLevel()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            last_level = robot.GetBoard().getLevel();
            BoardSolver RobotSolver = new BoardSolver(robot.GetBoard());
            //TestBoardGenerator board1 = new TestBoardGenerator(80, 80, 28);
            //BoardSolver RobotSolver = new BoardSolver(board1.getBoard());
            RobotSolver.DispayBoard();
            RobotSolver.AcceptVisitor(new PathifyGameboard());
            RobotSolver.DispayBoard();
            RobotSolver.AcceptVisitor(new PathifyGameboard());
            RobotSolver.DispayBoard();

            RobotSolver.DisplaySubBoard();
            //RobotSolver.AcceptVisitor(new LinearLookaheadSolve());
            RobotSolver.AcceptVisitor(new RecursiveSquiggleSolve());
            //RobotSolver.AcceptVisitor(new SubsectionTendrilSolve());
            //RobotSolver.OutputSolutionPath();
            RobotSolver.Submit();
        }
    }
}
