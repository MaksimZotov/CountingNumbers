package model;

import java.util.Random;

public class Field {
    private Cell[][] cells;
    private Random random = new Random();

    public Field(int height, int width) {
        cells = new Cell[2 * height][];
        for (int i = 0; i < 2 * height; i++) {
            cells[i] = new Cell[width];
            for (int j = 0; j < width; j++)
                cells[i][j] = new Cell(i, j, random.nextInt(5) + 5);
        }
    }

    public Cell getCell (int x, int y) {
        return cells[x][y];
    }
}
