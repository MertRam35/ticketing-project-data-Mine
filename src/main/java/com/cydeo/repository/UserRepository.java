package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);

    @Transactional
    void deleteByUserName(String username);


    List<User> findByRoleDescriptionIgnoreCase(String description);
}
