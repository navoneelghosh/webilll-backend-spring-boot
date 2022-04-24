package com.example.webill.controller;

import com.example.webill.models.*;
import com.example.webill.service.StripeService;
import com.example.webill.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.EphemeralKey;
import com.stripe.model.PaymentIntent;
import com.stripe.model.SetupIntent;
import com.stripe.net.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class StripeController {

    @Autowired
    private UserService userService;

    @Autowired
    private StripeService stripeService;

    @Value("${stripe.api_key}")
    private String stripeApiKey;

    @Value("${stripe.stripe_pk}")
    private String stripePk;

    //create account id for user
    @PostMapping(value = "/createAccount")
    public ResponseEntity<?> createAccount(@RequestBody Users user) throws StripeException {
        Users userFromDb = userService.get(user.getUsername());

        //get account id and customer id from stripe
        String[] accountDetails = stripeService.createAccount(userFromDb);
        if(accountDetails.length!=2){
            CustomResponse customResponse = new CustomResponse(400,"unable to fetch account details from stripe");
            return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
        }else{
            //insert account details into db
            UserStripeAccount userStripeAccount = new UserStripeAccount(user.getUsername(),accountDetails[0],accountDetails[1]);
            userService.addStripeDetails(userStripeAccount);
            return new ResponseEntity<>(userStripeAccount,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getAccount")
    public ResponseEntity<?> getAccount(@RequestParam(name = "username")String username){
        //get account details from db for given username
        CustomResponse customResponse = new CustomResponse();
        try{
            UserStripeAccount userStripeAccount = stripeService.getAccountDetails(username);
            if(userStripeAccount==null){
                customResponse.setMessage("account details not found in db, check username");
                customResponse.setStatus(HttpStatus.NOT_FOUND.value());

                return new ResponseEntity<>(customResponse,HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(userStripeAccount,HttpStatus.OK);
            }
        }catch (Exception e){
            customResponse.setMessage(e.getMessage());
            customResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(customResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //create payment sheet
    @GetMapping(value = "/payment_sheet")
    public ResponseEntity<?> getPaymentSheet(@RequestParam(name = "username")String username) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        try{
            UserStripeAccount userStripeAccount = stripeService.getAccountDetails(username);
            PaymentSheetModel paymentSheetModel = stripeService.createPaymentSheet(userStripeAccount);
            return new ResponseEntity<>(paymentSheetModel,HttpStatus.OK);
        }catch (StripeException e){
            System.out.println(e.getMessage());
            CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseEntity<>(customResponse,HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),"account details not found, check username");
            return new ResponseEntity<>(customResponse,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/pay_friend")
    public ResponseEntity<?> payFriend(@RequestBody PayFriendModel payFriendModel){
        try{
            PaymentIntent paymentIntent = stripeService.payFriend(payFriendModel);
            CustomResponse customResponse = new CustomResponse();
            customResponse.setMessage(paymentIntent.getStatus());
            customResponse.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(customResponse,HttpStatus.OK);
        }catch (StripeException stripeException){
            CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(), stripeException.getMessage());
            return new ResponseEntity<>(customResponse,HttpStatus.BAD_REQUEST);
        }

    }


}
