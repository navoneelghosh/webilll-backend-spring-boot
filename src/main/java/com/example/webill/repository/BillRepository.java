package com.example.webill.repository;

import com.example.webill.models.BillModel;
import com.example.webill.models.Bills_Prod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<BillModel,Integer> {

    @Query(value = "select distinct * from bills where billId = :billId limit 1",nativeQuery = true)
    BillModel getBillById(@Param("billId") Integer billId);

    @Query(value = "select * from WeBillDB.bills where billId = :billId and \n"+
            "latitude = :latitude and longitude = :longitude",nativeQuery = true)
    BillModel getBillsForUserByLoc(@Param("billId")Integer billId,
                                    @Param("latitude")String latitude,
                                    @Param("longitude")String longitude);

    @Modifying
    @Query(value = "INSERT INTO WeBillDB.bills (\n" +
            "billname, totalamount,\n" +
            "date, paid_by,\n" +
            "latitude, longitude\n" +
            ") VALUES (\n" +
            ":billname, :totalamount,\n" +
            ":date, :paid_by,\n" +
            ":latitude, :longitude\n" +
            ")",nativeQuery = true)
    void addBill(@Param("billname")String billname, @Param("totalamount")double totalamount,
                 @Param("date")String date, @Param("paid_by")String paid_by,
                 @Param("latitude")String latitude, @Param("longitude")String longitude);

    @Query(value = "SELECT billId FROM WeBillDB.bills \n" +
            "WHERE billname = :billname AND totalamount = :totalamount AND\n" +
            "date = :date AND paid_by = :paid_by AND\n" +
            "latitude = :latitude AND longitude = :longitude ORDER BY billId DESC", nativeQuery = true)
    List<Integer> getLastAddedBillId(@Param("billname")String billname, @Param("totalamount")double totalamount,
                         @Param("date")String date, @Param("paid_by")String paid_by,
                         @Param("latitude")String latitude, @Param("longitude")String longitude);

    @Modifying
    @Query(value = "update bill_split set isSettled = 'true' where usernameFrom = :usernameFrom and usernameTo=:usernameTo",nativeQuery = true)
    int processPayment(@Param("usernameFrom")String usernameFrom,@Param("usernameTo")String usernameTo);
}
