/*@author Ian Murphy

 */

package com.stretchy;

import com.stretchy.interfaces.Displayable;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ThreadFactory;

public class Main {

    public static void main(String[] args)
    {
//        int last_level = 0;
//        for (;;) {
//
//            WebpageParser robot = new WebpageParser("http://www.hacker.org/runaway/index.php?name=stretchyvizsla&password=xdp-XAM-EPK-Y2J");
//            System.out.println("Board size: " + robot.GetBoard().getBoardX() + " by " + robot.GetBoard().getBoardY());
//            System.out.println("Min: " + robot.GetBoard().getMinInstructions() + " Max: " + robot.GetBoard().getMaxInstructions());
//            //Gameboard manual = new Gameboard(".XXX..XXX.XXX..X", 4,4,3,3,1);
//
//            if(last_level == robot.GetBoard().getLevel()) {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            last_level = robot.GetBoard().getLevel();
//            //TestBoardGenerator board1 = new TestBoardGenerator(200, 200, 30);
//            Gameboard board = robot.GetBoard();
//            OutputHandler consoleOutput = new OutputHandler(board);
//
//            //BoardSolver RobotSolver = new BoardSolver(robot.GetBoard());
//
//            BoardSolver RobotSolver = new BoardSolver(board);
//            //RobotSolver.DispayBoard();
//            RobotSolver.AcceptVisitor(new PathifyGameboard());
//
//            RobotSolver.AcceptVisitor(new PathifyGameboard());
//            RobotSolver.setOutputHandler(consoleOutput);
//            //consoleOutput.StartOutput();
//            //RobotSolver.AcceptVisitorThreaded(new RecursiveSquiggleSolve(), 7);
//            RobotSolver.AcceptVisitor(new RecursiveSquiggleSolve());
//
//
//
//
//
//            //RobotSolver.AcceptVisitor(new RecursiveSquiggleSolve());
//            //RobotSolver.AcceptVisitor(new SubsectionTendrilSolve());
//            //RobotSolver.OutputSolutionPath();
//
//
//            //System.out.println(board.getInstructionQueue());
//            RobotSolver.Submit();
//        }


        OutputHandler out = new OutputHandler();
        out.registerDisplayObject(new Displayable() {
            String cat = "hi";
            @Override
            public String display() {
                return cat += cat;
            }
        });

        out.StartOutput();
    }
}
