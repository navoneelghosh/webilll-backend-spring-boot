package com.example.webill.service;

import com.example.webill.models.Constants;
import com.example.webill.models.Maps;
import com.example.webill.repository.MapsRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class MapsService {

    @Autowired
    private MapsRepository mapsRepository;

    @Autowired
    private Constants constants;

    public Maps getExpenseLocation(){
        return mapsRepository.getExpenseLocation();
    }
    
}
