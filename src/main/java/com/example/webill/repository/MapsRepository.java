package com.example.webill.repository;

import com.example.webill.models.Maps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface MapsRepository extends JpaRepository<Maps,String> {
    
    @Query(value = "select location_name, sum(amount) as total_expense, count(amount) as visits, latitude, longitude "+
                   "from temp_expenses where user_name = 'test' group by latitude, longitude",nativeQuery = true)
    Maps getExpenseLocation();

}
