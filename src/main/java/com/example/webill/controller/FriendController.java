package com.example.webill.controller;

import com.example.webill.models.*;
import com.example.webill.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private Constants constants;

    @PostMapping(value = "/add")
    public ResponseEntity<CustomResponse> addFriend(@RequestBody Friend friend){
        int status = 0;
        CustomResponse customResponse = new CustomResponse();
        try{
            status = friendService.addFriend(friend);
        }
        catch (Exception e){
            customResponse.setStatus(HttpStatus.NOT_FOUND.value());
            customResponse.setMessage("User or friend profile not found, sign up.");
            return new ResponseEntity<>(customResponse,HttpStatus.OK);
        }

        switch (status){
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
        //return new ResponseEntity<>(customResponse,customResponse.getStatus());
    }

    //get total balance for user
    @GetMapping(value = "/getBalance")
    public ResponseEntity<?> getBalance(@RequestParam(name = "username")String username){
        Object responseObj = friendService.getBalance(username);
        if(responseObj instanceof UserBalance){
            return new ResponseEntity<>(responseObj,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseObj,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/getFriendsBreakdown")
    public ResponseEntity<?> getFriendsBreakdown(@RequestParam(name = "username")String username){
        List<FriendBreakdown> friendBreakdownList = friendService.getFriendsBreakdown(username);
        if(friendBreakdownList.size()==0){
            CustomResponse customResponse = new CustomResponse();
            customResponse.setMessage("No friends expenses found");
            customResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(customResponse,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(friendBreakdownList,HttpStatus.OK);
    }
}
