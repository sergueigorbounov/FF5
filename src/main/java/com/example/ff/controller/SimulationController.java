package com.example.ff.controller;

import com.example.ff.model.Cell;
import com.example.ff.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // This indicates that this class is a controller with RESTful endpoints.
public class SimulationController {
    private final SimulationService simulationService; // A service layer that handles the logic of simulation.

    @Autowired // This annotation tells Spring to inject the SimulationService instance here.
    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService; // The simulation service is set through constructor injection.
    }

    @GetMapping("/simulation/step") // Mapping for the endpoint that will trigger a simulation step.
    public ResponseEntity<Cell[][]> simulateStep() {
        simulationService.simulateStep(); // This method call should perform the simulation step logic.
        // Assuming there is a method getGridState that returns the current state of the grid.
        return ResponseEntity.ok(simulationService.getGridState());
    }
    @PostMapping("/simulation/start")
    public ResponseEntity<Cell[][]> startSimulation() {
        // Assuming you have a service to handle the simulation logic
        Cell[][] gridState = simulationService.startAndRetrieveGrid();
        return ResponseEntity.ok(gridState);
    }

    // If you need to start the simulation, you might want to add an endpoint to initialize or reset it.
    // You would also need endpoints for starting and stopping the simulation, if necessary.
}
