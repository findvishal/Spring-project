package com.sunbasedata.assignment.repository;

import com.sunbasedata.assignment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLoginId(String loginId);
    User findByLoginId(String LoginId);
}
