package com.example.webill.controller;

import com.example.webill.models.CustomResponse;
import com.example.webill.models.OCRBill;
import com.example.webill.models.VeryfiOCRResponse;
import com.example.webill.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
