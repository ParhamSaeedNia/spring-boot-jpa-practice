package org.example.springbootjpapractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.springbootjpapractice.entity.User;
import org.example.springbootjpapractice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for managing users with pagination and advanced search capabilities")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Operation(
        summary = "Get all users with pagination",
        description = "Retrieve a paginated list of all users. Supports sorting and filtering.",
        tags = {"User Management"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved paginated users",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Page.class),
                examples = @ExampleObject(
                    value = "{\"content\":[{\"id\":1,\"name\":\"John Doe\",\"email\":\"john@example.com\",\"age\":25,\"city\":\"New York\"}],\"totalElements\":1,\"totalPages\":1,\"size\":10,\"number\":0}"
                )
            )
        )
    })
    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(
            @Parameter(description = "Page number (0-based)", example = "0") 
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10") 
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort by field (name, email, age, city, createdAt)", example = "name") 
            @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc, desc)", example = "asc") 
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<User> users = userService.getAllUsers(pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @Operation(
        summary = "Get user by ID",
        description = "Retrieve a specific user by their unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @Operation(
        summary = "Get user by email",
        description = "Retrieve a specific user by their email address"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(
            @Parameter(description = "User email", required = true, example = "john@example.com")
            @PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @Operation(
        summary = "Search users by city with pagination",
        description = "Find users living in a specific city with pagination support"
    )
    @GetMapping("/search/city/{city}")
    public ResponseEntity<Page<User>> findUsersByCity(
            @Parameter(description = "City name", required = true, example = "New York")
            @PathVariable String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<User> users = userService.findUsersByCity(city, pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @Operation(
        summary = "Search users by age range with pagination",
        description = "Find users within a specific age range with pagination support"
    )
    @GetMapping("/search/age")
    public ResponseEntity<Page<User>> findUsersByAgeRange(
            @Parameter(description = "Minimum age", required = true, example = "25")
            @RequestParam Integer minAge,
            @Parameter(description = "Maximum age", required = true, example = "35")
            @RequestParam Integer maxAge,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "age") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<User> users = userService.findUsersByAgeRange(minAge, maxAge, pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @Operation(
        summary = "Search users older than specified age with pagination",
        description = "Find users older than a specific age with pagination support"
    )
    @GetMapping("/search/older-than/{age}")
    public ResponseEntity<Page<User>> findUsersOlderThan(
            @Parameter(description = "Minimum age", required = true, example = "30")
            @PathVariable Integer age,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "age") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<User> users = userService.findUsersOlderThan(age, pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @Operation(
        summary = "Search users by name with pagination",
        description = "Find users by name (case-insensitive) with pagination support"
    )
    @GetMapping("/search/name/{name}")
    public ResponseEntity<Page<User>> findUsersByName(
            @Parameter(description = "Name to search for", required = true, example = "John")
            @PathVariable String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<User> users = userService.findUsersByName(name, pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @Operation(
        summary = "Search users by email domain with pagination",
        description = "Find users with emails from a specific domain with pagination support"
    )
    @GetMapping("/search/email-domain/{domain}")
    public ResponseEntity<Page<User>> findUsersByEmailDomain(
            @Parameter(description = "Email domain", required = true, example = "@example.com")
            @PathVariable String domain,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "email") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<User> users = userService.findUsersByEmailDomain(domain, pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @Operation(
        summary = "Search users by name and city with pagination",
        description = "Find users by both name and city with pagination support"
    )
    @GetMapping("/search/name-city")
    public ResponseEntity<Page<User>> findUsersByNameAndCity(
            @Parameter(description = "Name to search for", required = true, example = "John")
            @RequestParam String name,
            @Parameter(description = "City name", required = true, example = "New York")
            @RequestParam String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<User> users = userService.findUsersByNameAndCity(name, city, pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @Operation(
        summary = "Count users by city",
        description = "Get the total count of users in a specific city"
    )
    @GetMapping("/count/city/{city}")
    public ResponseEntity<Long> countUsersByCity(
            @Parameter(description = "City name", required = true, example = "Chicago")
            @PathVariable String city) {
        Long count = userService.countUsersByCity(city);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    @Operation(
        summary = "Create a new user",
        description = "Add a new user to the system"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid user data")
    })
    @PostMapping
    public ResponseEntity<User> createUser(
            @Parameter(description = "User object", required = true)
            @RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @Operation(
        summary = "Update an existing user",
        description = "Update user information by ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid user data"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated user object", required = true)
            @RequestBody User user) {
        try {
            user.setId(id);
            User updatedUser = userService.updateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @Operation(
        summary = "Delete a user",
        description = "Remove a user from the system by ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @Operation(
        summary = "Check if user exists by email",
        description = "Verify if a user with the given email exists"
    )
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> userExistsByEmail(
            @Parameter(description = "Email address", required = true, example = "john@example.com")
            @PathVariable String email) {
        boolean exists = userService.userExistsByEmail(email);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}