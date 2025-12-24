package com.ritwik.journalApp.repository;

import com.ritwik.journalApp.entity.JournalEntry;
import com.ritwik.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);

    void deleteByUserName(String name);
}
