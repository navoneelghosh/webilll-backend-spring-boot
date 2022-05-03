package com.example.webill.controller;

import com.example.webill.models.Constants;
import com.example.webill.models.Maps;
import com.example.webill.models.MapsRequestModel;
import com.example.webill.models.Users;
import com.example.webill.service.MapsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class MapsController {

    @Autowired
    private MapsService mapsService;

    @Autowired
    private Constants constants;

    @PostMapping(value = "/mapsData")
    public ResponseEntity<?> getExpenseLocation(@RequestBody MapsRequestModel user){
        String username = user.getUsername();
        String yearParam = "%" + user.getYear();
        System.out.println("USERNAME: "+username);
        List<Maps> mapsResponse = mapsService.getExpenseLocation(username, yearParam);

        return new ResponseEntity<>(mapsResponse,HttpStatus.OK);
    }
    
}
