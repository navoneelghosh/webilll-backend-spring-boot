package com.example.webill.repository;

import com.example.webill.models.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend,String> {

    @Query(value = "select count(*) from friends_prod where friendshipKey = :friendshipkey",nativeQuery = true)
    int checkExistingFriendship(@Param("friendshipkey")String friendshipkey);

    @Modifying
    @Query(value = "insert into friends_prod (username1,username2,friendshipKey) values (:username1,:username2,:friendshipKey)",nativeQuery = true)
    void addFriendship(@Param("username1")String username1,@Param("username2") String username2,@Param("friendshipKey")String friendshipKey);
}
