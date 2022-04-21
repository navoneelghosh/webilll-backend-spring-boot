package com.example.webill.controller;

import com.example.webill.models.Constants;
import com.example.webill.models.CustomResponse;
import com.example.webill.models.Friend;
import com.example.webill.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                break;
            case 400 :
                customResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                customResponse.setMessage("Bad request, request body fields cannot be empty or same");
                break;
            case 404 :
                customResponse.setStatus(HttpStatus.NOT_FOUND.value());
                customResponse.setMessage("User or friend profile not found, sign up.");
                break;
            case 409 :
                customResponse.setStatus(HttpStatus.CONFLICT.value());
                customResponse.setMessage("Friendship already exists");
                break;
            default:
                customResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                customResponse.setMessage("Error adding friend");
        }
        return new ResponseEntity<>(customResponse,HttpStatus.OK);
    }
}
