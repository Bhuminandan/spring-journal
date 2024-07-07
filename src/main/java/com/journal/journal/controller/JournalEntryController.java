package com.journal.journal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journal.journal.entity.JournalEntry;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<ObjectId, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAllGeneralEntriesOfUser(@PathVariable String username) {
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry entry) {
        journalEntries.put(entry.getId(), entry);
        return true;
    }

    @GetMapping("id/{journalEntryId}")
    public JournalEntry getSingleJournalEntry(@PathVariable ObjectId journalEntryId) {
        return journalEntries.get(journalEntryId);
    }

    @DeleteMapping("id/{journalEntryId}")
    public boolean deleteJournalEntry(@PathVariable ObjectId journalEntryId) {
        return true;
    }

    @PutMapping("id/{journalEntryId}")
    public JournalEntry updateJournalEntry(@PathVariable ObjectId journalEntryId, @RequestBody JournalEntry entry) {
     return journalEntries.put(journalEntryId, entry);   
    }
}
