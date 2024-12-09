package org.example.backend.data;

import org.example.backend.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
    @Query("SELECT u.id FROM User u WHERE u.login = :login")
    int findIdByLogin(@Param("login") String login);
    @Query("SELECT u.status FROM User u WHERE u.login = :login")
    int findStatusByLogin(@Param("login") String login);
}

