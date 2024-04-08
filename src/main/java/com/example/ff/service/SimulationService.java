package com.example.ff.service;

import com.example.ff.model.Cell;
import com.example.ff.model.Grid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SimulationService {

    private final Grid grid;
    private final Random random = new Random();
    //private final double spreadProbability;

    public SimulationService(@Value("${simulation.height}") int height,
                             @Value("${simulation.width}") int width,
                             @Value("${simulation.spreadProbability}") double spreadProbability) {
        //this.spreadProbability = spreadProbability;
        this.grid = new Grid(height, width, spreadProbability);
        initializeGridWithFire();
    }

    private void initializeGridWithFire() {
        // Initially, all cells are not burning
        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                grid.getCell(i, j).setBurning(false);
                grid.getCell(i, j).setBurned(false);
                grid.getCell(i, j).setTree(true);
            }
        }
        // Ignite a few cells at random positions as the starting point of the fire
        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                if (Math.random() < 0.1) { // Let's say 10% chance to start burning
                    grid.getCell(i, j).setBurning(true);
                }
            }
        }
    }

    public void simulateStep() {
        // This method needs to update the burning and burned state of each cell
        // The provided logic has the basic structure but should correctly handle burning and burned state transitions
        spreadFire();
    }

    private void spreadFire() {
        Cell[][] newCells = grid.cloneCells();

        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                Cell currentCell = grid.getCell(i, j);
                if (currentCell.isBurning()) {
                    // Directly setting the cell to burned; ensure logic is correct for your needs
                    newCells[i][j].setBurning(false);
                    newCells[i][j].setBurned(true);
                    newCells[i][j].setTree(false);
                    // Spread fire
                    spreadFireToAdjacentCells(i, j, newCells);
                }
            }
        }

        grid.setCells(newCells);
    }

    private void spreadFireToAdjacentCells(int x, int y, Cell[][] newCells) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];
            // Add fire spreading logic similar to what's already provided
        }
    }

    public Cell[][] getGridState() {
        return grid.getCells();
    }

    // Resets the grid to its initial state with some cells on fire and returns the state
    public Cell[][] startAndRetrieveGrid() {
        initializeGridWithFire(); // Reinitialize the grid with fire
        return getGridState(); // Return the grid's initial state
    }
}
