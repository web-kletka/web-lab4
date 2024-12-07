package org.example.backend.controlers;

import org.example.backend.services.CalculateResultService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game/")
@CrossOrigin(origins = "http://localhost:5173/")
public class GameController {
    private final CalculateResultService calculateResultService;

    public GameController(CalculateResultService calculateResultService) {
        this.calculateResultService = calculateResultService;
    }

    @PostMapping()
    public String index(String a) {
        return calculateResultService.calculate(a);
    }

    @GetMapping
    public String getAllTasks() {
        return "GEET RABOTAET";
    }
}
