package org.example.backend.controlers;

import org.example.backend.common.exceptions.ValidException;
import org.example.backend.controlers.ResponseDAO.PointResponse;
import org.example.backend.data.entity.Point;
import org.example.backend.services.CalculateResultService;
import org.example.backend.services.ParsParamsService;
import org.example.backend.services.PointsService;
import org.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private final CalculateResultService calculateResultService;
    private final ParsParamsService parsParamsService;
    private final UserService userService;
    private final PointsService pointsService;

    @Autowired
    public GameController(
            CalculateResultService calculateResultService,
            ParsParamsService parsParamsService,
            UserService userService,
            PointsService pointsService) {
        this.calculateResultService = calculateResultService;
        this.parsParamsService = parsParamsService;
        this.userService = userService;
        this.pointsService = pointsService;
    }

    @PostMapping("/check")
    public PointResponse check(@RequestBody Map<String, String> requestBody) {
        String x = requestBody.get("x");
        String y = requestBody.get("y");
        String z = requestBody.get("z");
        String r = requestBody.get("r");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();

        Point point = null;
        long startTime = System.currentTimeMillis();
        try {
            parsParamsService.pars(x, y, z, r);
            parsParamsService.validParams();
            boolean resultOfCalc = calculateResultService.calculate(parsParamsService.getX(), parsParamsService.getY(), parsParamsService.getZ(), parsParamsService.getR());
            point = new Point(userService.getUserIdByUsername(user), parsParamsService.getX(), parsParamsService.getY(), parsParamsService.getZ(), parsParamsService.getR(), resultOfCalc, System.currentTimeMillis() - startTime);

            System.out.println("ok: " + point);
            pointsService.savePoint(point);
            return PointResponse.from(point);
        } catch (ValidException e) {
            System.out.println("err: " + point);
            return PointResponse.from(e.getMessage());
        }
    }

    @GetMapping("/get_all_points")
    public List<PointResponse> getAllPoints() {
        List<PointResponse> points = new ArrayList<>();
        List<Point> pointList = pointsService.getAllPoints();
        System.out.println(pointList);
        for (Point point : pointList) {
            points.add(PointResponse.from(point));
        }
        return points;
    }
}

