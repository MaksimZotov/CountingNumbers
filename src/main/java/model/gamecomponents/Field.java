package model.gamecomponents;

import java.util.Random;

public class Field implements fieldStateForClient {

    private Cell[][] cells;
    private Random random = new Random();
    private int height;
    private int width;

    public Field(int height, int width) {
        this.height = height;
        this.width = width;
        cells = new Cell[height][];
        for (int i = 0; i < height; i++) {
            cells[i] = new Cell[width];
            for (int j = 0; j < width; j++)
                cells[i][j] = new Cell(i, j, random.nextInt(5) + 5);
        }
    }

    public Cell getCell(int y, int x) {
        return cells[y][x];
    }
}
