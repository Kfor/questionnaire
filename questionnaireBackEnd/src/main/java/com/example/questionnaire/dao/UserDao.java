package com.example.questionnaire.dao;

import com.example.questionnaire.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {
    User findDistinctByUsername(String username);
}
