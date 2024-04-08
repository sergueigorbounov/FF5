package com.example.ff.model;

import java.util.Random;

public class Grid {
    private final Cell[][] cells;
    private final Random random = new Random();
    private final double spreadProbability;

    public Grid(int rows, int columns, double spreadProbability) {
        this.cells = new Cell[rows][columns];
        this.spreadProbability = spreadProbability;
        initializeCells();
    }

    private void initializeCells() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(false);
            }
        }
        int initialFireRow = random.nextInt(cells.length);
        int initialFireCol = random.nextInt(cells[0].length);
        cells[initialFireRow][initialFireCol].setBurning(true);
    }

    public void spreadFire() {
        Cell[][] nextState = cloneCells();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Cell currentCell = cells[i][j];
                if (currentCell.isBurning()) {
                    trySpreadTo(i - 1, j, nextState);
                    trySpreadTo(i + 1, j, nextState);
                    trySpreadTo(i, j - 1, nextState);
                    trySpreadTo(i, j + 1, nextState);
                    nextState[i][j].setBurning(false);
                    nextState[i][j].setBurned(true);
                    nextState[i][j].setTree(false);
                }
            }
        }

        setCells(nextState);
    }

    private void trySpreadTo(int row, int col, Cell[][] nextState) {
        if (row >= 0 && row < cells.length && col >= 0 && col < cells[0].length && !nextState[row][col].isBurned()) {
            if (random.nextDouble() < spreadProbability) {
                nextState[row][col].setBurning(true);
                nextState[row][col].setBurned(false);
                nextState[row][col].setTree(false);
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getHeight() {
        return cells.length;
    }

    public int getWidth() {
        return cells[0].length;
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public void setCells(Cell[][] newCells) {
        for (int i = 0; i < newCells.length; i++) {
            System.arraycopy(newCells[i], 0, cells[i], 0, newCells[i].length);
        }
    }

    public Cell[][] cloneCells() {
        Cell[][] clone = new Cell[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Cell original = cells[i][j];
                clone[i][j] = new Cell(original.isBurning(), original.isBurned(), original.isTree());
            }
        }
        return clone;
    }
}
