package com.example.webill.repository;

import com.example.webill.models.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend,String> {

    @Modifying
    @Query(value = "insert into friends_prod (username1,username2,friendshipKey) values (:username1,:username2,:friendshipKey)",nativeQuery = true)
    void addFriendship(@Param("username1")String username1,@Param("username2") String username2,@Param("friendshipKey")String friendshipKey);

    @Query(value = "select sum(amount) from bill_split where usernameTo = :username",nativeQuery = true)
    double getAmountOwedForUser(@Param("username")String username);

    @Query(value = "select sum(amount) from bill_split where usernameFrom = :username",nativeQuery = true)
    double getAmountToPay(@Param("username")String username);

    @Query(value = "select count(*) from friends_prod where friendshipKey = :friendshipKey",nativeQuery = true)
    int checkFriendshipExists(@Param("friendshipKey")String friendshipKey);

    @Query(value = "select friendshipkey from friends_prod where username1=:username or username2=:username",nativeQuery = true)
    List<String> getFriendshipKeys(@Param("username")String username);

    @Query(value = "select sum(amount) from bill_split where usernameFrom=:friend and usernameTo=:username",nativeQuery = true)
    double getAmountOwedByFriend(@Param("username")String username,@Param("friend")String friend);

    @Query(value = "select sum(amount) from bill_split where usernameFrom=:username and usernameTo=:friend",nativeQuery = true)
    double getAmountToPayToFriend(@Param("username")String username,@Param("friend")String friend);

}
