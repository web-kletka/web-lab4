package org.example.backend.services;


import org.springframework.stereotype.Service;

@Service
public class CalculateResultService {
    public String calculate(String text){
        return text + " abra kadabra";
    }
}
