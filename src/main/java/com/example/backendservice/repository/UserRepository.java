package com.example.backendservice.repository;

import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.domain.entity.User;
import com.example.backendservice.exception.NotFoundException;
import com.example.backendservice.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  @Query("SELECT u FROM User u WHERE u.id = ?1")
  Optional<User> findById(String id);

  @Query("SELECT u FROM User u WHERE u.email = ?1")
  Optional<User> findUserByEmail(String email);

  @Query("SELECT u FROM User u WHERE u.username = ?1")
  Optional<User> findUserByUsername(String username);

  default User getUser(UserPrincipal currentUser) {
    return findUserByEmail(currentUser.getUsername())
        .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USERNAME,
            new String[]{currentUser.getUsername()}));
  }

}
