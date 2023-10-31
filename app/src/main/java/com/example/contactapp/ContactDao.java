package com.example.contactapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact")
    List<Contact> getAll();
    @Insert
    void insert(Contact... contacts);
    @Query("DELETE FROM Contact WHERE id = :contactId")
    void deleteContactById(int contactId);
    @Query("DELETE FROM Contact")
    void deleteAll();

}
