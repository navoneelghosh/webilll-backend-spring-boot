package com.example.webill.controller;

import com.example.webill.models.*;
import com.example.webill.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping(value = "/processBill2",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> processBill2(@RequestBody MultipartFile file){
        try{
            billService.processBillImage(file);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage());
            return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
        }
    }

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
        List<BillModel> bills = new ArrayList<>();
        try{
            bills = billService.getBillsForUserByLoc(username,latitude,longitude,dateString);
        }catch (Exception e){
            bills = new ArrayList<>();
        }

        return new ResponseEntity<>(bills,HttpStatus.OK);
    }

    @PostMapping(value = "/processPayment")
    public ResponseEntity<CustomResponse> processPayment(@RequestBody ProcessPaymentModel processPaymentModel){
        CustomResponse customResponse = new CustomResponse();
        int responseCode = billService.processPayment(processPaymentModel);

        if(responseCode==HttpStatus.OK.value()){
            customResponse.setStatus(HttpStatus.OK.value());
            customResponse.setMessage("payment processed successfully");
            return new ResponseEntity<>(customResponse,HttpStatus.OK);
        }else{
            customResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            customResponse.setMessage("could not process payment");
            return new ResponseEntity<>(customResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/addSplitBill")
    public ResponseEntity<CustomResponse> putSplitBill(@RequestBody SplitBillRequest splitBillRequest){

        CustomResponse customResponse = new CustomResponse();
        int responseCode = billService.putSplitBill(splitBillRequest);
        switch (responseCode){
            case 200 :
                customResponse.setStatus(HttpStatus.OK.value());
                customResponse.setMessage("Successfully added friend");
                return new ResponseEntity<>(customResponse,HttpStatus.OK);
            case 400 :
                customResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                customResponse.setMessage("Bad request, request body fields cannot be empty or same");
                return new ResponseEntity<>(customResponse,HttpStatus.BAD_REQUEST);

            case 404 :
                customResponse.setStatus(HttpStatus.NOT_FOUND.value());
                customResponse.setMessage("User or friend profile not found, sign up.");
                return new ResponseEntity<>(customResponse,HttpStatus.NOT_FOUND);

            case 409 :
                customResponse.setStatus(HttpStatus.CONFLICT.value());
                customResponse.setMessage("Friendship already exists");
                return new ResponseEntity<>(customResponse,HttpStatus.CONFLICT);

            default:
                customResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                customResponse.setMessage("Error adding friend");
                return new ResponseEntity<>(customResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
