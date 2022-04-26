package com.example.webill.controller;

import com.example.webill.models.*;
import com.example.webill.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping(value = "/processBill")
    public ResponseEntity<?> processBill(@RequestBody OCRBill ocrBill){
        try{
            VeryfiOCRResponse response = billService.processBill(ocrBill);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage());
            return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getBillsForUser")
    public ResponseEntity<?> getBillsForUser(@RequestParam(name = "username")String username){
        if(username==null || username.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<BillModel> bills = new ArrayList<>();
        try{
            bills = billService.getBillsForUser(username);
        }catch (Exception e){
            bills = new ArrayList<>();
        }

        return new ResponseEntity<>(bills,HttpStatus.OK);
    }

    @PostMapping(value = "/getBillsForUserByLoc")
    public ResponseEntity<?> getBillsForUserByLoc(@RequestBody BillsByLocRequest billsByLocRequest){
        String username = billsByLocRequest.getUsername();
        String latitude = Double.toString(billsByLocRequest.getLatitude());
        String longitude = Double.toString(billsByLocRequest.getLongitude());
        String dateString = billsByLocRequest.getDateString();
        if(username==null || username.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<Bills_Prod> bills = new ArrayList<>();
        try{
            bills = billService.getBillsForUserByLoc(username,latitude,longitude,dateString);
        }catch (Exception e){
            bills = new ArrayList<>();
        }

        return new ResponseEntity<>(bills,HttpStatus.OK);
    }
}
