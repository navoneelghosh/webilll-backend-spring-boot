package com.example.webill.service;

import com.example.webill.models.PayFriendModel;
import com.example.webill.models.PaymentSheetModel;
import com.example.webill.models.UserStripeAccount;
import com.example.webill.models.Users;
import com.example.webill.repository.UserStripeRepository;
import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerListPaymentMethodsParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StripeService {

    @Value("${stripe.api_key}")
    private String stripeApiKey;

    @Value("${stripe.stripe_pk}")
    private String stripePk;

    @Autowired
    private UserService userService;

    @Autowired
    private UserStripeRepository userStripeRepository;

    public UserStripeAccount getAccountDetails(String username){
        UserStripeAccount userStripeAccount = userStripeRepository.getAccount(username);
        if(userStripeAccount==null){
            return null;
        }
        return userStripeAccount;
    }

    public String[] createAccount(Users user){
        Users userFromDb = userService.get(user.getUsername());
        String account_id = "";
        String customer_id = "";

        try{
            account_id = getAccountId(userFromDb);
            customer_id = getCustomerId(userFromDb,account_id);

            return new String[]{account_id,customer_id};
        } catch (StripeException | UnknownHostException e) {
            System.out.println(e.getMessage());
        }
        return new String[]{};
    }

    private String getCustomerId(Users users,String account_id) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        Map<String, Object> params = new HashMap<>();
        params.put("description","My First Test Customer (created for API docs)");
        params.put("name",users.getUsername());

        Customer customer = Customer.create(params);
        return customer.getId();

    }

    private String getAccountId(Users user) throws StripeException, UnknownHostException {
        Stripe.apiKey = stripeApiKey;

        Map<String,Object> individual = new HashMap<>();
        Map<String,String> dateOfBirth = new HashMap<>();
        Map<String, Object> capabilities = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        Map<String,Object> businessProfile = new HashMap<>();
        Map<String, Object> transfers = new HashMap<>();
        Map<String, Object> tosAcceptance = new HashMap<>();
        tosAcceptance.put("date", Instant.now().getEpochSecond());
        tosAcceptance.put("ip", InetAddress.getLocalHost().getHostAddress());

        transfers.put("requested", true);

        dateOfBirth.put("day","01");
        dateOfBirth.put("month","01");
        dateOfBirth.put("year","1970");
        individual.put("dob",dateOfBirth);
        individual.put("first_name",user.getUsername());
        individual.put("last_name",user.getEmail());
        individual.put("ssn_last_4","0000");

        capabilities.put("transfers", transfers);

        businessProfile.put("product_description","money transfer betw. user accounts");

        params.put("type", "custom");
        params.put("country", "US");
        params.put("business_type","individual");
        params.put("business_profile",businessProfile);
        params.put("capabilities", capabilities);
        params.put("individual",individual);
        params.put("tos_acceptance",tosAcceptance);

        Account account = Account.create(params);

        //create external account
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("external_account", "tok_visa_debit");
        account.getExternalAccounts().create(cardParams);

        return account.getId();
    }


    //create payment sheet
    public PaymentSheetModel createPaymentSheet(UserStripeAccount userStripeAccount) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        //create parameters
        Map<String,Object> params = new HashMap<>();
        params.put("customer",userStripeAccount.getCustomer_id());

        //build request options for ephemeral key
        RequestOptions.RequestOptionsBuilder requestOptionsBuilder = new RequestOptions.RequestOptionsBuilder();
        requestOptionsBuilder.setStripeVersionOverride(Stripe.API_VERSION);

        //create ephemeral key
        EphemeralKey ephemeralKey = EphemeralKey.create(params, requestOptionsBuilder.build());

        //create payment intent
        SetupIntent setupIntent = SetupIntent.create(params);

        PaymentSheetModel paymentSheetModel = new PaymentSheetModel();
        paymentSheetModel.setSetupIntent(setupIntent.getClientSecret());
        paymentSheetModel.setEphemeralKey(ephemeralKey.getSecret());
        paymentSheetModel.setCustomer(userStripeAccount.getCustomer_id());
        paymentSheetModel.setPublishableKey(stripePk);

        return paymentSheetModel;
    }


    //pay friend
    public PaymentIntent payFriend(PayFriendModel payFriendModel) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        //1. get stripe payment method list
        PaymentMethodCollection paymentMethods = getPaymentMethodList(payFriendModel.getSourceCustomerId());
        String srcCardId = paymentMethods.getData().get(0).getId();

        //attempt to pay friend
        try{
            //create parameters for the payment intent
            Map<String, Object> params = new HashMap<>();

            Map<String,Object> transferData = new HashMap<>();
            transferData.put("destination",payFriendModel.getDestAccountId());

            params.put("amount", payFriendModel.getAmount());
            params.put("currency", "usd");
            params.put("customer",payFriendModel.getSourceCustomerId());
            params.put("payment_method",srcCardId);
            params.put("transfer_data",transferData);
            params.put("off_session",true);
            params.put("confirm",true);

            PaymentIntent paymentIntent = PaymentIntent.create(params);
            return paymentIntent;
        }catch (CardException cardException){
            System.out.println("Card error code:"+cardException.getCode());
            String paymentIntentId = cardException.getRequestId();
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            return paymentIntent;
        }
    }

    private void attachPaymentMethod(String customerId) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        Map<String, Object> card = new HashMap<>();
        card.put("number", "4242424242424242");
        card.put("exp_month", 4);
        card.put("exp_year", 2023);
        card.put("cvc", "314");
        Map<String, Object> params = new HashMap<>();
        params.put("type", "card");
        params.put("card", card);

        PaymentMethod paymentMethod = PaymentMethod.create(params);

        Map<String, Object> custParams = new HashMap<>();
        custParams.put("customer", customerId);

        PaymentMethod updatedPaymentM = paymentMethod.attach(custParams);
    }

    private PaymentMethodCollection getPaymentMethodList(String customerId) throws StripeException {
        Stripe.apiKey = stripeApiKey;

//        Map<String, Object> params = new HashMap<>();
//        params.put("customer", customerId);
//        params.put("type", "card");

        Customer customer = Customer.retrieve(customerId);

        CustomerListPaymentMethodsParams params = CustomerListPaymentMethodsParams.builder()
                        .setType(CustomerListPaymentMethodsParams.Type.CARD)
                        .build();

        PaymentMethodCollection paymentMethods = customer.listPaymentMethods(params);

        if(paymentMethods.getData().size()<1){
            attachPaymentMethod(customerId);
            paymentMethods = customer.listPaymentMethods(params);
        }

        return paymentMethods;
    }
}
