package com.example.webill.repository;

import com.example.webill.models.Maps;
import com.example.webill.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MapsRepository extends JpaRepository<Maps,Integer> {
    
    @Query(value = "SELECT bp.billId as expense_id, bp.billName as location_name, \n" +
            "CASE \n" +
            "WHEN bs.usernameFrom = :username THEN SUM(bs.amount) \n" +
            "WHEN bs.usernameTo = :username THEN (bp.totalAmount - SUM(bs.amount))\n" +
            "END as total_expense,\n" +
            "COUNT(DISTINCT bp.billId) as visits, bp.latitude, bp.longitude\n" +
            "FROM WeBillDB.bills_prod as bp JOIN WeBillDB.bill_split bs \n" +
            "ON bp.billId = bs.billId\n" +
            "WHERE bs.usernameFrom = :username OR bs.usernameTo = :username \n" +
            "group by bp.latitude, bp.longitude;",nativeQuery = true)
    List<Maps> getExpenseLocation(@Param("username")String username);

}
