package com.example.webill.controller;

import com.example.webill.models.Constants;
import com.example.webill.models.Maps;
import com.example.webill.service.MapsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class MapsController {

    @Autowired
    private MapsService mapService;

    @Autowired
    private Constants constants;

    @PostMapping(value = "/mapsData")
    public ResponseEntity<Maps> getExpenseLocation(){
        
        Maps mapsResponse = mapService.getExpenseLocation();

        return new ResponseEntity<>(mapsResponse,HttpStatus.OK);
    }
    
}
