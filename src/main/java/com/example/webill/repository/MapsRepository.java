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
    
    @Query(value = "select expense_id, location_name, sum(amount) as total_expense, count(amount) as visits, latitude, longitude "+
                   "from temp_expenses where user_name = :username group by latitude, longitude ",nativeQuery = true)
    List<Maps> getExpenseLocation(@Param("username")String username);

}
