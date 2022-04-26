package com.example.webill.repository;

import com.example.webill.models.BillModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<BillModel,Integer> {

    @Query(value = "select distinct * from bills where billId = :billId limit 1",nativeQuery = true)
    BillModel getBillById(@Param("billId") Integer billId);
}
