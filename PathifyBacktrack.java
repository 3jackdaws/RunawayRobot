package com.stretchy;

import com.stretchy.interfaces.ISolverVisitor;

/**
 * Created by Ian Murphy on 5/22/2016.
 */
public class PathifyBacktrack implements ISolverVisitor {
    Gameboard _board;
    char [] _terrain;
    int _board_x;
    int _dim;
    long _recursion = 0;
    int _x,_y;
    @Override
    public void SolverAction(Gameboard gb) {
        _board = gb;
        _terrain = _board.getTerrain().toCharArray();
        _board_x = _board.getBoardX();
        _dim = _board.getBoardX();
        _pathify();
    }

    @Override
    public ISolverVisitor clone() {
        return null;
    }

    protected void _pathify()
    {

        try {
            _p_recurse(0,0);
        } catch (BoardSolvedException e) {
            System.out.println("Done");

        }
        DispayBoard();
        FinalizePath();
        DispayBoard();
        _board.setTerrain(String.valueOf(_terrain));
    }

    protected boolean _p_recurse(int x, int y) throws BoardSolvedException {
        if(_recursion % 100000000 == 0) {
            _x = x;
            _y = y;
            //DispayBoard();
        }
        _recursion++;
        if(x == _dim || y == _dim){
            //_terrain[y*_board_x + x] = '*';
            return true;
        }
        else if(_terrain[y*_board_x + x] == 'X' || _terrain[y*_board_x + x] == '#') return false;
        else if(_terrain[y*_board_x + x] == '*') return true;
        else {
            boolean down, right;
            _terrain[y*_board_x + x] = '*';
            right = _p_recurse(x+1,y);
            down = _p_recurse(x,y+1);
            if(!right && !down) {
                _terrain[y * _board_x + x] = '#';
                return false;
            }
            return true;
        }
    }

    public void DispayBoard() {
        char out;
        System.out.print("\n\n");
        int numchars = _dim * _dim;
        for (int i = 1; i < numchars + 1; i++) {
            out = _terrain[i - 1];
            if(i == (_y * _dim + _x -1))
            {
                System.out.print("\033[36;1m" + out + "\033[0m");
            }
            else if(out == 'X')
                System.out.print("\033[31;1m" + out + "\033[0m");
            else if(out == '#')
                System.out.print("\033[33m" + out + "\033[0m");
            else if(out == '*')
                System.out.print("\033[32m" + out + "\033[0m");
            else
                System.out.print(out);
            System.out.print(" ");
            if ((i % _dim) == 0)
                System.out.print("\n");
        }
    }

    private void FinalizePath()
    {
        int numchars = _dim * _dim;
        for (int i = 1; i < numchars + 1; i++) {
            if(_terrain[i - 1] != '*')
                _terrain[i - 1] = 'X';
            else _terrain[i - 1] = '.';

        }
    }

}
