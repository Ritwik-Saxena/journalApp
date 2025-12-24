package com.ritwik.journalApp.service;

import com.ritwik.journalApp.entity.JournalEntry;
import com.ritwik.journalApp.entity.User;
import com.ritwik.journalApp.repository.JournalEntryRepository;
import com.ritwik.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void saveUser(User user) {

            userRepository.save(user);

    }
    public boolean saveNewUser(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        }catch(Exception e){
            log.error("An error occurred while saving user {} :",user.getUserName(), e);
            return false;
        }


    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId userId) {
        return userRepository.findById(userId);
    }

    public void deleteUserById(ObjectId userId) {
        userRepository.deleteById(userId);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }
}
