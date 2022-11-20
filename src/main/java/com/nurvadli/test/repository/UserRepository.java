package com.nurvadli.test.repository;

import com.nurvadli.test.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findBySocialSecurityNumberAndIsDeletedIsFalse(String socialSecurityNumber);

    Optional<User> findByIdAndIsDeletedIsFalse(String id);
}
