package org.example.springbootjpapractice.repository;

import org.example.springbootjpapractice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
    
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByNameContainingIgnoreCase(@Param("name") String name);
    
    @Query("SELECT u FROM User u WHERE u.city = :city")
    List<User> findByCity(@Param("city") String city);
    
    @Query("SELECT u FROM User u WHERE u.age BETWEEN :minAge AND :maxAge")
    List<User> findByAgeBetween(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);
    
    @Query("SELECT u FROM User u WHERE u.age > :age")
    List<User> findUsersOlderThan(@Param("age") Integer age);
    
    @Query("SELECT u FROM User u WHERE u.name = :name AND u.city = :city")
    List<User> findByNameAndCity(@Param("name") String name, @Param("city") String city);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.city = :city")
    Long countByCity(@Param("city") String city);
    
    @Query("SELECT u FROM User u WHERE u.email LIKE CONCAT('%', :domain)")
    List<User> findByEmailDomain(@Param("domain") String domain);
    
    @Query(value = "SELECT * FROM users WHERE age > :age ORDER BY created_at DESC", nativeQuery = true)
    List<User> findUsersOlderThanOrderByCreatedAt(@Param("age") Integer age);
    
    @Query("SELECT u FROM User u WHERE u.createdAt >= CURRENT_DATE - :days DAY")
    List<User> findUsersCreatedInLastDays(@Param("days") Long days);
    
    @Query("SELECT u FROM User u WHERE u.city = :city")
    Page<User> findByCity(@Param("city") String city, Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE u.age BETWEEN :minAge AND :maxAge")
    Page<User> findByAgeBetween(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge, Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE u.age > :age")
    Page<User> findUsersOlderThan(@Param("age") Integer age, Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<User> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE u.email LIKE CONCAT('%', :domain)")
    Page<User> findByEmailDomain(@Param("domain") String domain, Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE u.name = :name AND u.city = :city")
    Page<User> findByNameAndCity(@Param("name") String name, @Param("city") String city, Pageable pageable);
    
    @Query(value = "SELECT * FROM users WHERE age > :age ORDER BY created_at DESC", nativeQuery = true)
    Page<User> findUsersOlderThanOrderByCreatedAt(@Param("age") Integer age, Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE u.createdAt >= CURRENT_DATE - :days DAY")
    Page<User> findUsersCreatedInLastDays(@Param("days") Long days, Pageable pageable);
}
