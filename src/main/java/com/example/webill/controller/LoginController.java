package com.example.webill.controller;

import com.example.webill.models.ChangePassword;
import com.example.webill.models.ChangePhone;
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

    @PostMapping(value = "/changePassword")
    public ResponseEntity<CustomResponse> changePassword(@RequestBody ChangePassword changePassword){
        if(changePassword.getUsername()==null || changePassword.getUsername().isEmpty()){
            return new ResponseEntity<>(new CustomResponse(HttpStatus.BAD_REQUEST.value(), "Username cannot be empty"),HttpStatus.BAD_REQUEST);
        }

        CustomResponse customResponse = userService.changePassword(changePassword);
        if(customResponse.getStatus()==HttpStatus.OK.value()){
            return new ResponseEntity<>(customResponse,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(customResponse,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/changePhone")
    public ResponseEntity<CustomResponse> changePhone(@RequestBody ChangePhone changePhone){
        if(changePhone.getUsername()==null || changePhone.getUsername().isEmpty()){
            return new ResponseEntity<>(new CustomResponse(HttpStatus.BAD_REQUEST.value(), "Username cannot be empty"),HttpStatus.BAD_REQUEST);
        }

        CustomResponse customResponse = userService.changePhone(changePhone);
        if(customResponse.getStatus()==HttpStatus.OK.value()){
            return new ResponseEntity<>(customResponse,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(customResponse,HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user){
        CustomResponse customResponse = new CustomResponse();
        if(user.getUsername()==null || user.getUsername().isEmpty()){
            customResponse.setMessage("username cannot be null");
            customResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(customResponse,HttpStatus.BAD_REQUEST);
        }
        try {
            Users existingUser = userService.get(user.getUsername());
            if(existingUser!=null){
                customResponse.setMessage("User already exists");
                customResponse.setStatus(HttpStatus.FORBIDDEN.value());
                return new ResponseEntity<>(customResponse, HttpStatus.FORBIDDEN);
            }
        }catch(Exception e){
            try{
                if((user.getEmail()==null || user.getEmail().isEmpty()) || (user.getPassword()==null || user.getPassword().isEmpty())){
                    customResponse.setMessage("email cannot be empty");
                    customResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                    return new ResponseEntity<>(customResponse,HttpStatus.BAD_REQUEST);
                }
                userService.registerUser(user);
                customResponse.setMessage("User successfully registered");
                customResponse.setStatus(HttpStatus.OK.value());
                return new ResponseEntity<>(customResponse,HttpStatus.OK);
            }catch (Exception e1){
                customResponse.setMessage("Could not register user");
                customResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                return new ResponseEntity<>(customResponse,HttpStatus.BAD_REQUEST);
            }
        }
        return null;
    }

}

