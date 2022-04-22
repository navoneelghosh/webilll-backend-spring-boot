package com.example.webill.controller;

import com.example.webill.models.CustomResponse;
import com.example.webill.models.Users;
import com.example.webill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/health",method = RequestMethod.GET)
    public String hello(){
        System.out.println("inside hello");
        return "hello world";
    }

    @GetMapping(value = "/getUsers")
    public List<Users> getUsers(){
        return userService.listAll();
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<CustomResponse> signin(@RequestBody Users user){
        if(user.getUsername()==null || user.getUsername().isEmpty()){
            return new ResponseEntity<>(new CustomResponse(HttpStatus.BAD_REQUEST.value(), "username cannot be empty"),HttpStatus.BAD_REQUEST);
        }
        if(user.getPassword()==null || user.getPassword().isEmpty()){
            return new ResponseEntity<>(new CustomResponse(HttpStatus.BAD_REQUEST.value(), "password cannot be empty"),HttpStatus.BAD_REQUEST);
        }
        try{
            //get existing user from db
            Users existingUser = userService.get(user.getUsername());
            if(user.getUsername().equals(existingUser.getUsername()) && user.getPassword().equals(existingUser.getPassword())){
                return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(), "login successful"),HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new CustomResponse(400, "incorrect password"), HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new CustomResponse(HttpStatus.NOT_FOUND.value(), "user doesnt exist, please register"),HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user){
        if(user.getUsername()==null || user.getUsername().isEmpty()){
            return new ResponseEntity<>("username cannot be null",HttpStatus.BAD_REQUEST);
        }
        try {
            Users existingUser = userService.get(user.getUsername());
            if(existingUser!=null){
                return new ResponseEntity<>("User already exists", HttpStatus.FORBIDDEN);
            }
        }catch(Exception e){
            try{
                if((user.getEmail()==null || user.getEmail().isEmpty()) || (user.getPassword()==null || user.getPassword().isEmpty())){
                    return new ResponseEntity<>("email cannot be empty",HttpStatus.BAD_REQUEST);
                }
                userService.registerUser(user);
                return new ResponseEntity<>("User successfully registered",HttpStatus.OK);
            }catch (Exception e1){
                return new ResponseEntity<>("Could not register user",HttpStatus.BAD_REQUEST);
            }
        }
        return null;
    }

}

