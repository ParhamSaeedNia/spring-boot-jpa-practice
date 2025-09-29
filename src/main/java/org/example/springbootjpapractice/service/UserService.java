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
    
    public User createUser(User user) {
        return userRepository.save(user);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public List<User> findUsersByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<User> findUsersByCity(String city) {
        return userRepository.findByCity(city);
    }
    
    public List<User> findUsersByAgeRange(Integer minAge, Integer maxAge) {
        return userRepository.findByAgeBetween(minAge, maxAge);
    }
    
    public List<User> findUsersOlderThan(Integer age) {
        return userRepository.findUsersOlderThan(age);
    }
    
    public List<User> findUsersByNameAndCity(String name, String city) {
        return userRepository.findByNameAndCity(name, city);
    }
    
    public Long countUsersByCity(String city) {
        return userRepository.countByCity(city);
    }
    
    public List<User> findUsersByEmailDomain(String domain) {
        return userRepository.findByEmailDomain(domain);
    }
    
    public List<User> findUsersOlderThanOrderByCreatedAt(Integer age) {
        return userRepository.findUsersOlderThanOrderByCreatedAt(age);
    }
    
    public List<User> findUsersCreatedInLastDays(Long days) {
        return userRepository.findUsersCreatedInLastDays(days);
    }
    
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public boolean userExistsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    
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
