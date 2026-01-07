package com.ritwik.journalApp.controller;

import com.ritwik.journalApp.DTO.EmailRequest;
import com.ritwik.journalApp.apiresponse.WeatherResponse;
import com.ritwik.journalApp.entity.JournalEntry;
import com.ritwik.journalApp.entity.User;
import com.ritwik.journalApp.repository.UserRepository;
import com.ritwik.journalApp.service.EmailService;
import com.ritwik.journalApp.service.JournalEntryService;
import com.ritwik.journalApp.service.UserService;
import com.ritwik.journalApp.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeatherService weatherService;

    @Autowired
    private EmailService emailService;


    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");

        String greeting = "";
        if(weatherResponse!=null){
            greeting = " Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }



        return new ResponseEntity<>("Hi "+ authentication.getName() + greeting ,HttpStatus.OK);
    }
    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        try {
            emailService.sendEmail(
                    request.getTo(),
                    request.getSubject(),
                    request.getBody()
            );
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            log.error("Error while sending email", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email");
        }
    }

}
