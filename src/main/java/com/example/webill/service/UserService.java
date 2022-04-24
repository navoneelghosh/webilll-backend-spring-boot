package com.example.webill.service;

import com.example.webill.models.UserStripeAccount;
import com.example.webill.models.Users;
import com.example.webill.repository.UserRepository;
import com.example.webill.repository.UserStripeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStripeRepository userStripeRepository;

    public List<Users> listAll(){
        return userRepository.findAll();
    }

    public void save(Users user){
        userRepository.save(user);
    }

    public void registerUser(Users user){
        String phone = (user.getPhone()!=null || !user.getPhone().isEmpty())?user.getPhone():"";
        String address = (user.getAddress()!=null || !user.getAddress().isEmpty())?user.getAddress():"";
        String gender = (user.getGender()!=null || !user.getGender().isEmpty())?user.getGender():"Female";
        userRepository.registerUser(user.getUsername(), user.getEmail(),user.getPassword(),phone,address,gender);
    }

    public Users get(String username){
        return userRepository.findById(username).get();
    }

    public void delete(String username){
        userRepository.deleteById(username);
    }

    public void addStripeDetails(UserStripeAccount userStripeAccount){
        boolean accountExists = userStripeRepository.existsById(userStripeAccount.getUsername());
        if(accountExists)
            return;
        else{
            userStripeRepository.addStripeDetails(userStripeAccount.getUsername(), userStripeAccount.getAccount_id(), userStripeAccount.getCustomer_id());
        }
    }

}
