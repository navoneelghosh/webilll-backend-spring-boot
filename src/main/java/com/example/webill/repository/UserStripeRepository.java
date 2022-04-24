package com.example.webill.repository;

import com.example.webill.models.UserStripeAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStripeRepository extends JpaRepository<UserStripeAccount,String> {

    @Modifying
    @Query(value = "insert into user_stripe_prod (username,account_id,customer_id) values (:username,:account_id,:customer_id)",nativeQuery = true)
    public void addStripeDetails(@Param("username")String username,@Param("account_id")String account_id,@Param("customer_id")String customer_id);

    @Query(value = "select * from user_stripe_prod where username = :username",nativeQuery = true)
    public UserStripeAccount getAccount(@Param("username")String username);
}
