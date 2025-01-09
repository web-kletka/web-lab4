package org.example.backend.controlers;


import org.example.backend.services.PointsService;
import org.example.backend.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/game/admin")
public class AdminGameController {
    private final UserService userService;
    private final PointsService pointsService;

    public AdminGameController(UserService userService, PointsService pointsService) {
        this.userService = userService;
        this.pointsService = pointsService;
    }

    @DeleteMapping("/delete_all_points")
    public boolean deleteAllPoints() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        if (userService.getUserStatusByUsername(user) != 1) return false;
        pointsService.deleteAllPoints();
        return true;
    }
}
