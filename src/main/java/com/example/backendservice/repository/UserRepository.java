package com.example.backendservice.repository;

import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.domain.entity.User;
import com.example.backendservice.exception.NotFoundException;
import com.example.backendservice.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  @Query("SELECT u FROM User u WHERE u.id = ?1")
  Optional<User> findById(String id);

  @Query("SELECT u FROM User u WHERE u.email = ?1")
  Optional<User> findUserByEmail(String email);

  @Query("SELECT u FROM User u WHERE u.username = ?1")
  Optional<User> findUserByUsername(String username);

  @Query("SELECT u FROM User u WHERE u.phone = ?1")
  Optional<User> findUserByPhone(String phone);

  default User getUser(UserPrincipal currentUser) {
    return findUserByEmail(currentUser.getUsername())
        .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USERNAME,
            new String[]{currentUser.getUsername()}));
  }

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);

  boolean existsByPhone(String phone);

  @Query(value = "select u.* from users u join follows f on f.user_follower_id = ?1 where " +
          "u.id = f.user_following_id order by f.created_date desc",
          nativeQuery = true)
  List<User> getFollowingByUserId(String id);

  @Query(value = "select u.* from users u join follows f on f.user_following_id = ?1 where " +
          "u.id = f.user_follower_id order by f.created_date desc", nativeQuery = true)
  List<User> getFollowersByUserId(String id);

  @Query(value = "select u.* from users u join follows f on f.user_following_id = ?1 where " +
          "u.id = f.user_follower_id and (u.username like concat(?2, '%') " +
          "or u.full_name like concat('%', ?2, '%')) order by f.created_date desc", nativeQuery = true)
  List<User> getFollowersByName(String userId, String name);

  @Query(value = "select u.* from users u join follows f on f.user_follower_id = ?1 where " +
          "u.id = f.user_following_id and (u.username like concat(?2, '%') " +
          "or u.full_name like concat('%', ?2, '%')) order by f.created_date desc", nativeQuery = true)
  List<User> getFollowingByName(String userId, String name);

  @Query(value = "select u.* from users u join follows f on u.id = f.user_following_id " +
          "where f.user_follower_id = ?1 and exists(select ff.* from follows ff where " +
          "ff.user_follower_id = f.user_following_id and ff.user_following_id = ?1) " +
          "order by f.created_date desc", nativeQuery = true)
  List<User> getFriendsById(String userId);

  @Query(value = "select u.* from users u join follows f on u.id = f.user_following_id " +
          "where f.user_follower_id = ?1 and exists(select ff.* from follows ff where " +
          "ff.user_follower_id = f.user_following_id and ff.user_following_id = ?1) " +
          "and (u.username like concat(?2, '%') or u.full_name like concat('%', ?2, '%')) " +
          "order by f.created_date desc", nativeQuery = true)
  List<User> getFriendsByName(String userId, String name);

  @Query(value = "select u.* from users u where u.username like concat(?2, '%') " +
          "or u.full_name like concat('%', ?2, '%')", nativeQuery = true)
  List<User> getUsersByName(String name);

  @Query(value = "select u.* from users u where u.id not in " +
          "(select f.user_follower_id from follows f where f.user_following_id = ?1)" +
          "and u.id <> ?1 and (u.username like concat(?2, '%') or u.full_name like concat('%', ?2, '%'))",
          nativeQuery = true)
  List<User> getUsersByNameNotFollowers(String userId, String name);

  @Query(value = "select distinct u.* from users u where u.id not in " +
          "(select f.user_following_id from follows f where f.user_follower_id = ?1)" +
          "and u.id <> ?1 and (u.username like concat(?2, '%') or u.full_name like concat('%', ?2, '%'))",
          nativeQuery = true)
  List<User> getUsersByNameNotFollowing(String userId, String name);

}
