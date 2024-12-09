package org.example.backend.controlers;


import org.example.backend.services.PointsService;
import org.example.backend.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/game/admin")
@CrossOrigin(origins = "http://localhost:5173/")
public class AdminGameController {
    private final UserService userService;
    private final PointsService pointsService;

    public AdminGameController(UserService userService, PointsService pointsService) {
        this.userService = userService;
        this.pointsService = pointsService;
    }

    @DeleteMapping("/delete_all_points")
    public boolean deleteAllPoints(@RequestBody Map<String, String> requestBody) {
        String login = requestBody.get("login");
        if (userService.getUserStatusByUsername(login) != 1) return false;
        pointsService.deleteAllPoints();
        return true;
    }
}
