package com.journal.journal.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journal.journal.entity.JournalEntry;
import com.journal.journal.entity.User;
import com.journal.journal.service.JournalEntryService;
import com.journal.journal.service.UserService;

@RestController
@RequestMapping("/journalv2")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService JournalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<List<JournalEntry>> getAllEntriesOfUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        List <JournalEntry> entries = user.getJournalEntries();
        if (entries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry, @PathVariable String username) {
        try {
            System.out.println(entry);
            System.out.println(username);
        JournalEntryService.saveEntry(entry, username);
        return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{journalEntryId}")
    public ResponseEntity<JournalEntry> getSingleJournalEntry(@PathVariable ObjectId journalEntryId) {
        Optional<JournalEntry> entry = JournalEntryService.getEntryById(journalEntryId);

        if (entry.isPresent()) {
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        } 

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{journalEntryId}/{username}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId journalEntryId, @PathVariable String username) {
         JournalEntryService.deleteEntry(journalEntryId, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{journalEntryId}/{username}")
    public ResponseEntity<JournalEntry> updateJournalEntry(@PathVariable ObjectId journalEntryId, @RequestBody JournalEntry newEntry, @PathVariable String username) {
        JournalEntry old = JournalEntryService.getEntryById(journalEntryId).orElse(null);
        if (old != null) {
            old.setTitle(newEntry.getTitle() == null && newEntry.getTitle().equals("") ? old.getTitle() : newEntry.getTitle());
            old.setContent(newEntry.getContent() == null && newEntry.getContent().equals("") ? old.getContent() : newEntry.getContent());
            JournalEntryService.saveEntryWithoutUser(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
