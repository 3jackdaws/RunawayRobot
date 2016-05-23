/*@author Ian Murphy

 */

package com.stretchy;

import com.stretchy.interfaces.Displayable;

public class Main {


    public static void main(String[] args)
    {
        int last_level = 0;
        for (;;) {

            WebpageParser robot = new WebpageParser("http://www.hacker.org/runaway/index.php?name=stretchyvizsla&password=xdp-XAM-EPK-Y2J");
            System.out.println("Board size: " + robot.GetBoard().getBoardX() + " by " + robot.GetBoard().getBoardY());
            System.out.println("Min: " + robot.GetBoard().getMinInstructions() + " Max: " + robot.GetBoard().getMaxInstructions());

            if(last_level == robot.GetBoard().getLevel()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //TestBoardGenerator board1 = new TestBoardGenerator(200, 200, 30);

            Gameboard gameboard = robot.GetBoard();
            //BoardSolver bs = new BoardSolver(gameboard);
            gameboard.AcceptVisitor(new PathifyBacktrack());
           // gameboard.DispayBoard();
            OutputHandler output = new OutputHandler();
            SolverThreadCoordinator coordinator = new SolverThreadCoordinator(gameboard);
//
//
//
            SolverThreadFactory threadFactory = new SolverThreadFactory();
            RecursiveSquiggleSolve algorithm = new RecursiveSquiggleSolve();
            algorithm.SolverAction(gameboard);
            threadFactory.addAlgorithm(algorithm);
            threadFactory.addCoordinator(coordinator);
            threadFactory.addGameboard(gameboard);
            threadFactory.addOutputHandler(output);
            threadFactory.addDirection("Front");

            coordinator.registerThreads(threadFactory.createThreads(3));
            threadFactory.addDirection("Back");
            coordinator.registerThreads(threadFactory.createThreads(3));
            String level = String.valueOf(gameboard.getLevel());
            output.registerDisplayObject(() -> "Level: " + level);
            output.registerDisplayObject(new Displayable() {
                String _out = "Running";
                @Override
                public String display() {
                    if(_out.length() > 10)
                        _out = "Running";
                    //_out+=".";
                    return "\033[32m" + (_out+=".") + "\033[0m";
                }
            });
            output.registerDisplayObject(coordinator);
            coordinator.registerOutputHandler(output);
            //output.StartOutput();
            coordinator.start();
            output.stopOutput();
        }



    }
}
