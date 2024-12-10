package org.example.backend.services;

import org.example.backend.data.PointsRepository;
import org.example.backend.data.entity.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointsService {

    private final PointsRepository pointsRepository;

    @Autowired
    public PointsService(PointsRepository pointsRepository) {
        this.pointsRepository = pointsRepository;
    }

    public void savePoint(Point point) {
        pointsRepository.save(point);
    }

    public List<Point> getAllPoints() {
        return pointsRepository.findAll();
    }

    public void deleteAllPoints() {
        pointsRepository.deleteAll();
    }
}
