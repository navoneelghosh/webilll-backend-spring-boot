package com.example.webill.service;

import com.example.webill.models.Constants;
import com.example.webill.models.Maps;
import com.example.webill.repository.MapsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MapsService {

    @Autowired
    private MapsRepository mapsRepository;

    @Autowired
    private Constants constants;

    public List<Maps> getExpenseLocation(String username){
        return mapsRepository.getExpenseLocation(username);
    }
    
}
