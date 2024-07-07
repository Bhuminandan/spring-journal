package com.journal.journal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journal.journal.entity.JournalEntry;
import com.journal.journal.entity.User;
import com.journal.journal.repository.JournalEntryRepository;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository JournalEntryRepository;

    @Autowired
    private UserService userService;


    public JournalEntry saveEntry(JournalEntry JournalEntry, String username) {
        User user = userService.findByUsername(username);
        JournalEntry.setDate(LocalDateTime.now());
        JournalEntry savedEntry = JournalEntryRepository.save(JournalEntry);
        user.getJournalEntries().add(savedEntry);
        userService.saveUser(user);
        return savedEntry;
    }

    public JournalEntry saveEntryWithoutUser(JournalEntry JournalEntry) {
        return JournalEntryRepository.save(JournalEntry);
    }

    public List<JournalEntry> getAllEntries() {
        return JournalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId id) {
        return JournalEntryRepository.findById(id);
    }

    public boolean deleteEntry(ObjectId id, String username) {
        User user = userService.findByUsername(username);
        user.getJournalEntries().removeIf(journalEntry -> journalEntry.getId().equals(id));
        userService.saveUser(user);
        JournalEntryRepository.deleteById(id);
        return true;
    }
}
