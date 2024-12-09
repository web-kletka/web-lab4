package org.example.backend.services;

import org.example.backend.data.UserRepository;
import org.example.backend.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUserByUsername(String login) {
        return userRepository.findByLogin(login);
    }

    public boolean getUserExists(String login) {
        return userRepository.findByLogin(login) != null;
    }

    public int getUserIdByUsername(String login) {
        return userRepository.findIdByLogin(login);
    }

    public int getUserStatusByUsername(String login) {
        return userRepository.findStatusByLogin(login);
    }

}

