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


    List<User> findAllByIsDeletedOrderByFirstNameDesc(Boolean deleted);

    Optional<User> findByUserNameAndIsDeleted(String username, Boolean deleted);

    @Transactional
    void deleteByUserName(String username);


    List<User> findByRoleDescriptionIgnoreCaseAndIsDeleted(String description, Boolean deleted);
}
