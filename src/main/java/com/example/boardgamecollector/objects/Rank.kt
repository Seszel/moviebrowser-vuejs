package com.example.boardgamecollector.objects

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Rank {

    var id: Int = 0
    var date: String = ""
    var rank: Int = 0

    constructor() {

    }

    constructor(id: Int, date: String, rank: Int){
        this.id = id
        this.date = date
        this.rank = rank

    }
}