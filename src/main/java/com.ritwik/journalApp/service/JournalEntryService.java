package com.ritwik.journalApp.service;

import com.mongodb.annotations.Sealed;
import com.ritwik.journalApp.entity.JournalEntry;
import com.ritwik.journalApp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.ritwik.journalApp.repository.JournalEntryRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;



    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {

        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the entry.", e);
        }

    }

    public void saveEntry(JournalEntry journalEntry) {


        journalEntryRepository.save(journalEntry);

    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId journalEntryId) {
        return journalEntryRepository.findById(journalEntryId);
    }

    @Transactional
    public boolean deleteJournalEntryById(ObjectId journalEntryId, String userName) {
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(journalEntryId));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(journalEntryId);
            }

        }catch (Exception e) {
            log.error("An error occurred while deleting the entry.", e);
            throw new RuntimeException("An error occurred while deleting the entry.", e);
        }
        return removed;


    }

}
