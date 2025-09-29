package org.example.springbootjpapractice.service;

import org.example.springbootjpapractice.entity.User;
import org.example.springbootjpapractice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }
    
    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    // Get user by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    // Find users by name (case-insensitive)
    public List<User> findUsersByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }
    
    // Find users by city
    public List<User> findUsersByCity(String city) {
        return userRepository.findByCity(city);
    }
    
    // Find users by age range
    public List<User> findUsersByAgeRange(Integer minAge, Integer maxAge) {
        return userRepository.findByAgeBetween(minAge, maxAge);
    }
    
    // Find users older than specified age
    public List<User> findUsersOlderThan(Integer age) {
        return userRepository.findUsersOlderThan(age);
    }
    
    // Find users by name and city
    public List<User> findUsersByNameAndCity(String name, String city) {
        return userRepository.findByNameAndCity(name, city);
    }
    
    // Count users by city
    public Long countUsersByCity(String city) {
        return userRepository.countByCity(city);
    }
    
    // Find users with specific email domain
    public List<User> findUsersByEmailDomain(String domain) {
        return userRepository.findByEmailDomain(domain);
    }
    
    // Find users older than specified age ordered by creation date
    public List<User> findUsersOlderThanOrderByCreatedAt(Integer age) {
        return userRepository.findUsersOlderThanOrderByCreatedAt(age);
    }
    
    // Find users created in the last N days
    public List<User> findUsersCreatedInLastDays(Long days) {
        return userRepository.findUsersCreatedInLastDays(days);
    }
    
    // Update user
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    // Delete user by ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    // Check if user exists by email
    public boolean userExistsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    
    // Paginated methods
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    
    public Page<User> findUsersByCity(String city, Pageable pageable) {
        return userRepository.findByCity(city, pageable);
    }
    
    public Page<User> findUsersByAgeRange(Integer minAge, Integer maxAge, Pageable pageable) {
        return userRepository.findByAgeBetween(minAge, maxAge, pageable);
    }
    
    public Page<User> findUsersOlderThan(Integer age, Pageable pageable) {
        return userRepository.findUsersOlderThan(age, pageable);
    }
    
    public Page<User> findUsersByName(String name, Pageable pageable) {
        return userRepository.findByNameContainingIgnoreCase(name, pageable);
    }
    
    public Page<User> findUsersByEmailDomain(String domain, Pageable pageable) {
        return userRepository.findByEmailDomain(domain, pageable);
    }
    
    public Page<User> findUsersByNameAndCity(String name, String city, Pageable pageable) {
        return userRepository.findByNameAndCity(name, city, pageable);
    }
    
    public Page<User> findUsersOlderThanOrderByCreatedAt(Integer age, Pageable pageable) {
        return userRepository.findUsersOlderThanOrderByCreatedAt(age, pageable);
    }
    
    public Page<User> findUsersCreatedInLastDays(Long days, Pageable pageable) {
        return userRepository.findUsersCreatedInLastDays(days, pageable);
    }
}
