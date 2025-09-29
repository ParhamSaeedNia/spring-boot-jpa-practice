package org.example.springbootjpapractice.config;

import org.example.springbootjpapractice.entity.User;
import org.example.springbootjpapractice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        
        User user1 = new User("John Doe", "john.doe@example.com", 25, "New York");
        User user2 = new User("Jane Smith", "jane.smith@example.com", 30, "Los Angeles");
        User user3 = new User("Bob Johnson", "bob.johnson@test.com", 35, "Chicago");
        User user4 = new User("Alice Brown", "alice.brown@example.com", 28, "New York");
        User user5 = new User("Charlie Wilson", "charlie.wilson@test.com", 42, "Miami");
        User user6 = new User("Diana Davis", "diana.davis@example.com", 22, "Seattle");
        User user7 = new User("Eve Miller", "eve.miller@demo.com", 38, "Boston");
        User user8 = new User("Frank Garcia", "frank.garcia@example.com", 45, "Chicago");
        
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);
        userRepository.save(user7);
        userRepository.save(user8);
        
        System.out.println("Sample data initialized successfully!");
        System.out.println("Total users created: " + userRepository.count());
    }
}
