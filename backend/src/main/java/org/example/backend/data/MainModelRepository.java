package org.example.backend.data;

import org.example.backend.data.entity.MainModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainModelRepository extends JpaRepository<MainModel, Long> {
    MainModel findByMaster(int master);
}
