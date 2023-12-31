package com.srvTKcdg.repositories;

import com.srvTKcdg.models.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface QuestionRepository extends JpaRepository <Question,Long> {
    @Query("SELECT DISTENCT q.subject FROM Question q")
    List<String> findDisctinctSubject();

    Page<Question> findBySubject(String subject, Pageable pageable);
}
