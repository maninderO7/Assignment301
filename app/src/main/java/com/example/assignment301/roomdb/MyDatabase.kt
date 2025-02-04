package com.example.assignment301.roomdb

import android.content.Context
import androidx.room.Room

object MyDatabase {
    var appDB: AssignmentAppDB ?= null

    fun buildDB(context: Context){
        appDB = Room.databaseBuilder(context, AssignmentAppDB::class.java, "PersonTableDB")
            .build()
    }

    fun getPersonTableInstance(context: Context): PersonTableInterface{
        if(appDB == null){
            buildDB(context)
        }

        return appDB?.personTableInterface()!!
    }
}