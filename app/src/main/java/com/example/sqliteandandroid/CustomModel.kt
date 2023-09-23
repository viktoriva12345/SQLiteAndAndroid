package com.example.sqliteandandroid

class CustomModel {
    //// SETTERS
    // GETTERS
    var id = 0
    var name: String? = null
    var age = 0
    var isActive = false

    constructor()
    constructor(id: Int, name: String?, age: Int, isActive: Boolean) {
        this.id = id
        this.name = name
        this.age = age
        this.isActive = isActive
    }

    override fun toString(): String {
        return "CustomModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", isActive=" + isActive +
                '}'
    }
}