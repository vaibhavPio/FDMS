package com.fdms.repository;


import com.fdms.entity.RequestAndSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestAndSuggestionRepository extends JpaRepository<RequestAndSuggestion, Integer> {


    @Query(value = "select * from request_and_suggestion where request_status=FALSE",nativeQuery = true)
    List<RequestAndSuggestion> findAllNotDone();
}
