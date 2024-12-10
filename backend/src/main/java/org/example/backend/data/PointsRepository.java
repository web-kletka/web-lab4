package org.example.backend.data;

import org.example.backend.data.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointsRepository extends JpaRepository<Point, Long> {
    Point findByMaster(int master);
    void deleteAll();
}
