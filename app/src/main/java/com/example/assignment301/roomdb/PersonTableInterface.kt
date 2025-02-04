package com.example.assignment301.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonTableInterface {
    @Query("SELECT * FROM PersonTable")
    fun getAll(): List<PersonTable>

    @Query("SELECT * FROM PersonTable WHERE id = (:id)")
    fun getById(id: Int): PersonTable

    @Query("UPDATE PersonTable SET name = (:name), age = (:age) WHERE id = (:id) ")
    fun setById(id: Int, name: String, age: String)

    @Query("DELETE FROM PersonTable WHERE id = (:id)" )
    fun deleteById(id: Int)

    @Insert
    fun addPerson(person: PersonTable): Long

}