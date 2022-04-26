package com.example.webill.repository;

import com.example.webill.models.BillModel;
import com.example.webill.models.Bills_Prod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillLocRepository extends JpaRepository<Bills_Prod,Integer> {

    @Query(value = "select * from WeBillDB.bills_prod where billId = :billId and \n"+
            "latitude = :latitude and longitude = :longitude",nativeQuery = true)
    Bills_Prod getBillsForUserByLoc(@Param("billId")Integer billId,
                                    @Param("latitude")String latitude,
                                    @Param("longitude")String longitude);
}
