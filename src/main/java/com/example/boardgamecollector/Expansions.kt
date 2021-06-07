package com.example.boardgamecollector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.boardgamecollector.db.MyDBHandler
import com.example.boardgamecollector.objects.Rank
import java.util.ArrayList

class Expansions : AppCompatActivity() {

    private var expansions: MutableList<String> = ArrayList()
    lateinit var expansionsList: ListView

    var id: Int = 0

    val dbHandler = MyDBHandler(this, null, null, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expansions)

        val extras = intent.extras ?: return
        id = extras.getInt("id")

        expansionsList = findViewById(R.id.listViewExpansions)

        expansions = dbHandler.getExpansions(id)

        if (expansions.size == 0){
            expansions.add("There are no expansions for this game")
        }
        expansionsList.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, expansions)




    }
}