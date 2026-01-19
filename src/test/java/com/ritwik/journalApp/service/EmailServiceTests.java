package com.ritwik.journalApp.service;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Disabled
    @Test
    void testSendMail() {
        emailService.sendEmail("carnation_duchess348@slmail.me",
                "Testing Java mail sender",
                "Hi, aap kaise hain ?");
    }
}