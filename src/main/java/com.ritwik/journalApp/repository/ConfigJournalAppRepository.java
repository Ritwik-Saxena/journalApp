package com.ritwik.journalApp.repository;

import com.ritwik.journalApp.entity.ConfigJournalAppEntity;
import com.ritwik.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
