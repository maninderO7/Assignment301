package com.example.assignment301.baseAdapterPrac

data class Person(
    var name: String,
    var age: String
){
    constructor():this("", "")

    override fun toString(): String {

        return "Name=$name  age=$age"
    }
}
