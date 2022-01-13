package com.app.bcpcixchallenge.security.repositories;

import com.app.bcpcixchallenge.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
