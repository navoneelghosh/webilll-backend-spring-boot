package com.example.webill.repository;

import com.example.webill.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Users,String> {

    @Modifying
    @Query(
            value =
                    "insert into users_prod (username,email,password,phone,address,gender) values (:username,:email,:password,:phone,:address,:gender)",
            nativeQuery = true)
    void registerUser(@Param("username") String username, @Param("email") String email,
                    @Param("password") String password, @Param("phone") String phone,@Param("address") String address,@Param("gender") String gender);

    @Query(value = "select * from users_prod where username = :username",nativeQuery = true)
    Users getUserById(@Param("username")String username);

   @Query(value = "select distinct bill_split.billId from bill_split where usernameFrom = :username or usernameTo=:username ;",nativeQuery = true)
   List<Integer> getBillIdsForUser(@Param("username")String username);

   @Modifying
   @Query(value = "update users_prod set password = :password where username = :username",nativeQuery = true)
   int changePassword(@Param("username")String username,@Param("password")String password);

    @Modifying
    @Query(value = "update users_prod set phone = :phone where username = :username",nativeQuery = true)
    int changePhone(@Param("username")String username,@Param("phone")String phone);



}
