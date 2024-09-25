package com.example.tp1_dev_mobile.data

class Contact (
    var name : String,
    var prenoms : String,
    var numero : String,
    var profession : String
) {
    var id = -1

    constructor(id :Int, name: String, prenoms: String, numero: String, profession: String) : this( name, prenoms, numero, profession){
        this.id = id
    }

}