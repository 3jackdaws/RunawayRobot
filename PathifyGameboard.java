package com.stretchy;

import java.nio.file.Path;

/**
 * Created by ian on 3/25/16.
 */
public class PathifyGameboard implements ISolverVisitor{

    private Gameboard Board;
    private char[] Terrain;
    @Override
    public void SolverAction(Gameboard gb) {
        Board = gb;
        Terrain = Board.getTerrain().toCharArray();
        Pathify();
        //Pathify();
        Board.setTerrain(String.valueOf(Terrain));
    }

    private void Pathify()
    {
        int sub_size = Board.getBoardX()-1;
        int row, col, lastx;
        for (col = 0, lastx = 0; col < sub_size; col++)
        {
            for(row = 0; row< sub_size; row++) {
                if (col > 0 && row > 0) {
                    if (Terrain[(row - 1) * Board.getBoardX() + col] == 'X' && Terrain[row * Board.getBoardX() + col - 1] == 'X') {
                        Terrain[row * Board.getBoardX() + col] = 'X';

                    }
                    if(row < Board.getBoardY() - 1 && col < Board.getBoardX())
                    {
                        if(Terrain[(row + 1) * Board.getBoardX() + col] == 'X' && Terrain[row * Board.getBoardX() + col + 1] == 'X') {
                            Terrain[row * Board.getBoardX() + col] = 'X';
                        }
                    }

                } else if (col > 0 && row == 0) {
                    if (Terrain[row * Board.getBoardX() + col - 1] == 'X') {
                        Terrain[row * Board.getBoardX() + col] = 'X';
                    }
                } else if (col == 0 && row > 0) {
                    if (Terrain[(row - 1) * Board.getBoardX() + col] == 'X') {
                        Terrain[row * Board.getBoardX() + col] = 'X';
                    }
                } //else if()
            }
        }

        for (row = sub_size; row > 0; row--)
        {
            for(col = sub_size; col > 0; col--) {
                if (col > 0 && row > 0) {
                    if (Terrain[(row - 1) * Board.getBoardX() + col] == 'X' && Terrain[row * Board.getBoardX() + col - 1] == 'X') {
                        Terrain[row * Board.getBoardX() + col] = 'X';

                    }
                    if(row < Board.getBoardY() - 1 && col < Board.getBoardX())
                    {
                        if(Terrain[(row + 1) * Board.getBoardX() + col] == 'X' && Terrain[row * Board.getBoardX() + col + 1] == 'X') {
                            Terrain[row * Board.getBoardX() + col] = 'X';
                        }
                    }

                } else if (col > 0 && row == 0) {
                    if (Terrain[row * Board.getBoardX() + col - 1] == 'X') {
                        Terrain[row * Board.getBoardX() + col] = 'X';
                    }
                } else if (col == 0 && row > 0) {
                    if (Terrain[(row - 1) * Board.getBoardX() + col] == 'X') {
                        Terrain[row * Board.getBoardX() + col] = 'X';
                    }
                }

            }
        }
        Terrain[(Board.getBoardY()-1) * Board.getBoardX() + 0] = 'X';
        for (row = Board.getBoardY()-1, col = 1; col < Board.getBoardX()-1; col++)
        {
            if (Terrain[(row - 1) * Board.getBoardX() + col] == 'X' && Terrain[(row) * Board.getBoardX() + col-1] == 'X') {
                Terrain[row * Board.getBoardX() + col] = 'X';
            }
        }


    }
}
