package com.example.assignment301.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PersonTable::class], version = 1, exportSchema = true)
abstract class AssignmentAppDB: RoomDatabase() {

    abstract fun personTableInterface(): PersonTableInterface


}