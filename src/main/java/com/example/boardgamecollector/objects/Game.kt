package com.example.boardgamecollector.objects

import java.util.*

class Game {

    var id: Int = 0
    var gameName: String? = ""
    var thumbnail: String = ""
    var image: String = ""
    var description: String = ""
    var yearPublished: Int = Calendar.getInstance().get(Calendar.YEAR);
    var artistNames: String = ""
    var rank: Int = 0

    constructor() {

    }


    constructor(id: Int, gameName: String, thumbnail: String, image: String,
                description: String, yearPublished: Int, artistNames: String, rank: Int){
        this.id = id
        this.gameName = gameName
        this.thumbnail = thumbnail
        this.image = image
        this.description = description
        this.yearPublished = yearPublished
        this.artistNames = artistNames
        this.rank = rank

    }
}
