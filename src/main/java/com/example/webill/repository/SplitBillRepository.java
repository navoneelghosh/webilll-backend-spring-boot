package com.example.webill.repository;

import com.example.webill.models.Bill_Split;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SplitBillRepository extends JpaRepository<Bill_Split,Integer> {

    @Modifying
    @Query(value = "INSERT INTO WeBillDB.bill_split (\n" +
            "billId, usernameFrom,\n" +
            "usernameTo, amount,\n" +
            "isSettled\n" +
            ") VALUES (\n" +
            ":billId, :usernameFrom,\n" +
            ":usernameTo, :amount,\n" +
            "\"false\");", nativeQuery = true)
    void addBillSplit(@Param("billId")int billId, @Param("usernameFrom")String usernameFrom,
                 @Param("usernameTo")String usernameTo, @Param("amount")double amount);

}
