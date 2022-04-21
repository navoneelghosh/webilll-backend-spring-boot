package com.example.webill.service;

import com.example.webill.models.Constants;
import com.example.webill.models.Friend;
import com.example.webill.models.Users;
import com.example.webill.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Transactional
public class FriendService {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private Constants constants;

    public int addFriend(Friend friend) {
        Users user;
        Users friend1;
        if(checkFriendRequestBody(friend)){
            //check if user and friend exist
            try{
                user = userService.get(friend.getUsername1());
                friend1 = userService.get(friend.getUsername2());
            }catch (Exception e){
                return constants.getUSERNOTFOUND();
            }

            if(user!=null && friend1!=null){
                //1.create friendship key
                String friendshipkey = createFriendshipKey(user.getUsername(),friend1.getUsername());
                //2.add friendship to table
                friend.setFriendshipKey(friendshipkey);
                int count = friendRepository.checkExistingFriendship(friendshipkey);
                if(count==0)
                    friendRepository.addFriendship(user.getUsername(), friend1.getUsername(),friendshipkey);
                else{
                    //friendship exists
                    return constants.getFRIENDSHIP_EXISTS();
                }
            }else{
                //user not found or friend not found
                return constants.getUSERNOTFOUND();
            }
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
