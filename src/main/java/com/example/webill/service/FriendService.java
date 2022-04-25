package com.example.webill.service;

import com.example.webill.models.*;
import com.example.webill.repository.FriendRepository;
import com.example.webill.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class FriendService {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Constants constants;

    public List<FriendBreakdown> getFriendsBreakdown(String username){
        List<FriendBreakdown> friendBreakdowns = new ArrayList<>();
        boolean userExists = userRepository.existsById(username);
        if(userExists){
            //1. get list of friends for user
            List<String> friends = getFriends(username);
            if(friends.size()>0){
                for(String friend : friends){
                    double[] amounts = getAmountsBreakdown(username,friend);
                    FriendBreakdown friendBreakdown = new FriendBreakdown(friend,amounts[0],amounts[1]);
                    friendBreakdowns.add(friendBreakdown);
                }
            }
        }

        return friendBreakdowns;
    }

    private List<String> getFriends(String username){
        List<String> friends = new ArrayList<>();
        List<String> keys = friendRepository.getFriendshipKeys(username);

        if(keys.size()>0){
            for(String key : keys){
                String[] entities = key.split("-");
                if(entities[0].equalsIgnoreCase(username))
                    friends.add(entities[2]);
                else
                    friends.add(entities[0]);
            }
        }

        return friends;
    }

    private double[] getAmountsBreakdown(String username,String friend){
        double amountOwed = 0.0,amountToPay = 0.0;
        try{
            amountOwed = friendRepository.getAmountOwedByFriend(username,friend);
        }catch (Exception e){
            //do nothing
        }

        try{
            amountToPay = friendRepository.getAmountToPayToFriend(username,friend);
        }catch (Exception e){
            //do nothing
        }

        return new double[]{amountOwed,amountToPay};
    }




    public Object getBalance(String username){
        CustomResponse customResponse = new CustomResponse();
        UserBalance userBalance = new UserBalance();

        //check if user exists
        boolean userExists = userRepository.existsById(username);
        if(userExists){
            double amountOwed=0,amountToPay=0;
            //get amount owed
            try{
                amountOwed = friendRepository.getAmountOwedForUser(username);
            }catch (Exception e){
                amountOwed = 0.0;
            }
            try{
                amountToPay = friendRepository.getAmountToPay(username);
            }catch (Exception e){
                amountToPay = 0.0;
            }
            userBalance.setAmountOwed(amountOwed);
            userBalance.setAmountToPay(amountToPay);

            return userBalance;

        }else{
            customResponse.setMessage("User does not exist");
            customResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return customResponse;
        }
    }

    public int addFriend(Friend friend) {
        Users user;
        Users friend1;
        if(checkFriendRequestBody(friend)) {
            //check if user and friend exist
            boolean userExists = userRepository.existsById(friend.getUsername1());
            boolean friendExists = userRepository.existsById(friend.getUsername2());

            if (!userExists || !friendExists)
                return constants.getUSERNOTFOUND();

            user = userRepository.getUserById(friend.getUsername1());
            friend1 = userRepository.getUserById(friend.getUsername2());

            String friendshipKey = createFriendshipKey(user.getUsername(), friend1.getUsername());
            friend.setFriendshipKey(friendshipKey);

            boolean friendshipExists = (friendRepository.checkFriendshipExists(friendshipKey)>0)? true : false;

            if (!friendshipExists)
                friendRepository.addFriendship(user.getUsername(), friend1.getUsername(), friendshipKey);
            else
                return constants.getFRIENDSHIP_EXISTS();

        }else{
            return constants.getBADREQUEST();
        }
        return constants.getSUCCESS();
    }

    private boolean checkFriendRequestBody(Friend friend){

        if(friend==null) return false;

        if(friend.getUsername1()==null || friend.getUsername1().isEmpty())
            return false;

        if(friend.getUsername2()==null || friend.getUsername2().isEmpty())
            return false;

        if(friend.getUsername1().equals(friend.getUsername2()))
            return false;

        return true;
    }

    private String createFriendshipKey(String username1,String username2){
        String friendshipKey = "";
        if(username1.compareTo(username2)>=0){
            friendshipKey+=username1;
            friendshipKey+="-friends-";
            friendshipKey+=username2;
        }
        else{
            friendshipKey+=username2;
            friendshipKey+="-friends-";
            friendshipKey+=username1;
        }
        return friendshipKey;
    }


}
