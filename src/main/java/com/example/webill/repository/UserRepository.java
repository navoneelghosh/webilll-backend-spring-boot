package com.example.webill.repository;

import com.example.webill.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users,String> {

    @Modifying
    @Query(
            value =
                    "insert into users_prod (username,email,password,phone,address,gender) values (:username,:email,:password,:phone,:address,:gender)",
            nativeQuery = true)
    void registerUser(@Param("username") String username, @Param("email") String email,
                    @Param("password") String password, @Param("phone") String phone,@Param("address") String address,@Param("gender") String gender);

}
